package com.shop.sukuna.service;

import java.util.Optional;

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

    public Supplier fetchSupplierById(long id) {
        Optional<Supplier> supplier = this.supplierRepository.findById(id);
        if (supplier.isPresent()) {
            return supplier.get();
        }
        return null;
    }

    public Supplier updateSupplier(Supplier supplier) {
        Supplier currentSupplier = this.fetchSupplierById(supplier.getId());
        if (currentSupplier != null) {
            currentSupplier.setSupplierName(supplier.getSupplierName());
            currentSupplier.setPhone(supplier.getPhone());
            currentSupplier.setAddress(supplier.getAddress());
            currentSupplier.setEmail(supplier.getEmail());

            this.supplierRepository.save(currentSupplier);
        }
        return currentSupplier;
    }

}
