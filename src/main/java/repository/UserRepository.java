package com.example.jobportal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getAllUsers() {

        String sql = "SELECT id, name, email, role FROM users";

        return jdbcTemplate.queryForList(sql);
    }
    public int registerUser(String name, String email,
                            String password, String role) {

        String checkSql = "SELECT COUNT(*) FROM users WHERE email = ?";

        Integer count = jdbcTemplate.queryForObject(
                checkSql,
                Integer.class,
                email
        );

        if (count != null && count > 0) {
            return 0;
        }

        String sql = "INSERT INTO users (name, email, password, role) " +
                "VALUES (?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                name,
                email,
                password,
                role
        );
    }
    public Map<String, Object> loginUser(String email, String password) {

        String sql = "SELECT id, name, email, role " +
                "FROM users " +
                "WHERE email = ? AND password = ?";

        List<Map<String, Object>> users = jdbcTemplate.queryForList(
                sql, email, password
        );

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }
    public String getUserRole(int userId) {

        String sql = "SELECT role FROM users WHERE id = ?";

        List<Map<String, Object>> users =
                jdbcTemplate.queryForList(sql, userId);

        if (users.isEmpty()) {
            return null;
        }

        return (String) users.get(0).get("role");
    }
}