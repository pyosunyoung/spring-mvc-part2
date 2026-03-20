package hello.login.web.argumentResolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class); // 이 @Login이 있느냐 여부
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());// 파라미터 타입 즉 member class를 가져오는데 해당이 Member 타입이 맞ㄴ냐 여부
        return hasLoginAnnotation && hasMemberType; // 두개를 다 만족하면 true => resloverArgument로 넘어감?
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        log.info("resolverArgument 실행");

       HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest(); // 이렇게 접근하면 httprequest를 뽑을 수 있음
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
        //session에 값이 있으면 member 없으면 null 반환
    }
    // 이 과정 모두 @Login이 호출 되었을 때 발생.
}
