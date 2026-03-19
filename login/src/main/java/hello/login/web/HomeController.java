package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        //쿠키값은 string이지만 자동으로 long으로 스프링이 가져와짐?, 쿠키값이 없는 사람도 home으로 들어오기 떄문에 required fasle

        if (memberId == null) {
            return "home";
        }

        //로그인
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember==null){
            return "home";
        }

        //로그인 전용 사용자 홈.
        model.addAttribute("member",loginMember);
        return "loginHome";

    }

    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        //세션 관리자에 저장된 회원 정보를 조회
        Member member = (Member)sessionManager.getSession(request);

        //로그인
        if(member==null){
            return "home";
        }

        //로그인 전용 사용자 홈.
        model.addAttribute("member",member);
        return "loginHome";

    }
}