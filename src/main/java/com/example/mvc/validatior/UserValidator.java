package com.example.mvc.validatior;

import com.example.mvc.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto dto = (UserDto) target;

        int contain1 = StringUtils.countOccurrencesOf(dto.getEmail(), "@");
        int contain2 = StringUtils.countOccurrencesOf(dto.getEmail(), ".com");

        if(contain1!=1 || contain2!=1){
            errors.rejectValue("email","notEmail","email 형식으로 입력해주세요.");
        }

        if(dto.getPassword().length()<8 || dto.getPassword().length()>16){
            errors.rejectValue("password","range","password의 길이는 8~16으로 해주세요.");
        }

        if((dto.getAge()<0 || dto.getAge()>100) || dto.getAge().getClass().isAssignableFrom(Number.class)){
            errors.rejectValue("age","range","age는 0~100 사이로 입력해주세요.");
        }
    }
}
