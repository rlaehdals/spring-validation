package com.example.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    @Email(message = "email 형식으로 기입해주세요.")
    public String email;

    @Size(min = 8, max = 16, message = "password의 길이는 8~16자리로 해주세요.")
    public String password;

    @Range(min = 0, max = 100, message = "age는 0~100 사이로 입력해주세요.")
    public Long age;

}
