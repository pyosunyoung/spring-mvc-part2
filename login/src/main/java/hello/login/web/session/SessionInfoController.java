package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            return "세션이 없습니다";
        }

        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId()); // sessionId = JsessionId?
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval()); // 세션 비활성화 시간?, 세션의 유효 시간, 예) 1800초, (30분)
        log.info("creationTime={}", new Date(session.getCreationTime())); //세션 생성 시간, 기본값이 long이어서 시간으로 변경
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime())); // 마지막 세션 접근 시간
        log.info("isNew={}", session.isNew()); // 새로 생성된 세션이냐? 판별
        return "세션 출력";
        //세션은 최근 접근 시 세션 유효 시간을 늘리는 것으로 세션 유지 가능.
    }
}
