package com.ntrpk.schoolregistration.mapper;

import com.ntrpk.schoolregistration.dto.CourseDTO;
import com.ntrpk.schoolregistration.dto.StudentDTO;
import com.ntrpk.schoolregistration.model.Course;
import com.ntrpk.schoolregistration.model.Student;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityMapper {

    public Student fromStudentDto(StudentDTO dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setUsername(dto.getUsername());
        student.setPassword(dto.getPassword());
        student.setEmail(dto.getEmail());
        student.setMobileNumber(dto.getMobileNumber());
        return student;
    }

    public Course fromCourseDto(CourseDTO dto) {
        Course course = new Course();
        course.setId(dto.getId());
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        return course;
    }

    public StudentDTO toStudentDto(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setUsername(student.getUsername());
        studentDTO.setMobileNumber(student.getMobileNumber());
        return studentDTO;
    }

    public StudentDTO toStudentDtoWithCourses(Student student) {
        StudentDTO studentDTO = toStudentDto(student);
        if (student.getCourseCount() > 0) {
            studentDTO.setCourses(student.getCourses().stream().map(this::toCourseDto).collect(Collectors.toSet()));
        }
        return studentDTO;
    }

    public CourseDTO toCourseDto(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setDescription(course.getDescription());
        return courseDTO;
    }

    public CourseDTO toCourseDtoWithStudents(Course course) {
        CourseDTO courseDTO = toCourseDto(course);
        if (course.getRegisteredStudentsCount() > 0) {
            courseDTO.setStudents(course.getStudents().stream().map(this::toStudentDto).collect(Collectors.toSet()));
        }
        return courseDTO;
    }
}
