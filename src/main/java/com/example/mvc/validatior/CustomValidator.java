package com.example.mvc.validatior;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.validation.Validation;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
public class CustomValidator implements Validator {

    private SpringValidatorAdapter validator;

    public CustomValidator(SpringValidatorAdapter validator) {
        this.validator = new SpringValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Collections.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Collection col = (Collection) target;

        for(Object now: col){
            validator.validate(now,errors);
        }
    }
}
