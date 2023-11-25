package com.example.my_kursach.services;

import com.example.my_kursach.models.Supplier;
import com.example.my_kursach.models.User;
import com.example.my_kursach.repos.SupplierRepo;
import com.example.my_kursach.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepo supplierRepo;
    public Supplier findById(int id) {

        return supplierRepo.findById(id).orElse(null);
    }

    public void updateSupplier(int idsupplier, Supplier updatedSupplier)
    {
        Supplier supplier=findById(idsupplier);

        if (!supplier.getCompanyName().equals(updatedSupplier.getCompanyName()))
        {
            supplier.setCompanyName(updatedSupplier.getCompanyName());
        }
        if (supplier.getAddress()!=updatedSupplier.getAddress())
        {
            supplier.setAddress(updatedSupplier.getAddress());
        }
        if (supplier.getContactData()!=updatedSupplier.getContactData())
        {
            supplier.setContactData(updatedSupplier.getContactData());
        }
        supplierRepo.save(supplier);
    }
    public void deleteSupplier(int supplierId) {
        supplierRepo.deleteById(supplierId);
    }
}
