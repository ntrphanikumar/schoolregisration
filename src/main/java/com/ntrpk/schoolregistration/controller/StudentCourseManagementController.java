package com.ntrpk.schoolregistration.controller;

import com.ntrpk.schoolregistration.dto.StudentDTO;
import com.ntrpk.schoolregistration.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasRole('ROLE_STUDENT')")
public class StudentCourseManagementController {
    @Autowired
    private StudentService studentService;

    @PostMapping("registerTo/{courseId}")
    public ResponseEntity<StudentDTO> registerToCourse(@PathVariable(value = "courseId") Long courseId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(studentService.registerToCourse(userDetails.getUsername(), courseId));
    }
}
