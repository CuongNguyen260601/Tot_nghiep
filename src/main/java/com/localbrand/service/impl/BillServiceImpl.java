package com.localbrand.service.impl;

import com.localbrand.common.Action_Enum;
import com.localbrand.common.Module_Enum;
import com.localbrand.common.ServiceResult;
import com.localbrand.common.Status_Enum;
import com.localbrand.dto.request.BillRequestDTO;
import com.localbrand.dto.response.BillProductResponseDTO;
import com.localbrand.dto.response.BillResponseDTO;
import com.localbrand.entity.*;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.AddressMapping;
import com.localbrand.model_mapping.Impl.BillMapping;
import com.localbrand.model_mapping.Impl.BillProductMapping;
import com.localbrand.repository.*;
import com.localbrand.service.BillService;
import com.localbrand.utils.Role_Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final BillMapping billMapping;
    private final BillProductRepository billProductRepository;
    private final BillProductMapping billProductMapping;
    private final AddressRepository addressRepository;
    private final AddressMapping addressMapping;
    private final CartProductRepository cartProductRepository;
    private final VoucherRepository voucherRepository;
    private final VoucherUserRepository voucherUserRepository;
    private final Role_Utils role_utils;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ServiceResult<BillResponseDTO> saveBillUser(BillRequestDTO billRequestDTO) {

        if(!billRequestDTO.getIdStatus().equals(Status_Enum.PROCESSING.getCode())){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.SAVE_BILL_FALSE, null);
        }

        log.info("Save bill");

        Voucher voucher;
        if(Objects.isNull(billRequestDTO.getIdVoucher())){
            if(StringUtils.isNotBlank(billRequestDTO.getCodeVoucher())){
                voucher = this.voucherRepository.findByCodeVoucher(billRequestDTO.getCodeVoucher().trim()).orElse(null);
            }else{
                voucher = null;
            }
        }else{
            voucher = this.voucherRepository.findById(billRequestDTO.getIdVoucher().longValue()).orElse(null);
            if(Objects.isNull(voucher)){
                if(StringUtils.isNotBlank(billRequestDTO.getCodeVoucher())){
                    voucher = this.voucherRepository.findByCodeVoucher(billRequestDTO.getCodeVoucher().trim()).orElse(null);
                }else{
                    voucher = null;
                }
            }
        }

        Bill bill = this.billMapping.toEntitySave(billRequestDTO);

        if(Objects.nonNull(voucher)){
            VoucherUser voucherUser = this.voucherUserRepository.findByIdVoucherAndAndIdUser(voucher.getIdVoucher().intValue(), bill.getIdUser()).orElse(null);

            if(Objects.isNull(voucherUser)){
                return new ServiceResult<>(HttpStatus.BAD_REQUEST, "Voucher is not applicable", null);
            }

            bill.setIdVoucher(voucher.getIdVoucher().intValue());
        }
        if(Objects.nonNull(billRequestDTO.getIdAddress())){
            bill.setIdAddress(billRequestDTO.getIdAddress());
        }else {
            Address address = this.addressMapping.toEntitySave(billRequestDTO.getAddressRequestDTO());

            address = this.addressRepository.save(address);

            bill.setIdAddress(address.getIdAddress().intValue());
        }

        bill = this.billRepository.save(bill);

        List<BillProduct> lstBillProduct = this.billProductMapping.toListProduct(bill, billRequestDTO);

        Float total = 0F;

        for (BillProduct billProduct:lstBillProduct) {
            if(billProduct.getIdStatus().equals(Status_Enum.EXISTS.getCode())){
                total += billProduct.getPrice()* billProduct.getQuantity();
            }

            CartProduct cartProduct = this.cartProductRepository.findFirstByIdProductDetailAndIdUser(billProduct.getIdProductDetail(), bill.getIdUser());

            this.cartProductRepository.delete(cartProduct);
        }

        if(Objects.nonNull(voucher))
        {
            total = total/100 * (100 - voucher.getDiscount());
        }

        bill.setTotal(total);

        bill.setPayment(total-bill.getDeposit());

        bill = this.billRepository.save(bill);

        this.billProductRepository.saveAll(lstBillProduct);

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.SAVE_BILL_SUCCESS, this.billMapping.toDto(bill));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ServiceResult<BillResponseDTO> saveBillAdmin(HttpServletRequest request, BillRequestDTO billRequestDTO) {
        log.info("Save bill");

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.BILL.getModule(), Action_Enum.SAVE.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not save bill", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not save bill", null);
        }

        Bill bill = this.billMapping.toEntitySave(billRequestDTO);

        if(Objects.nonNull(billRequestDTO.getIdAddress())){
            bill.setIdAddress(billRequestDTO.getIdAddress());
        }else {
            Address address = this.addressMapping.toEntitySave(billRequestDTO.getAddressRequestDTO());

            address = this.addressRepository.save(address);

            bill.setIdAddress(address.getIdAddress().intValue());
        }

        bill = this.billRepository.save(bill);

        List<BillProduct> lstBillProduct = this.billProductMapping.toListProduct(bill, billRequestDTO);

        Float total = 0F;

        for (BillProduct billProduct:lstBillProduct) {
            if(billProduct.getIdStatus().equals(Status_Enum.EXISTS.getCode())){
                total += billProduct.getPrice()*billProduct.getQuantity();
            }
        }

        bill.setTotal(total);

        bill.setPayment(total-bill.getDeposit());

        bill = this.billRepository.save(bill);

        this.billProductRepository.saveAll(lstBillProduct);

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.SAVE_BILL_SUCCESS, this.billMapping.toDto(bill));
    }

    @Override
    public ServiceResult<BillResponseDTO> cancelBillUser(Optional<Long> idBill, String reason) {
        if(idBill.isEmpty() || idBill.get()<1 || reason.isBlank()){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.SAVE_BILL_FALSE, null);
        }
        Bill bill = this.billRepository.findById(idBill.get()).orElse(null);

        if(Objects.isNull(bill)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.CANCEL_BILL_FALSE, null);
        }

        if(!bill.getIdStatus().equals(Status_Enum.PROCESSING.getCode())){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.CANCEL_BILL_FALSE, null);
        }
        bill.setDescriptionBill(reason);

        bill.setIdStatus(Status_Enum.CANCEL.getCode());

        bill = this.billRepository.save(bill);

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.CANCEL_BILL_SUCCESS, this.billMapping.toDto(bill));
    }

    @Override
    public ServiceResult<BillResponseDTO> cancelBillAdmin(HttpServletRequest request, Optional<Long> idBill, String reason) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.BILL.getModule(), Action_Enum.CANCEL.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not cancel bill", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not cancel bill", null);
        }

        if(idBill.isEmpty() || idBill.get()<1 || reason.isBlank()){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.SAVE_BILL_FALSE, null);
        }
        Bill bill = this.billRepository.findById(idBill.get()).orElse(null);

        if(Objects.isNull(bill)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.CANCEL_BILL_FALSE, null);
        }

        if(!bill.getIdStatus().equals(Status_Enum.PROCESSING.getCode())){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.CANCEL_BILL_FALSE, null);
        }
        bill.setDescriptionBill(reason);

        bill.setIdStatus(Status_Enum.CANCEL.getCode());

        bill = this.billRepository.save(bill);

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.CANCEL_BILL_SUCCESS, this.billMapping.toDto(bill));
    }

    @Override
    public ServiceResult<List<BillResponseDTO>> getAllListBill(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idUser) {

        log.error("Get list bill");

        if(page.isEmpty() || page.get() < 0
            || limit.isEmpty() || limit.get() <1
        ){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        if(idUser.isEmpty() || idUser.get()<1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.GET_LIST_BILL_IS_FALSE, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());

        List<Bill> listBill = this.billRepository.findAllByIdUser(idUser.get(), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.GET_LIST_BILL_IS_SUCCESS, listBill.stream().map(this.billMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<BillResponseDTO>> getListBillAndSort(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idUser, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Date> startDate, Optional<Date> endDate) {
        log.error("Get list bill and filter");

        if(page.isEmpty() || page.get() < 0
                || limit.isEmpty() || limit.get() <1
        ){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        if(idUser.isEmpty() || idUser.get()<1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.GET_LIST_BILL_IS_FALSE, null);
        }

        Pageable pageable;

        if(sort.isEmpty() || sort.get() < 0 || sort.get() > 1){
            pageable = PageRequest.of(page.orElse(0), limit.get());
        }else if( sort.get() == 0){
            pageable = PageRequest.of(page.orElse(0), limit.get(), Sort.by(Sort.Direction.ASC, "dateCreate"));
        }else{
            pageable = PageRequest.of(page.orElse(0), limit.get(), Sort.by(Sort.Direction.DESC, "dateCreate"));
        }

        List<Bill> listBill = new ArrayList<>();

        if(idStatus.isEmpty() || idStatus.get()<0){
            listBill = this.billRepository.findAllByIdUser(idUser.get(), startDate.get(), endDate.get(), pageable).toList();
        }else{
            listBill = this.billRepository.findAllByIdUserAndIdStatus(idUser.get(), idStatus.get(), startDate.get(), endDate.get(), pageable).toList();
        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.GET_LIST_BILL_IS_SUCCESS, listBill.stream().map(this.billMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<BillProductResponseDTO>> getListBillProductByBill(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idBill) {
        log.error("Get list bill product and filter");

        if(page.isEmpty() || page.get() < 0
                || limit.isEmpty() || limit.get() <1
        ){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        if(idBill.isEmpty() || idBill.get()<1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.Bill_Product.GET_LIST_BILL_PRODUCT_FALSE, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0),limit.get());


        List<BillProduct> lstBillProduct = this.billProductRepository.findAllByIdBillAndIdStatus(idBill.get(), Status_Enum.EXISTS.getCode(), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.Bill_Product.GET_LIST_BILL_PRODUCT_SUCCESS, lstBillProduct.stream().map(this.billProductMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<BillResponseDTO>> getAllListBillAdmin(HttpServletRequest request, Optional<Integer> page, Optional<Integer> limit) {

        String email = request.getAttribute("USER_NAME").toString();

        Boolean checkRole = role_utils.checkRole(email, Module_Enum.BILL.getModule(), Action_Enum.READ.getAction());
        if(!checkRole){
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not see list bill", null);
        }

        if(page.isEmpty() || page.get() < 0
                || limit.isEmpty() || limit.get() <1
        ){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());

        List<Bill> lstBill = this.billRepository.findAll(pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.GET_LIST_BILL_IS_SUCCESS, lstBill.stream().map(this.billMapping::toDto).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<List<BillResponseDTO>> getListBillAndSortAdmin(HttpServletRequest request, Optional<Integer> page, Optional<Integer> limit, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Date> startDate, Optional<Date> endDate) {

        String email = request.getAttribute("USER_NAME").toString();

        Boolean checkRole = role_utils.checkRole(email, Module_Enum.BILL.getModule(), Action_Enum.READ.getAction());
        if(!checkRole){
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not see list bill", null);
        }

        log.error("Get list bill and filter");

        if(page.isEmpty() || page.get() < 0
                || limit.isEmpty() || limit.get() <1
        ){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable;

        if(sort.isEmpty() || sort.get() < 0 || sort.get() > 1){
            pageable = PageRequest.of(page.orElse(0), limit.get());
        }else if( sort.get() == 0){
            pageable = PageRequest.of(page.orElse(0), limit.get(), Sort.by(Sort.Direction.ASC, "dateCreate"));
        }else{
            pageable = PageRequest.of(page.orElse(0), limit.get(), Sort.by(Sort.Direction.DESC, "dateCreate"));
        }

        List<Bill> listBill = new ArrayList<>();

        if(idStatus.isEmpty() || idStatus.get()<0){
            listBill = this.billRepository.findAllByDate(startDate.get(), endDate.get(), pageable).toList();
        }else{
            listBill = this.billRepository.findAllByDateAndIdStatus(idStatus.get(), startDate.get(), endDate.get(), pageable).toList();
        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.GET_LIST_BILL_IS_SUCCESS, listBill.stream().map(this.billMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<BillProductResponseDTO>> getListBillProductByBillAdmin(HttpServletRequest request, Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idBill) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.BILL.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get bill", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get bill", null);
        }

        log.error("Get list bill product and filter");

        if(page.isEmpty() || page.get() < 0
                || limit.isEmpty() || limit.get() <1
        ){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        if(idBill.isEmpty() || idBill.get()<1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.Bill_Product.GET_LIST_BILL_PRODUCT_FALSE, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());

        List<BillProduct> lstBillProduct = this.billProductRepository.findAllByIdBill(idBill.get(), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.Bill_Product.GET_LIST_BILL_PRODUCT_SUCCESS, lstBillProduct.stream().map(this.billProductMapping::toDto).collect(Collectors.toList()));

    }

}
