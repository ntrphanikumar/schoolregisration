package com.ntrpk.schoolregistration.validator;

import com.ntrpk.schoolregistration.exception.ValidationException;
import com.ntrpk.schoolregistration.model.Course;
import com.ntrpk.schoolregistration.model.Student;
import com.ntrpk.schoolregistration.repository.CourseRepository;
import com.ntrpk.schoolregistration.repository.StudentRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StudentValidatorTest {
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Value("${application.course.max_students}")
    private int maxStudentsPerCourse;

    @Value("${application.student.max_courses}")
    private int maxCoursesPerStudent;

    @InjectMocks
    private StudentValidator studentValidator;

    @Test
    public void testValidateForCourseRegistrationShouldThrowValidationExceptionIfStudentIsNotFound() {
        Mockito.when(studentRepository.findByUsername("name")).thenReturn(Optional.empty());
        Assertions.assertThrows(ValidationException.class, () -> {
            studentValidator.validateForCourseRegistration("name", 1L);
        });
    }

    @Test
    public void testValidateForCourseRegistrationShouldThrowValidationExceptionIfCourseIsNotFound() {
        Student student = Mockito.mock(Student.class);
        Mockito.when(studentRepository.findByUsername("name")).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ValidationException.class, () -> {
            studentValidator.validateForCourseRegistration("name", 1L);
        });
    }

    @Test
    public void testValidateForCourseRegistrationShouldThrowValidationExceptionIfMaxCoursesForStudentIsReached() {
        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Mockito.when(studentRepository.findByUsername("name")).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(student.getCourseCount()).thenReturn(maxCoursesPerStudent);
        Assertions.assertThrows(ValidationException.class, () -> {
            studentValidator.validateForCourseRegistration("name", 1L);
        });
    }

    @Test
    public void testValidateForCourseRegistrationShouldThrowValidationExceptionIfMaxStudentsForCourseIsReached() {
        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Mockito.when(studentRepository.findByUsername("name")).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(student.getCourseCount()).thenReturn(maxCoursesPerStudent - 1);
        Mockito.when(course.getRegisteredStudentsCount()).thenReturn(maxStudentsPerCourse);
        Assertions.assertThrows(ValidationException.class, () -> {
            studentValidator.validateForCourseRegistration("name", 1L);
        });
    }

    @Test
    public void testValidateForCourseRegistrationShouldReturnStudentWhenAllValidationsPassed() {
        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Mockito.when(studentRepository.findByUsername("name")).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(student.getCourseCount()).thenReturn(maxCoursesPerStudent - 1);
        Mockito.when(course.getRegisteredStudentsCount()).thenReturn(maxStudentsPerCourse - 1);
        Assert.assertEquals(student, studentValidator.validateForCourseRegistration("name", 1L));
    }
}
