package hello.exception.servlet;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {
    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("예외 발생!");//exception이 터지면 무조건 500에러
    }

    @GetMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException { //IOException을 넣어야 snedError 가능
        response.sendError(400, "400 오류!"); //send error는 상태코드 지정 가능.
    }


    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException { //IOException을 넣어야 snedError 가능
        response.sendError(404, "404 오류!"); //send error는 상태코드 지정 가능.
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500); //response안에 send error 저장 가능.
    }
}
