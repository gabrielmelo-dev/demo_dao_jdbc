package com.daojdbc.dao;

import com.daojdbc.models.Department;
import com.daojdbc.models.Seller;

public interface SellerDao {

    public void insert(Seller seller);

    public void update(Seller seller);

    public void deleteById(Integer id);

    public Seller findById(Integer id);

    public Seller findByName(String name);

    public Department[] findAll();
}
