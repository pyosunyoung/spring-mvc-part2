package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Nullable
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {
        try{

            if(ex instanceof UserException){
                log.info("UserException resolver tp 400");

                String acceptheader = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //응답 400

                if("application/json".equals(acceptheader)){
                    Map<String,Object> errorResult = new HashMap<>();
                    errorResult.put("ex",ex.getClass());
                    errorResult.put("message",ex.getMessage()); // 예외 관련 class와 메시지 값을 따로 errorReuslt에 보관해둠

                    String result = objectMapper.writeValueAsString(errorResult); // json을 문자로 바꿔주는 로직

                    response.setContentType("application/json"); //그리고 response로 응답 보낼준비
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(errorResult.toString()); //해당 화면에 출력
                    response.getWriter().write(result);
                    return new ModelAndView();

                } else {
                    //TEXT HTML일 경우
                    return new ModelAndView("error/500"); //템플릿에 에러 500 html이 출력됨.
                }
            }

        }catch(IOException e){
            log.error("resolver ex", e);
        }

        return null;
    }
}
