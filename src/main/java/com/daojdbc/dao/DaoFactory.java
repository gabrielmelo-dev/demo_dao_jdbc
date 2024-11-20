package com.daojdbc.dao;

import com.daojdbc.dao.Imp.DepartmentDaoJDBC;
import com.daojdbc.dao.Imp.SellerDaoJDBC;
import com.daojdbc.db.DB;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }

}
