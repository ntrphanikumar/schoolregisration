package com.ntrpk.schoolregistration.service;

import com.ntrpk.schoolregistration.dto.StudentDTO;
import com.ntrpk.schoolregistration.mapper.EntityMapper;
import com.ntrpk.schoolregistration.model.Course;
import com.ntrpk.schoolregistration.model.Student;
import com.ntrpk.schoolregistration.repository.CourseRepository;
import com.ntrpk.schoolregistration.repository.StudentRepository;
import com.ntrpk.schoolregistration.validator.StudentValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentValidator studentValidator;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void testCreateStudent() {
        StudentDTO studentDTO = Mockito.mock(StudentDTO.class);
        Student student = Mockito.mock(Student.class);
        Mockito.when(mapper.fromStudentDto(studentDTO)).thenReturn(student);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        Mockito.when(mapper.toStudentDto(student)).thenReturn(studentDTO);
        Assert.assertEquals(studentDTO, studentService.createStudent(studentDTO));
        Mockito.verify(studentValidator).validateForCreate(studentDTO);
    }

    @Test
    public void testUpdateStudent() {
        StudentDTO studentDTO = Mockito.mock(StudentDTO.class);
        Student student = Mockito.mock(Student.class);
        Mockito.when(studentRepository.getById(studentDTO.getId())).thenReturn(student);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        Mockito.when(mapper.toStudentDto(student)).thenReturn(studentDTO);
        Assert.assertEquals(studentDTO, studentService.updateStudent(studentDTO));
        Mockito.verify(studentValidator).validateForUpdate(studentDTO);
    }

    @Test
    public void testRegisterToCourse() {
        Student student = new Student();
        Course course = new Course();
        Mockito.when(studentValidator.validateForCourseRegistration("username", 1L)).thenReturn(student);
        Mockito.when(courseRepository.getById(1L)).thenReturn(course);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        StudentDTO studentDTO = new StudentDTO().setId(1L);
        Mockito.when(mapper.toStudentDtoWithCourses(student)).thenReturn(studentDTO);
        Assert.assertEquals(studentDTO, studentService.registerToCourse("username", 1L));
        Assert.assertEquals(1, student.getCourseCount());
        Assert.assertEquals(1, course.getRegisteredStudentsCount());
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student();
        student.setCourseCount(1);
        Course course = new Course();
        course.setRegisteredStudentsCount(3);
        student.getCourses().add(course);
        Mockito.when(studentRepository.getById(1L)).thenReturn(student);
        studentService.deleteStudentById(1);
        Assert.assertEquals(2, course.getRegisteredStudentsCount());
    }
}
