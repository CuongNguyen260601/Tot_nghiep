package com.localbrand.service.impl;

import com.localbrand.common.ServiceResult;
import com.localbrand.common.Status_Enum;
import com.localbrand.dto.request.BillRequestDTO;
import com.localbrand.dto.response.BillProductResponseDTO;
import com.localbrand.dto.response.BillResponseDTO;
import com.localbrand.entity.Address;
import com.localbrand.entity.Bill;
import com.localbrand.entity.BillProduct;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.AddressMapping;
import com.localbrand.model_mapping.Impl.BillMapping;
import com.localbrand.model_mapping.Impl.BillProductMapping;
import com.localbrand.repository.AddressRepository;
import com.localbrand.repository.BillProductRepository;
import com.localbrand.repository.BillRepository;
import com.localbrand.service.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ServiceResult<BillResponseDTO> saveBillUser(BillRequestDTO billRequestDTO) {

        if(!billRequestDTO.getIdStatus().equals(Status_Enum.PROCESSING.getCode())){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Bill.SAVE_BILL_FALSE, null);
        }

        log.info("Save bill");

        Address address = this.addressMapping.toEntitySave(billRequestDTO.getAddressRequestDTO());

        address = this.addressRepository.save(address);

        Bill bill = this.billMapping.toEntitySave(billRequestDTO);

        bill.setIdAddress(address.getIdAddress().intValue());


        bill = this.billRepository.save(bill);

        List<BillProduct> lstBillProduct = this.billProductMapping.toListProduct(bill, billRequestDTO);

        Integer total = 0;

        for (BillProduct billProduct:lstBillProduct) {
            if(billProduct.getIdStatus().equals(Status_Enum.EXISTS.getCode())){
                total += billProduct.getQuantity();
            }
        }

        bill.setTotal(total);

        bill = this.billRepository.save(bill);

        this.billProductRepository.saveAll(lstBillProduct);

        return new ServiceResult<>(HttpStatus.OK, Notification.Bill.SAVE_BILL_SUCCESS, this.billMapping.toDto(bill));
    }

    @Override
    public ServiceResult<BillResponseDTO> saveBillAdmin(BillRequestDTO billRequestDTO) {
        log.info("Save bill");

        Address address = this.addressMapping.toEntitySave(billRequestDTO.getAddressRequestDTO());

        address = this.addressRepository.save(address);

        Bill bill = this.billMapping.toEntitySave(billRequestDTO);

        bill.setIdAddress(address.getIdAddress().intValue());


        bill = this.billRepository.save(bill);

        List<BillProduct> lstBillProduct = this.billProductMapping.toListProduct(bill, billRequestDTO);

        Integer total = 0;

        for (BillProduct billProduct:lstBillProduct) {
            if(billProduct.getIdStatus().equals(Status_Enum.EXISTS.getCode())){
                total += billProduct.getQuantity();
            }
        }

        bill.setTotal(total);

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
    public ServiceResult<List<BillResponseDTO>> getAllListBillAdmin(Optional<Integer> page, Optional<Integer> limit) {
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

}
