package br.com.leandro.starwarsapi.validator;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

@Component
public class JavaxValidator<T> {

    private Validator validator;

    public JavaxValidator(Validator validator) {
        this.validator = validator;
    }

    public void validate(T entity) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    public void validate(Collection<T> collection) {
        if(CollectionUtils.isNotEmpty(collection)) {
            for(T entity : collection) {
                this.validate(entity);
            }
        }
    }
}