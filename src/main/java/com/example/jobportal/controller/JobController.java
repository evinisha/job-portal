package com.example.jobportal.controller;

import com.example.jobportal.repository.JobRepository;
import com.example.jobportal.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class JobController {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobController(JobRepository jobRepository,
                         UserRepository userRepository) {

        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    // Get all jobs
    @GetMapping("/api/jobs")
    public List<Map<String, Object>> getJobs() {
        return jobRepository.getAllJobs();
    }

    // Add a job
    @PostMapping("/api/jobs")
    public String addJob(@RequestBody Map<String, Object> job) {

        String title = (String) job.get("title");
        String description = (String) job.get("description");
        String company = (String) job.get("company");
        String location = (String) job.get("location");
        double salary = ((Number) job.get("salary")).doubleValue();
        int recruiterId = ((Number) job.get("recruiter_id")).intValue();

        String role = userRepository.getUserRole(recruiterId);

        if (role == null) {
            return "User not found!";
        }

        if (!role.equals("RECRUITER")) {
            return "Only recruiters can add jobs!";
        }

        jobRepository.addJob(
                title,
                description,
                company,
                location,
                salary,
                recruiterId
        );

        return "Job added successfully!";
    }

    // Get job by ID
    @GetMapping("/api/jobs/{id}")
    public ResponseEntity<Object> getJobById(@PathVariable int id) {

        Map<String, Object> job = jobRepository.getJobById(id);

        if (job == null) {
            return ResponseEntity
                    .status(404)
                    .body("Job not found!");
        }

        return ResponseEntity.ok(job);
    }

    // Update job
    @PutMapping("/api/jobs/{id}")
    public String updateJob(@PathVariable int id,
                            @RequestBody Map<String, Object> job) {

        String title = (String) job.get("title");
        String description = (String) job.get("description");
        String company = (String) job.get("company");
        String location = (String) job.get("location");
        double salary = ((Number) job.get("salary")).doubleValue();

        jobRepository.updateJob(
                id,
                title,
                description,
                company,
                location,
                salary
        );

        return "Job updated successfully!";
    }

    // Delete job
    @DeleteMapping("/api/jobs/{id}")
    public String deleteJob(@PathVariable int id) {

        jobRepository.deleteJob(id);

        return "Job deleted successfully!";
    }

    // Search jobs
    @GetMapping("/api/jobs/search")
    public List<Map<String, Object>> searchJobs(
            @RequestParam String keyword) {

        return jobRepository.searchJobs(keyword);
    }
    @GetMapping("/api/jobs/recruiter/{recruiterId}")
    public List<Map<String, Object>> getJobsByRecruiter(
            @PathVariable int recruiterId) {

        return jobRepository.getJobsByRecruiter(recruiterId);
    }
}