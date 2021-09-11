package com.ntrpk.schoolregistration.controller;

import com.ntrpk.schoolregistration.dto.CourseDTO;
import com.ntrpk.schoolregistration.dto.StudentDTO;
import com.ntrpk.schoolregistration.service.CourseService;
import com.ntrpk.schoolregistration.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDto) {
        return ResponseEntity.ok(studentService.createStudent(studentDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable(value = "id") Long id, @RequestBody StudentDTO studentDto) {
        return ResponseEntity.ok(studentService.updateStudent(studentDto.setId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok().build();
    }
}
