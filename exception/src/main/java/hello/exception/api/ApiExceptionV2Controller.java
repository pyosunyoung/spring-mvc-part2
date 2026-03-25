package hello.exception.api;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//다른 컨트롤러에 적용하고 싶다? @ControllerAdvice gogo
//해당 부분은 정상 코드
@Slf4j
@RestController
public class ApiExceptionV2Controller {

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) { //memberDto 반환 api
        if (id.equals("ex")) { //ExceptionHandler exception 발생 시 여기서 위 Exception e 가 실행됨.
            throw new RuntimeException("잘못된 사용자");
        }

        if(id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값"); //기본 에러값은 500
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello" + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String memberId;
        private String name;
    }
}

// 컨트롤러에서 터지면 exception핸들러 리조버가 해당 애노테이션 @ExceptionHandler 호출
// 정상 호출로 이루어지기 때문에 200으로 설정되어 responseStatus 애노테이션을 통해 변경해줌
// 즉 정상 흐름 반환이기 때문에 막 서블릿 컨테이너가서 다시 오류 경로로 다시 에러를 보내주는 그런 불상사를 해결 가능해짐