package com.ntrpk.schoolregistration.validator;

import com.ntrpk.schoolregistration.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Validator<T, R> {
    R validate(T entity);
}

class CompositeValidator<T> implements Validator<T, List<String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompositeValidator.class);

    private final List<Validator<T, String>> validators;

    CompositeValidator(Validator<T, String>... validators) {
        this.validators = Arrays.asList(validators);
    }

    @Override
    public List<String> validate(T entity) {
        return validators.stream().map(v -> v.validate(entity)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    void validateAndThrow(T entity) {
        List<String> errors = validate(entity);
        if (!errors.isEmpty()) {
            LOGGER.error("Found validation errors: {}", errors);
            throw new ValidationException(errors);
        }
    }
}
