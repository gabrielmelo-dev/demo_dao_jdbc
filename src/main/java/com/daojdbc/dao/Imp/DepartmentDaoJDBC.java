package com.daojdbc.dao.Imp;

import com.daojdbc.dao.DepartmentDao;
import com.daojdbc.db.DBException;
import com.daojdbc.models.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    
    private Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        String sql = "INSERT INTO department (name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, department.getName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    department.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void update(Department department) {
        String sql = "UPDATE department SET name = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, department.getName());
            ps.setInt(2, department.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM department WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public Department findById(Integer id) {
        String sql = "SELECT * FROM department WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Department(rs.getInt("id"), rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return null;
    }

    @Override
    public Department findByName(String name) {
        String sql = "SELECT * FROM department WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Department(rs.getInt("id"), rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Department> findAll() {
        String sql = "SELECT * FROM department";
        List<Department> departments = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                departments.add(new Department(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return departments;
    }
    
}
