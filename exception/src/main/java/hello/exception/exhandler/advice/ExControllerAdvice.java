package hello.exception.exhandler.advice;

import hello.exception.api.ApiExceptionV3Controller;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "hello.exception.api")
// 예외 코드
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExceptionHandler(IllegalArgumentException e) { //IllegalArgumentException의 자식 까지 예외를 잡아줌
        log.error("[illegalExceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }//이 컨트롤러 에서 해당 ex가 발생하면 이 로직이 실행되고 해당 ErrorResult는 json으로 반환됨.
    //ExceptionHandler가 가장 우선 순위가 높다.

    @ExceptionHandler// userException으로 매개변수 설정하면 해당 class 생략 가능?
    public ResponseEntity<ErrorResult> userExHandler(UserException e) { //UserException의 자식 까지 예외를 잡아줌
        log.error("[userExHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler // ExceptionHandler는 해당 컨트롤러 에서만 적용이 됨.
    public ErrorResult exHandler(Exception e) { //Exception? 최상위 예외임. 그래서 위에서 생성해준 IllegalArgumentException, UserException이 잡지 못한 예외들을 모두 잡아줌
        log.error("[exHandler] ex", e); // Exception은 모든 부모의 예외인듯?
        return new ErrorResult("EX", "내부 오류");
    } // 부모, 자식 예외 둘다 있을 떄 부모는 자식 까지 예외 처리 가능하지만 자식은 자기 자신만 예외 처리 가능 하지만
    // 둘다 있을 떄 자식은 자식이 더 자세한 것이 우선권을 가져 자식 예외처리가 우선권을 가짐.
}
