package hello.typeconverter.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class FormatterController {

    @GetMapping("/formatter/edit")
    public String formatterForm(Model model) {
        Form form = new Form();
        form.setNumber(10000);
        form.setLocalDateTime(LocalDateTime.now());
        model.addAttribute("form", form);
        return "formatter-form";

    }

    @PostMapping("/formatter/edit")
    public String formatterSubmit(@ModelAttribute Form form) {
        return "formatter-view";
    }

    @Data
    static class Form{
        @NumberFormat(pattern = "###,###")
        private Integer number;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;
    }
}

//@NumberFormat : 숫자 관련 형식 지정 포맷터 사용, NumberFormatAnnotationFormatterFactory, 문자 => 숫자로
//@DateTimeFormat : 날짜 관련 형식 지정 포맷터 사용, 문자 => date 객체로?
//Jsr310DateTimeFormatAnnotationFormatterFactory