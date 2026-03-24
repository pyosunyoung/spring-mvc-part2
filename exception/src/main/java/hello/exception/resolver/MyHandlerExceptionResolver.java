package hello.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    @Nullable
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {


        try{
            if(ex instanceof IllegalArgumentException) { //IllegalArgument는 클라이언트가 잘못 argument를 입력한것
                log.info("Illegal argument exception resolver to 400"); // 기본값은 원래 ex는 500오류인데 400오류로 바꿔주겠다로 정으.
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage()); //400으로 설정.
                return new ModelAndView(); //ModelAndView를 새로운 모델 앤 뷰를 넘겨주고 was까지 정상적으로 리턴됨.was 정상 응답 됨 그림 참고 view를 안넘기고 정상응답 그리고 exception을 안넘기고
            }
        }catch (IOException e){
            log.error("resolver ex", e);
            e.printStackTrace(); //이 예외를 먹어버림.
        }


        return null;
        //어떠한 ex이 넘어오면 해당 ModelAndView를 반환시킨다.
    }

//    ExceptionResolver 가 ModelAndView 를 반환하는 이유는 마치 try, catch를 하듯이, Exception 을 처
//    리해서 정상 흐름 처럼 변경하는 것이 목적이다. 이름 그대로 Exception 을 Resolver(해결)하는 것이 목적이
//    다.
//    여기서는 IllegalArgumentException 이 발생하면 response.sendError(400) 를 호출해서 HTTP 상태
//    코드를 400으로 지정하고, 빈 ModelAndView 를 반환한다.

//    빈 ModelAndView: new ModelAndView() 처럼 빈 ModelAndView 를 반환하면 뷰를 렌더링 하지 않고,
//    정상 흐름으로 서블릿이 리턴된다.
//    ModelAndView 지정: ModelAndView 에 View , Model 등의 정보를 지정해서 반환하면 뷰를 렌더링 한
//    다.
//null: null 을 반환하면, 다음 ExceptionResolver 를 찾아서 실행한다. 만약 처리할 수 있는
//    ExceptionResolver 가 없으면 예외 처리가 안되고, 기존에 발생한 예외를 서블릿 밖으로 던진다. 즉 was까지 접근하여 500에러가 출력됨.
}
