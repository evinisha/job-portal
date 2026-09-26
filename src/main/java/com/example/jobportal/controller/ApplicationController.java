package com.example.jobportal.controller;

import com.example.jobportal.repository.ApplicationRepository;
import com.example.jobportal.repository.JobRepository;
import com.example.jobportal.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationController(
            ApplicationRepository applicationRepository,
            JobRepository jobRepository,
            UserRepository userRepository) {

        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    // Apply for a job
    @PostMapping("/api/applications")
    public String applyForJob(
            @RequestBody Map<String, Object> application) {

        int jobId = ((Number) application.get("job_id")).intValue();
        int userId = ((Number) application.get("user_id")).intValue();

        if (!applicationRepository.jobExists(jobId)) {
            return "Job not found!";
        }

        if (!applicationRepository.userExists(userId)) {
            return "User not found!";
        }

        if (applicationRepository.alreadyApplied(jobId, userId)) {
            return "You have already applied for this job!";
        }

        applicationRepository.applyForJob(jobId, userId);

        return "Job application submitted successfully!";
    }

    // Get applications submitted by a user
    @GetMapping("/api/applications/user/{userId}")
    public List<Map<String, Object>> getApplicationsByUser(
            @PathVariable int userId) {

        return applicationRepository.getApplicationsByUser(userId);
    }

    // Get applicants for a job
    @GetMapping("/api/applications/job/{jobId}")
    public Object getApplicationsByJob(
            @PathVariable int jobId,
            @RequestParam int recruiterId) {

        Integer jobRecruiterId = jobRepository.getRecruiterId(jobId);

        if (jobRecruiterId == null) {
            return "Job not found!";
        }

        String role = userRepository.getUserRole(recruiterId);

        if (role == null) {
            return "User not found!";
        }

        if (!role.equals("RECRUITER")) {
            return "Only recruiters can view applicants!";
        }

        if (!jobRecruiterId.equals(recruiterId)) {
            return "You are not the recruiter of this job!";
        }

        return applicationRepository
                .getApplicationsByJob(jobId);
    }

    // Update application status
    @PutMapping("/api/applications/{id}/status")
    public String updateApplicationStatus(
            @PathVariable int id,
            @RequestBody Map<String, Object> application) {

        String status = (String) application.get("status");

        applicationRepository.updateApplicationStatus(id, status);

        return "Application status updated successfully!";
    }
}