package com.ntrpk.schoolregistration.controller;


import com.ntrpk.schoolregistration.dto.CourseDTO;
import com.ntrpk.schoolregistration.dto.StudentDTO;
import com.ntrpk.schoolregistration.service.CourseService;
import com.ntrpk.schoolregistration.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ReportController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/studentsFor/{courseId}")
    public ResponseEntity<List<StudentDTO>> getStudentsByCourse(@PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(studentService.getStudentsByCourse(courseId));
    }

    @GetMapping("/coursesFor/{studentId}")
    public ResponseEntity<List<CourseDTO>> getCoursesForStudent(@PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok(courseService.getCoursesByStudent(studentId));
    }

    @GetMapping("/listCourses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/listStudents")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
}
