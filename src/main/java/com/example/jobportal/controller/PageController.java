package com.example.jobportal.controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
//    @GetMapping("/register-test")
//    @ResponseBody
//    public String registerTest() {
//        return "REGISTER MAPPING IS WORKING!";
//    }

    @GetMapping("/jobseeker-dashboard")
    public String jobSeekerDashboard() {
        return "jobseeker-dashboard";
    }
    @GetMapping("/my-applications")
    public String myApplicationsPage() {
        return "my-applications";
    }
    @GetMapping("/recruiter-dashboard")
    public String recruiterDashboard() {
        return "recruiter-dashboard";
    }
    @GetMapping("/applicants")
    public String applicantsPage() {
        return "applicants";
    }
    @GetMapping("/job-details")
    public String jobDetailsPage() {
        return "job-details";
    }
}