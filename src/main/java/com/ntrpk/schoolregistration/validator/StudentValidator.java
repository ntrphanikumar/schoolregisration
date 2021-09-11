package com.ntrpk.schoolregistration.validator;

import com.ntrpk.schoolregistration.dto.StudentDTO;
import com.ntrpk.schoolregistration.exception.ValidationException;
import com.ntrpk.schoolregistration.model.Course;
import com.ntrpk.schoolregistration.model.Student;
import com.ntrpk.schoolregistration.repository.CourseRepository;
import com.ntrpk.schoolregistration.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StudentValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentValidator.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Value("${application.course.max_students}")
    private int maxStudentsPerCourse;

    @Value("${application.student.max_courses}")
    private int maxCoursesPerStudent;

    private Validator<StudentDTO, String> nonEmptyUsername = student -> StringUtils.isEmpty(student.getUsername())
            ? "Username is mandatory" : null;
    private Validator<StudentDTO, String> nonEmptyPassword = student -> StringUtils.isEmpty(student.getPassword())
            ? "Password is mandatory" : null;
    private Validator<StudentDTO, String> nonEmptyEmail = student -> StringUtils.isEmpty(student.getEmail())
            ? "E-Mail is mandatory" : null;
    private Validator<StudentDTO, String> nonEmptyFirstName = student -> StringUtils.isEmpty(student.getFirstName())
            ? "First name is mandatory" : null;
    private Validator<StudentDTO, String> nonEmptyLastName = student -> StringUtils.isEmpty(student.getLastName())
            ? "Last name is mandatory" : null;
    private Validator<StudentDTO, String> uniqueUsername = student -> !StringUtils.isEmpty(student.getUsername())
            && studentRepository.findByUsername(student.getUsername()).isPresent() ? "Username already in use." : null;
    private Validator<StudentDTO, String> studentExists = studentDto -> studentRepository.existsById(studentDto.getId())
            ? null : "No Student found";

    private CompositeValidator<StudentDTO> createStudentValidator = new CompositeValidator<>(nonEmptyFirstName,
            nonEmptyLastName, nonEmptyEmail, nonEmptyUsername, nonEmptyPassword, uniqueUsername);
    private CompositeValidator<StudentDTO> studentExistsValidator = new CompositeValidator<>(studentExists);


    public void validateForCreate(StudentDTO studentDTO) {
        LOGGER.info("Validating to create student.");
        createStudentValidator.validateAndThrow(studentDTO);
        LOGGER.info("Validation successful for creating new student.");
    }

    public void validateForUpdate(StudentDTO studentDTO) {
        LOGGER.info("Validation for update student with id: {}", studentDTO.getId());
        studentExistsValidator.validateAndThrow(studentDTO);
        LOGGER.info("Validation successful for update student with id: {}", studentDTO.getId());
    }

    public void validateForExistence(Long studentId) {
        LOGGER.info("Validation for existence of student with id: {}", studentId);
        studentExistsValidator.validateAndThrow(new StudentDTO().setId(studentId));
    }

    public Student validateForCourseRegistration(String studentUserName, Long courseId) {
        Student student = studentRepository.findByUsername(studentUserName).orElseThrow(() -> new ValidationException("No Student found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ValidationException("No Course found"));
        if (student.getCourseCount() == maxCoursesPerStudent) {
            throw new ValidationException("Student already registered to max allowed courses.");
        }
        if (course.getRegisteredStudentsCount() == maxStudentsPerCourse) {
            throw new ValidationException("Course already had max allowed students.");
        }
        return student;
    }
}
