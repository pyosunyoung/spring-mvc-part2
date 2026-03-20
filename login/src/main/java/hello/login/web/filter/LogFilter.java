package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LogFilter init"); // 필터 초기화 메서드, 서블릿 컨테이너가 생성될 때 호출된다.
    }

    @Override
    public void destroy() {
        log.info("LogFilter destroy"); // 필터 종료? 서블릿 컨테이너가 종료될 때 호출된다.
    }

    //servletRequet는 httpServletRequest의 부모임.
    //고객의 요청이 올 때 마다 해당 메서드가 호출된다. 필터의 로직을 구현하면 된다., HTTP 요청이 오면 doFilter 가 호출된다
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("LogFilter doFilter"); // 필터 동작?

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString(); //HTTP 요청을 구분하기 위해 요청당 임의의 uuid 를 생성해둔다.
        try{
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            filterChain.doFilter(servletRequest,servletResponse);
            //. 다음 필터가 있으면 필터를 호출하고, 필터가 없으면 서블릿을 호출한다
        } catch (Exception e){
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }
}
