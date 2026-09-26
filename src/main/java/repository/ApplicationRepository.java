package com.example.jobportal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ApplicationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ApplicationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Apply for a job
    public int applyForJob(int jobId, int userId) {

        String sql = "INSERT INTO applications (job_id, user_id) " +
                "VALUES (?, ?)";

        return jdbcTemplate.update(sql, jobId, userId);
    }

    // Check whether user already applied
    public boolean alreadyApplied(int jobId, int userId) {

        String sql = "SELECT COUNT(*) FROM applications " +
                "WHERE job_id = ? AND user_id = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                jobId,
                userId
        );

        return count != null && count > 0;
    }

    // Get applications submitted by a user
    public List<Map<String, Object>> getApplicationsByUser(int userId) {

        String sql = "SELECT a.id, a.job_id, a.user_id, " +
                "a.applied_date, a.status, " +
                "j.title, j.company " +
                "FROM applications a " +
                "JOIN jobs j ON a.job_id = j.id " +
                "WHERE a.user_id = ?";

        return jdbcTemplate.queryForList(sql, userId);
    }

    // Get applicants for a job
    public List<Map<String, Object>> getApplicationsByJob(int jobId) {

        String sql = "SELECT a.id, a.job_id, a.user_id, " +
                "a.applied_date, a.status, " +
                "u.name, u.email " +
                "FROM applications a " +
                "JOIN users u ON a.user_id = u.id " +
                "WHERE a.job_id = ?";

        return jdbcTemplate.queryForList(sql, jobId);
    }

    // Update application status
    public int updateApplicationStatus(int applicationId, String status) {

        String sql = "UPDATE applications SET status = ? WHERE id = ?";

        return jdbcTemplate.update(
                sql,
                status,
                applicationId
        );
    }
    public boolean jobExists(int jobId) {

        String sql = "SELECT COUNT(*) FROM jobs WHERE id = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                jobId
        );

        return count != null && count > 0;
    }
    public boolean userExists(int userId) {

        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                userId
        );

        return count != null && count > 0;
    }
}