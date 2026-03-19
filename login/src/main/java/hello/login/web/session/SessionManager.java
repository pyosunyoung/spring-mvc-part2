package hello.login.web.session;

//세션 관리

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";

    private Map<String, Object> sessionStore = new ConcurrentHashMap<>(); //동시다발적으로 요청이 올때 이 map이 좋음


    /**
     * 세션 생성
     * sessionId 생성 (임의의 추정 불가능한 랜덤 값)
     * 세션 저장소에 sessionId와 보관할 값 저장
     * sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
     */

    public void CreateSession(Object value, HttpServletResponse response) {
        //세션 id를 생성하고, 값을 세션에 저장
         String sessionId = UUID.randomUUID().toString();// uuid를 통해 랜덤값 얻을 수 있음
        sessionStore.put(sessionId, value);

        //쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
//        Cookie[] cookies = request.getCookies();
//        if (cookies == null) {
//            return null;
//        }
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals(SESSION_COOKIE_NAME)) {
//                return sessionStore.get(cookie.getName());
//            }
//        }
//        return null;
    }

    /**
     * 세션 만료
     * 클라이언트가 요청한 sessionId 쿠키의 값으로, 세션 저장소에 보관한 sessionId와 값 제거
     */

    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }


    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }//stream은 배열을 스트림으로 변경해줌 그러면 fliter,reduce 이런거 쓸 수 있는듯?, 그리고 루프를 돌림. map느낌?
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    } // findAny는 순서 상관없이 먼저 나오면 끝, findFirst는 순서대로 돌리다가 나오면 끝


}
