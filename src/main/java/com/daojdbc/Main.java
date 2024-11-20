package com.daojdbc;

import java.util.List;

import com.daojdbc.dao.DaoFactory;
import com.daojdbc.dao.DepartmentDao;
import com.daojdbc.dao.SellerDao;
import com.daojdbc.models.Department;
import com.daojdbc.models.Seller;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("=== TEST 2: seller findByDepartment ===");
        Department department = new Department(2, null);
        List<Seller> sellers = sellerDao.findByDepartment(department);
        for (Seller s : sellers) {
            System.out.println(s);
        }

        System.out.println("=== TEST 3: seller findAll ===");
        sellers = sellerDao.findAll();
        for (Seller s : sellers) {
            System.out.println(s);
        }

        System.out.println("=== TEST 4: seller insert ===");
        Seller newSeller = new Seller(null, "George", "K8b0Q@example.com", new java.util.Date(), 4000.0, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("=== TEST 5: seller update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        System.out.println("Update completed");

        System.out.println("=== TEST 6: seller delete ===");
        sellerDao.deleteById(1);
        System.out.println("Delete completed");

        System.out.println("=== TEST 7: seller findByName ===");
        seller = sellerDao.findByName("George");
        System.out.println(seller);

        System.out.println("=== TEST 8: Department findByName ===");
        Department dep = departmentDao.findByName("Books");
        System.out.println(dep);

        System.out.println("=== TEST 9: Department findAll ===");
        List<Department> departments = departmentDao.findAll();
        for (Department d : departments) {
            System.out.println(d);
        }

        // db.sql -> id = 5
        System.out.println("=== TEST 10: Department insert ===");
        Department newDep = new Department(null, "Music");
        departmentDao.insert(newDep);
        System.out.println("Inserted! New id = " + newDep.getId());

        System.out.println("=== TEST 11: Department update ===");
        dep = departmentDao.findById(1);
        dep.setName("Books");
        departmentDao.update(dep);
        System.out.println("Update completed");

        System.out.println("=== TEST 12: Department delete ===");
        departmentDao.deleteById(5);
        System.out.println("Delete completed");

    }
}