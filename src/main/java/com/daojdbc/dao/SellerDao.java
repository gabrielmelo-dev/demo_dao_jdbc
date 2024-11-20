package com.daojdbc.dao;

import java.util.List;

import com.daojdbc.models.Department;
import com.daojdbc.models.Seller;

public interface SellerDao {

    public void insert(Seller seller);

    public void update(Seller seller);

    public void deleteById(Integer id);

    public Seller findById(Integer id);

    public Seller findByName(String name);

    public List<Seller> findByDepartment(Department department);

    public List<Seller> findAll();
}