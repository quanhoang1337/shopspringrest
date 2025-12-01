package com.shop.sukuna.service;

import org.springframework.stereotype.Service;

import com.shop.sukuna.domain.Supplier;
import com.shop.sukuna.repository.SupplierRepository;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier createSupplier(Supplier supplier) {
        return this.supplierRepository.save(supplier);
    }

}
