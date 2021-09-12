package com.ntrpk.schoolregistration.service;

import com.ntrpk.schoolregistration.dto.StudentDTO;
import com.ntrpk.schoolregistration.exception.ValidationException;
import com.ntrpk.schoolregistration.mapper.EntityMapper;
import com.ntrpk.schoolregistration.model.Course;
import com.ntrpk.schoolregistration.model.Student;
import com.ntrpk.schoolregistration.repository.CourseRepository;
import com.ntrpk.schoolregistration.repository.StudentRepository;
import com.ntrpk.schoolregistration.validator.CourseValidator;
import com.ntrpk.schoolregistration.validator.StudentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentValidator studentValidator;

    @Autowired
    private EntityMapper mapper;

    public StudentDTO createStudent(StudentDTO studentDTO) {
        LOGGER.info("Creating new student.");
        studentValidator.validateForCreate(studentDTO);
        Student student = Optional.of(studentDTO).map(mapper::fromStudentDto).map(studentRepository::save).get();
        LOGGER.info("Created new student with id: {}", student.getId());
        return mapper.toStudentDto(student);
    }

    public StudentDTO updateStudent(StudentDTO studentDTO) {
        LOGGER.info("Updating student with id: {}", studentDTO.getId());
        studentValidator.validateForUpdate(studentDTO);
        Student student = studentRepository.getById(studentDTO.getId());
        if (StringUtils.hasText(studentDTO.getEmail())) {
            student.setEmail(studentDTO.getEmail());
        }
        if (StringUtils.hasText(studentDTO.getMobileNumber())) {
            student.setMobileNumber(studentDTO.getMobileNumber());
        }
        if (StringUtils.hasText(studentDTO.getPassword())) {
            student.setPassword(studentDTO.getPassword());
        }
        if (StringUtils.hasText(studentDTO.getFirstName())) {
            student.setFirstName(studentDTO.getFirstName());
        }
        if (StringUtils.hasText(studentDTO.getLastName())) {
            student.setLastName(studentDTO.getLastName());
        }
        LOGGER.info("Updated student with id: {}", student.getId());
        return mapper.toStudentDto(studentRepository.save(student));
    }

    public StudentDTO getStudentById(long id) {
        LOGGER.info("Getting student by id: {}", id);
        studentValidator.validateForExistence(id);
        return studentRepository.findById(id).map(mapper::toStudentDtoWithCourses).get();
    }

    public List<StudentDTO> getAllStudents() {
        LOGGER.info("Fetching all students");
        return studentRepository.findAll().stream().map(mapper::toStudentDto).collect(Collectors.toList());
    }

    public void deleteStudentById(long id) {
        LOGGER.info("Deleting student by id: {}", id);
        studentValidator.validateForExistence(id);
        Student student = studentRepository.getById(id);
        if (student.getCourseCount() > 0) {
            LOGGER.info("Student {} is registered to courses {}. De registering from them", student.getId()
                    , student.getCourses().stream().map(Course::getId).collect(Collectors.toSet()));
            courseRepository.saveAll(student.getCourses().stream().map(course -> {
                course.getStudents().remove(student);
                course.setRegisteredStudentsCount(course.getRegisteredStudentsCount() - 1);
                return course;
            }).collect(Collectors.toSet()));
            LOGGER.info("De registered student with id: {} from all his courses.", student.getId());
        }
        studentRepository.delete(student);
        LOGGER.info("Deleted student by id: {}", id);
    }

    public StudentDTO registerToCourse(String studentUserName, long courseId) {
        LOGGER.info("Registering student: {} to course: {}", studentUserName, courseId);
        Student student = studentValidator.validateForCourseRegistration(studentUserName, courseId);
        Course course = courseRepository.getById(courseId);
        student.getCourses().add(course);
        student.setCourseCount(student.getCourseCount() + 1);
        course.setRegisteredStudentsCount(course.getRegisteredStudentsCount() + 1);
        student = studentRepository.save(student);
        LOGGER.info("Registered student: {} to course: {}", student.getId(), courseId);
        return Optional.of(student).map(mapper::toStudentDtoWithCourses).get();
    }

    public List<StudentDTO> getStudentsByCourse(Long courseId) {
        LOGGER.info("Fetching all students for course: {}", courseId);
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ValidationException("Invalid courseId: " + courseId))
                .getStudents().stream().map(mapper::toStudentDto).collect(Collectors.toList());
    }
}