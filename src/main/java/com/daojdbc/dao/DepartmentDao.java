package com.daojdbc.dao;

import com.daojdbc.models.Department;

public interface DepartmentDao {

    public void insert(Department department);

    public void update(Department department);

    public void deleteById(Integer id);

    public Department findById(Integer id);

    public Department findByName(String name);

    public Department[] findAll();
}
