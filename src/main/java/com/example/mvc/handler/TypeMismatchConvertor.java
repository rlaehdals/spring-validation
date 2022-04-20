package com.example.mvc.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class TypeMismatchConvertor {
    private final Map<String, String> typeMismatchMessages = new HashMap<>();


    @PostConstruct
    public void init(){
        typeMismatchMessages.put("age", "나이는 숫자로 입력해주세요.");
    }

    public Result convert(String originalMessage){
        int chain = originalMessage.lastIndexOf("[");
        String prefix = originalMessage.substring(chain);
        String resultString = prefix.substring(2, prefix.length() - 3);
        return new Result(resultString, typeMismatchMessages.get(resultString));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result{
        String field;
        String description;

    }
}
