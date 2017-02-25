package com.mad.backend.services;

import com.mad.backend.dtos.SupplierDto;
import com.mad.backend.models.Supplier;
import com.mad.backend.repositories.ProductRepository;
import com.mad.backend.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 2/25/17.
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ProductRepository productRepository) {
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        supplierRepository.findAll().forEach(suppliers::add);
        List<SupplierDto> result = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            result.add(new SupplierDto(supplier));
        }
        return result;
    }

    @Override
    public void createSupplier(SupplierDto supplierDto) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDto.getName());
        supplier.setAddress(supplierDto.getAddress());
        supplierRepository.save(supplier);
    }

    @Override
    public void updateSupplier(SupplierDto supplierDto) {
        Supplier supplier = supplierRepository.findOne(supplierDto.getId());
        if (supplier != null) {
            supplier.setName(supplierDto.getName());
            supplier.setAddress(supplierDto.getAddress());
            supplierRepository.save(supplier);
        }
    }

    @Override
    public void deleteSupplier(Integer supplierId) {
        Supplier supplier = supplierRepository.findOne(supplierId);
        if (supplier != null) {
            productRepository.delete(supplier.getProducts());
            supplierRepository.delete(supplierId);
        }
    }
}
