package com.example.jobportal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JobRepository {

    private final JdbcTemplate jdbcTemplate;

    public JobRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getAllJobs() {
        String sql = "SELECT id, title, description, company, location, salary, recruiter_id FROM jobs";
        return jdbcTemplate.queryForList(sql);
    }
    public List<Map<String, Object>> getJobsByRecruiter(int recruiterId) {

        String sql = "SELECT id, title, description, company, location, salary, recruiter_id " +
                "FROM jobs WHERE recruiter_id = ?";

        return jdbcTemplate.queryForList(sql, recruiterId);
    }

    public int addJob(String title, String description, String company,
                      String location, double salary, int recruiterId) {

        String sql = "INSERT INTO jobs " +
                "(title, description, company, location, salary, recruiter_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                title,
                description,
                company,
                location,
                salary,
                recruiterId
        );
    }
    public Map<String, Object> getJobById(int id) {

        String sql = "SELECT id, title, description, company, location, salary, recruiter_id " +
                "FROM jobs WHERE id = ?";

        List<Map<String, Object>> jobs =
                jdbcTemplate.queryForList(sql, id);

        if (jobs.isEmpty()) {
            return null;
        }

        return jobs.get(0);
    }
    public int updateJob(int id, String title, String description,
                         String company, String location,
                         double salary) {

        String sql = "UPDATE jobs SET title = ?, description = ?, " +
                "company = ?, location = ?, salary = ? " +
                "WHERE id = ?";

        return jdbcTemplate.update(
                sql,
                title,
                description,
                company,
                location,
                salary,
                id
        );
    }
    public int deleteJob(int id) {

        String sql = "DELETE FROM jobs WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
    public List<Map<String, Object>> searchJobs(String keyword) {

        String sql = "SELECT id, title, description, company, location, salary, recruiter_id " +
                "FROM jobs " +
                "WHERE title LIKE ? OR description LIKE ?";

        String searchKeyword = "%" + keyword + "%";

        return jdbcTemplate.queryForList(
                sql,
                searchKeyword,
                searchKeyword
        );
    }
    public Integer getRecruiterId(int jobId) {

        String sql = "SELECT recruiter_id FROM jobs WHERE id = ?";

        List<Map<String, Object>> jobs =
                jdbcTemplate.queryForList(sql, jobId);

        if (jobs.isEmpty()) {
            return null;
        }

        return ((Number) jobs.get(0).get("recruiter_id")).intValue();
    }
}