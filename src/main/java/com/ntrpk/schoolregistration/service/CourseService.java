package com.ntrpk.schoolregistration.service;

import com.ntrpk.schoolregistration.dto.CourseDTO;
import com.ntrpk.schoolregistration.exception.ValidationException;
import com.ntrpk.schoolregistration.mapper.EntityMapper;
import com.ntrpk.schoolregistration.model.Course;
import com.ntrpk.schoolregistration.repository.CourseRepository;
import com.ntrpk.schoolregistration.repository.StudentRepository;
import com.ntrpk.schoolregistration.validator.CourseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseValidator courseValidator;

    @Autowired
    private EntityMapper mapper;

    public CourseDTO createCourse(CourseDTO courseDTO) {
        LOGGER.info("Creating course");
        courseValidator.validateForCreate(courseDTO);
        Course course = Optional.of(courseDTO).map(mapper::fromCourseDto).map(courseRepository::save).get();
        LOGGER.info("Created course with id: {}", course.getId());
        return mapper.toCourseDto(course);
    }

    public CourseDTO updateCourse(CourseDTO courseDTO) {
        LOGGER.info("Updating course: {}", courseDTO.getId());
        courseValidator.validateForUpdate(courseDTO);
        Course course = courseRepository.getById(courseDTO.getId());
        if (StringUtils.hasText(courseDTO.getDescription())) {
            course.setDescription(courseDTO.getDescription());
        }
        LOGGER.info("Updated course with id: {}", course.getId());
        return mapper.toCourseDto(courseRepository.save(course));
    }

    public CourseDTO getCourse(long courseId) {
        LOGGER.info("Retrieving course by id: {}", courseId);
        courseValidator.validateForExistence(courseId);
        return courseRepository.findById(courseId).map(mapper::toCourseDtoWithStudents).get();
    }

    public List<CourseDTO> getAllCourses() {
        LOGGER.info("Fetching all courses");
        return courseRepository.findAll().stream().map(mapper::toCourseDto).collect(Collectors.toList());
    }

    public void deleteCourse(long courseId) {
        LOGGER.info("Deleting course with id: {}", courseId);
        courseValidator.validateForExistence(courseId);
        Course course = courseRepository.getById(courseId);
        if (course.getRegisteredStudentsCount() > 0) {
            LOGGER.info("Students are registered to the course {}. De registering them first", courseId);
            studentRepository.saveAll(course.getStudents().stream().map(student -> {
                student.getCourses().remove(course);
                student.setCourseCount(student.getCourseCount() - 1);
                return student;
            }).collect(Collectors.toSet()));
            LOGGER.info("De registered all students from the course: {}", courseId);
        }
        courseRepository.delete(course);
        LOGGER.info("Deleted course with id: {}", courseId);
    }

    public List<CourseDTO> getCoursesByStudent(Long studentId) {
        LOGGER.info("Fetching all courses for student: {}", studentId);
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ValidationException("Invalid studentId: " + studentId))
                .getCourses().stream().map(mapper::toCourseDto).collect(Collectors.toList());
    }
}
