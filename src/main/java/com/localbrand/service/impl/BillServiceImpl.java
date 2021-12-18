package com.localbrand.service.impl;

import com.localbrand.common.*;
import com.localbrand.dto.request.BillRequestDTO;
import com.localbrand.dto.response.*;
import com.localbrand.entity.*;
import com.localbrand.exception.MessageLogs;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.AddressMapping;
import com.localbrand.model_mapping.Impl.BillComboMapping;
import com.localbrand.model_mapping.Impl.BillMapping;
import com.localbrand.model_mapping.Impl.BillProductMapping;
import com.localbrand.repository.*;
import com.localbrand.service.BillService;
import com.localbrand.service.MailService;
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
    private final BillComboMapping billComboMapping;
    private final CartComboRepository cartComboRepository;
    private final BillComboRepository billComboRepository;
    private final MailService mailService;
    private final UserRepository userRepository;


    @Transactional(rollbackOn = Exception.class)
    @Override
    public ServiceResult<BillResponseDTO> saveBillUser(BillRequestDTO billRequestDTO) {

        log.info(MessageLogs.SAVE_USER_BILL);

        if(!billRequestDTO.getIdStatus().equals(Status_Enum.PROCESSING.getCode())){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.SAVE_BILL_FALSE, null);
        }


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
                }
            }
        }

        if(Objects.nonNull(billRequestDTO.getIdBill())){
            List<BillProduct> billProducts = this.billProductRepository.findAllByIdBill(billRequestDTO.getIdBill().intValue());
            List<BillCombo> billCombos = this.billComboRepository.findAllByIdBill(billRequestDTO.getIdBill().intValue());

            if(billProducts.size()>0){
                this.billProductRepository.deleteAll(billProducts);
            }
            if(billCombos.size()>0){
                this.billComboRepository.deleteAll(billCombos);
            }
        }
        Bill bill = this.billMapping.toEntitySave(billRequestDTO);

        if(Objects.nonNull(voucher)){
            VoucherUser voucherUser = this.voucherUserRepository.findByIdVoucherAndAndIdUser(voucher.getIdVoucher().intValue(), bill.getIdUser()).orElse(null);

            if(Objects.isNull(voucherUser) || voucherUser.getQuantity().equals(0)){
                return new ServiceResult<>(HttpStatus.BAD_REQUEST, "Voucher is not applicable", null);
            }

            bill.setIdVoucher(voucher.getIdVoucher().intValue());

            voucherUser.setQuantity(voucherUser.getQuantity()-1);

            this.voucherUserRepository.save(voucherUser);
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
        List<BillCombo> lstBillCombo = this.billComboMapping.toListCombo(bill, billRequestDTO);

        float total = 0F;

        for (BillProduct billProduct:lstBillProduct) {
            if(billProduct.getIdStatus().equals(Status_Enum.EXISTS.getCode())){
                total += billProduct.getPrice()* billProduct.getQuantity();
            }

            CartProduct cartProduct = this.cartProductRepository.findFirstByIdProductDetailAndIdUser(billProduct.getIdProductDetail(), bill.getIdUser());

            this.cartProductRepository.delete(cartProduct);
        }

        for (BillCombo billCombo:lstBillCombo) {
            if(billCombo.getIdStatus().equals(Status_Enum.EXISTS.getCode())){
                total += billCombo.getPrice()* billCombo.getQuantity();
            }

            CartCombo cartCombo = this.cartComboRepository.findFirstByIdComboAndIdUser(billCombo.getIdCombo(), bill.getIdUser());

            this.cartComboRepository.delete(cartCombo);
        }

        Voucher voucherDonate = this.voucherRepository.findFirstByCondition(Status_Enum.EXISTS.getCode(),total).orElse(null);

        if(Objects.nonNull(voucherDonate) && bill.getIdStatus().equals(Status_Enum.PAID.getCode())){
            VoucherUser voucherUser = this.voucherUserRepository.findByIdVoucherAndAndIdUser(voucherDonate.getIdVoucher().intValue(), bill.getIdUser()).orElse(null);
            if(Objects.nonNull(voucherUser)){
                voucherUser.setQuantity(voucherUser.getQuantity()+1);
            }else{
                VoucherUser voucherDonateUser = VoucherUser
                        .builder()
                        .idVoucher(voucherDonate.getIdVoucher().intValue())
                        .idUser(bill.getIdUser())
                        .quantity(1)
                        .build();
                this.voucherUserRepository.save(voucherDonateUser);
            }
        }
        if(Objects.nonNull(voucher))
        {
            total = total/100 * (100 - voucher.getDiscount());
        }

        bill.setTotal(total);

        bill.setPayment(total-bill.getDeposit());

        bill = this.billRepository.save(bill);

        this.billProductRepository.saveAll(lstBillProduct);
        this.billComboRepository.saveAll(lstBillCombo);


        if(bill.getIdStatus().equals(Status_Enum.CANCEL.getCode())){
            List<BillProduct> billProducts = this.billProductRepository.findAllByIdBillAndIdStatus(bill.getIdBill().intValue(), Status_Enum.EXISTS.getCode());
            List<BillCombo> billCombos = this.billComboRepository.findAllByIdBillAndIdStatus(bill.getIdBill().intValue(), Status_Enum.EXISTS.getCode());
            billProducts.forEach(billProduct -> {
                billProduct.setIdStatus(Status_Enum.DELETE.getCode());
            });

            billCombos.forEach(billCombo -> {
                billCombo.setIdStatus(Status_Enum.DELETE.getCode());
            });


            this.billProductRepository.saveAll(billProducts);
            this.billComboRepository.saveAll(billCombos);
        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.SAVE_BILL_SUCCESS, this.billMapping.toDto(bill));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ServiceResult<BillResponseDTO> saveBillAdmin(BillRequestDTO billRequestDTO) {
        log.info("Save bill");

        if(Objects.nonNull(billRequestDTO.getIdBill())){
            List<BillProduct> billProducts = this.billProductRepository.findAllByIdBill(billRequestDTO.getIdBill().intValue());
            List<BillCombo> billCombos = this.billComboRepository.findAllByIdBill(billRequestDTO.getIdBill().intValue());

            if(billProducts.size()>0){
                this.billProductRepository.deleteAll(billProducts);
            }
            if(billCombos.size()>0){
                this.billComboRepository.deleteAll(billCombos);
            }
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
        List<BillCombo> lstBillCombo = this.billComboMapping.toListCombo(bill, billRequestDTO);

        Float total = 0F;

        for (BillProduct billProduct:lstBillProduct) {
            if(billProduct.getIdStatus().equals(Status_Enum.EXISTS.getCode())){
                total += billProduct.getPrice()*billProduct.getQuantity();
            }
        }
        for (BillCombo billCombo:lstBillCombo) {
            if(billCombo.getIdStatus().equals(Status_Enum.EXISTS.getCode())){
                total += billCombo.getPrice()*billCombo.getQuantity();
            }
        }

        bill.setTotal(total);

        bill.setPayment(total-bill.getDeposit());

        if(bill.getIdStatus().equals(Status_Enum.PAID.getCode())){
            bill.setDateSuccess(Date.valueOf(java.time.LocalDate.now()));
        }

        bill = this.billRepository.save(bill);


        User user = this.userRepository.findById(bill.getIdUser().longValue()).orElse(null);

        if(Objects.nonNull(user) && user.getIdRole().equals(Role_Id_Enum.ROLE_USER.getId())){
            Voucher voucherDonate = this.voucherRepository.findFirstByCondition(Status_Enum.EXISTS.getCode(),total).orElse(null);

            if(Objects.nonNull(voucherDonate) && bill.getIdStatus().equals(Status_Enum.PAID.getCode())){
                VoucherUser voucherUser = this.voucherUserRepository.findByIdVoucherAndAndIdUser(voucherDonate.getIdVoucher().intValue(), bill.getIdUser()).orElse(null);
                if(Objects.nonNull(voucherUser)){
                    voucherUser.setQuantity(voucherUser.getQuantity()+1);
                }else{
                    VoucherUser voucherDonateUser = VoucherUser
                        .builder()
                        .idVoucher(voucherDonate.getIdVoucher().intValue())
                        .idUser(bill.getIdUser())
                        .quantity(1)
                        .build();
                    this.voucherUserRepository.save(voucherDonateUser);
                }
                mailService.sendEmailBillSuccess(user,voucherDonate);
            }
        }

        this.billProductRepository.saveAll(lstBillProduct);
        this.billComboRepository.saveAll(lstBillCombo);

        if(bill.getIdStatus().equals(Status_Enum.CANCEL.getCode())){
            List<BillProduct> billProducts = this.billProductRepository.findAllByIdBillAndIdStatus(bill.getIdBill().intValue(), Status_Enum.EXISTS.getCode());
            List<BillCombo> billCombos = this.billComboRepository.findAllByIdBillAndIdStatus(bill.getIdBill().intValue(), Status_Enum.EXISTS.getCode());
            billProducts.forEach(billProduct -> {
                billProduct.setIdStatus(Status_Enum.DELETE.getCode());
            });

            billCombos.forEach(billCombo -> {
                billCombo.setIdStatus(Status_Enum.DELETE.getCode());
            });


            this.billProductRepository.saveAll(billProducts);
            this.billComboRepository.saveAll(billCombos);
        }

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
    public ServiceResult<BillResponseDTO> cancelBillAdmin(Optional<Long> idBill, String reason) {

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
    public ServiceResult<BillDetailResponseDTO> getBillDetailByBillUser(Optional<Integer> idBill) {
        log.error("Get list bill product and filter");

        if(idBill.isEmpty() || idBill.get()<1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.Bill_Product.GET_LIST_BILL_PRODUCT_FALSE, null);
        }

        BillDetailResponseDTO billDetailResponseDTO = new BillDetailResponseDTO();
        List<BillProduct> lstBillProduct = this.billProductRepository.findAllByIdBillAndIdStatus(idBill.get(), Status_Enum.EXISTS.getCode());
        List<BillCombo> lstBillCombo = this.billComboRepository.findAllByIdBillAndIdStatus(idBill.get(), Status_Enum.EXISTS.getCode());

        billDetailResponseDTO.setListBillProductDetail(lstBillProduct.stream().map(billProductMapping :: toDto).collect(Collectors.toList()));
        billDetailResponseDTO.setListBillComboDetail(lstBillCombo.stream().map(billComboMapping :: toDto).collect(Collectors.toList()));

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.Bill_Product.GET_LIST_BILL_PRODUCT_SUCCESS, billDetailResponseDTO);
    }

    @Override
    public ServiceResult<List<BillResponseDTO>> getAllListBillAdmin(Optional<Integer> page, Optional<Integer> billType, Optional<Integer> limit) {

        if(page.isEmpty() || page.get() < 0
                || limit.isEmpty() || limit.get() <1
                || billType.isEmpty() || billType.get() < 0
        ){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());

        List<Bill> lstBill = this.billRepository.findAllByBillType(billType.get(),pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.GET_LIST_BILL_IS_SUCCESS, lstBill.stream().map(this.billMapping::toDto).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<List<BillResponseDTO>> getListBillAndSortAdmin(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Date> startDate, Optional<Date> endDate) {
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
    public ServiceResult<BillDetailResponseDTO> getBillDetailByBillAdmin(Optional<Integer> idBill) {

        log.error("Get bill detail");

        if(idBill.isEmpty() || idBill.get()<1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.Bill_Product.GET_LIST_BILL_PRODUCT_FALSE, null);
        }

        BillDetailResponseDTO billDetailResponseDTO = new BillDetailResponseDTO();
        List<BillProduct> lstBillProduct = this.billProductRepository.findAllByIdBill(idBill.get());
        List<BillCombo> lstBillCombo = this.billComboRepository.findAllByIdBill(idBill.get());

        billDetailResponseDTO.setListBillProductDetail(lstBillProduct.stream().map(billProductMapping::toDto).collect(Collectors.toList()));
        billDetailResponseDTO.setListBillComboDetail(lstBillCombo.stream().map(billComboMapping::toDto).collect(Collectors.toList()));
        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.Bill_Product.GET_LIST_BILL_PRODUCT_SUCCESS, billDetailResponseDTO);

    }

    @Override
    public ServiceResult<List<BillProductResponseDTO>> getListBillProductByBillAdmin(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idBill) {

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

    @Override
    public ServiceResult<List<BillComboResponseDTO>> getListBillComboByBillAdmin(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idBill) {

        log.error("Get list bill product and filter");

        if(page.isEmpty() || page.get() < 0
                || limit.isEmpty() || limit.get() <1
        ){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        if(idBill.isEmpty() || idBill.get()<1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.Bill_Combo.GET_LIST_BILL_COMBO_FALSE, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());

        List<BillCombo> lstBillCombo = this.billComboRepository.findAllByIdBill(idBill.get(), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.Bill_Combo.GET_LIST_BILL_COMBO_SUCCESS, lstBillCombo.stream().map(this.billComboMapping::toDto).collect(Collectors.toList()));

    }



    @Override
    public ServiceResult<List<BillResponseUserDTO>> getAllListBillUserOther(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idUser) {
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

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.GET_LIST_BILL_IS_SUCCESS, listBill.stream().map(this.billMapping::toDtoResponse).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<BillResponseUserDTO>> getAllListBillUserAndSortOther(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idUser, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Date> startDate, Optional<Date> endDate) {
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

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.GET_LIST_BILL_IS_SUCCESS, listBill.stream().map(this.billMapping::toDtoResponse).collect(Collectors.toList()));
    }

}
