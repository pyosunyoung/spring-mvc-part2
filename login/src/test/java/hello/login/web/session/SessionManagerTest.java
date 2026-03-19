package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class SessionManagerTest {
    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        //세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse(); // 가짜로 http response 테스트 mock
        Member member = new Member();
        sessionManager.CreateSession(member, response);

        //요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());//mysessionId=1234dfgd13-12354

        //세션 조회
        Object result = sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo(member); // 세션 데이터와 가져온 member 데이터가 같은지

        //세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        Assertions.assertThat(expired).isNull();

    }
}
