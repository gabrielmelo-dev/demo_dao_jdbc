package com.daojdbc;

import java.util.Date;

import com.daojdbc.models.Department;
import com.daojdbc.models.Seller;

public class Main {
    public static void main(String[] args) {

        Department department = new Department(1, "IT");
        Seller seller = new Seller(1, "Joao", "joaovitor@.com", new Date(), 1000.0, department);
        System.out.println(seller);
    }
}