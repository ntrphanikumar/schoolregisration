package com.ntrpk.schoolregistration.validator;

import com.ntrpk.schoolregistration.dto.CourseDTO;
import com.ntrpk.schoolregistration.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CourseValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseValidator.class);

    @Autowired
    private CourseRepository courseRepository;

    private Validator<CourseDTO, String> nonEmptyName = course -> StringUtils.isEmpty(course.getName())
            ? "Course name is mandatory" : null;
    private Validator<CourseDTO, String> nonEmptyDescription = course -> StringUtils.isEmpty(course.getDescription())
            ? "Description is mandatory" : null;
    private Validator<CourseDTO, String> uniqueName = course -> !StringUtils.isEmpty(course.getName())
            && courseRepository.findByName(course.getName()).isPresent() ? "Name already in use." : null;
    private Validator<CourseDTO, String> courseExists = course -> courseRepository.existsById(course.getId())
            ? null : "No Course found";

    private CompositeValidator<CourseDTO> createCourseValidator = new CompositeValidator<>(nonEmptyName,
            nonEmptyDescription, uniqueName);
    private CompositeValidator<CourseDTO> courseExistsValidator = new CompositeValidator<>(courseExists);


    public void validateForCreate(CourseDTO courseDTO) {
        LOGGER.info("Validating to create course.");
        createCourseValidator.validateAndThrow(courseDTO);
        LOGGER.info("Validation successful for creating new course.");
    }

    public void validateForUpdate(CourseDTO courseDTO) {
        LOGGER.info("Validation for update course with id: {}", courseDTO.getId());
        courseExistsValidator.validateAndThrow(courseDTO);
        LOGGER.info("Validation successful for update course with id: {}", courseDTO.getId());
    }

    public void validateForExistence(Long courseId) {
        LOGGER.info("Validation for existence of course with id: {}", courseId);
        courseExistsValidator.validateAndThrow(new CourseDTO().setId(courseId));
    }
}