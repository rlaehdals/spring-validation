package com.example.mvc.controller;

import com.example.mvc.dto.UserDto;
import com.example.mvc.validatior.CustomValidator;
import com.example.mvc.validatior.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final CustomValidator customValidator;

//    @InitBinder
    public void init(DataBinder binder){
        binder.addValidators(userValidator);
    }
    private final UserValidator userValidator;

    @PostMapping("/basic")
    public ResponseEntity<?> basic(@RequestBody UserDto dto){
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PostMapping("/validation/v1")
    public ResponseEntity<?> validationV1(@RequestBody UserDto dto){

        HashMap<String, String> errors = new HashMap<>();


        int contain1 = StringUtils.countOccurrencesOf(dto.getEmail(), "@");
        int contain2 = StringUtils.countOccurrencesOf(dto.getEmail(), ".com");

        if(contain1!=1 || contain2!=1){
            errors.put("email", "email 형식으로 기입해주세요.");
        }

        if(dto.getPassword().length()<8 || dto.getPassword().length()>16){
            errors.put("password", "password의 길이는 8~16자리로 해주세요.");
        }

        if(dto.getAge()<0 || dto.getAge()>100){
            errors.put("age","age는 0~100 사이로 입력해주세요.");
        }

        if(errors.size()!=0){
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PostMapping("/validation/v2")
    public String validationV2(@RequestBody UserDto dto, BindingResult bindingResult){



        int contain1 = StringUtils.countOccurrencesOf(dto.getEmail(), "@");
        int contain2 = StringUtils.countOccurrencesOf(dto.getEmail(), ".com");

        if(contain1!=1 || contain2!=1){
            bindingResult.rejectValue("email","notEmail","email 형식으로 입력해주세요.");

        }

        if(dto.getPassword().length()<8 || dto.getPassword().length()>16){
            bindingResult.rejectValue("password","range","password의 길이는 8~16으로 해주세요.");
        }

        if(dto.getAge()<0 || dto.getAge()>100){
            bindingResult.rejectValue("age","range","age는 0~100 사이로 입력해주세요.");
        }

        if(bindingResult.hasErrors()){
            return "/page";  // 원래 페이지로 다시 이동 임의로 page로 설정한 것임
        }

        return "redirect:/"; // 정상 동작 임의로 /로 리다이렉팅한 것임
    }
    @PostMapping("/validation/v3")
    public String validationV3(@RequestBody UserDto dto, BindingResult bindingResult) {

        if (userValidator.supports(UserDto.class)) {
            userValidator.validate(dto, bindingResult);
        }

        if(bindingResult.hasErrors()){
            return "/page";  // 원래 페이지로 다시 이동 임의로 page로 설정한 것임
        }

        return "redirect:/"; // 정상 동작 임의로 /로 리다이렉팅한 것임
    }

    @PostMapping("/validation/v4")
    public String validationV4(@Validated @ModelAttribute UserDto dto, BindingResult bindingResult) {

        System.out.println(bindingResult.getAllErrors().size());
        if(bindingResult.hasErrors()){
            return "/page";  // 원래 페이지로 다시 이동 임의로 page로 설정한 것임
        }
        return "redirect:/"; // 정상 동작 임의로 /로 리다이렉팅한 것임
    }
    @PostMapping("/validation/v5")
    public ResponseEntity<?> validationV5(@Validated @RequestBody UserDto dto, BindingResult bindingResult){

        HashMap<String, Object> errors = new HashMap<>();

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().stream()
                    .forEach(a->{
                        String field = ((FieldError) a).getField();
                        String defaultMessage = a.getDefaultMessage();
                        errors.put(field, defaultMessage);
                    });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PostMapping("/validation/v6")
    public ResponseEntity<?> validationV6(@Validated @RequestBody UserDto dto){
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PostMapping("/validation/v7")
    public ResponseEntity<?> validationV7(@Valid @RequestBody List<UserDto> dto, BindingResult bindingResult){
        customValidator.validate(dto,bindingResult);

        HashMap<String, Object> errors = new HashMap<>();
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().stream()
                    .forEach(a->{
                        String field = ((FieldError) a).getField();
                        String defaultMessage = a.getDefaultMessage();
                        errors.put(field, defaultMessage);
                    });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
