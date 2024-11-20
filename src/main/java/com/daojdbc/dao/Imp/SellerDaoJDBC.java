package com.daojdbc.dao.Imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daojdbc.dao.SellerDao;
import com.daojdbc.db.DBException;
import com.daojdbc.models.Department;
import com.daojdbc.models.Seller;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
        String sql = "INSERT INTO seller (name, email, birthDate, baseSalary, departmentId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            prepareStatementSeller(ps, seller);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    seller.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void update(Seller seller) {
        String sql = "UPDATE seller SET name = ?, email = ?, birthDate = ?, baseSalary = ?, departmentId = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(6, seller.getId());
            prepareStatementSeller(ps, seller);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM seller WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public Seller findById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT seller.*, department.* FROM seller INNER JOIN department ON seller.departmentId = department.id WHERE seller.id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Department department = instantiateDepartment(rs);
                    Seller seller = instantiateSeller(rs, department);
                    return seller;
                }
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return null;
    }

    @Override
    public Seller findByName(String name) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT seller.*, department.* FROM seller INNER JOIN department ON seller.departmentId = department.id WHERE seller.name = ?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Department department = instantiateDepartment(rs);
                    Seller seller = instantiateSeller(rs, department);
                    return seller;
                }
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        List<Seller> sellers = new ArrayList<>();
        Map<Integer, Department> departmentMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT seller.*, department.* FROM seller INNER JOIN department ON seller.departmentId = department.id WHERE department.id = ?")) {
            ps.setInt(1, department.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Department dep = departmentMap.get(rs.getInt("departmentId"));
                    if (dep == null) {
                        dep = instantiateDepartment(rs);
                        departmentMap.put(rs.getInt("departmentId"), dep);
                    }
                    Seller seller = instantiateSeller(rs, dep);
                    sellers.add(seller);
                }
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return sellers;
    }

    @Override
    public List<Seller> findAll() {
        List<Seller> sellers = new ArrayList<>();
        Map<Integer, Department> departmentMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT seller.*, department.* FROM seller INNER JOIN department ON seller.departmentId = department.id")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Department dep = departmentMap.get(rs.getInt("departmentId"));
                    if (dep == null) {
                        dep = instantiateDepartment(rs);
                        departmentMap.put(rs.getInt("departmentId"), dep);
                    }
                    sellers.add(instantiateSeller(rs, dep));
                }
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return sellers;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("department.id"), rs.getString("department.name"));
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        return new Seller(rs.getInt("seller.id"), rs.getString("seller.name"),
                rs.getString("seller.email"), rs.getDate("seller.birthDate"),
                rs.getDouble("seller.baseSalary"), department);
    }

    private PreparedStatement prepareStatementSeller(PreparedStatement ps, Seller seller) throws SQLException {
        ps.setString(1, seller.getName());
        ps.setString(2, seller.getEmail());
        ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
        ps.setDouble(4, seller.getBaseSalary());
        ps.setInt(5, seller.getDepartment().getId());
        return ps;
    }

}
