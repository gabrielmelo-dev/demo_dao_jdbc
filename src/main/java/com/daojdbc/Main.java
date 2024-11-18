package com.daojdbc;

import com.daojdbc.models.Department;

public class Main {
    public static void main(String[] args) {

        Department department = new Department(1, "IT");
        System.out.println(department);
    }
}