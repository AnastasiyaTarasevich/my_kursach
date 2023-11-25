package com.example.my_kursach.repos;

import com.example.my_kursach.models.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepo extends CrudRepository<Supplier, Integer> {

}
