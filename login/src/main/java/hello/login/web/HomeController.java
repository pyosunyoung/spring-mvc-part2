package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentResolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

//    @GetMapping("/")
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

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);// 세션을 생성할 필요가 없기 때문에 false
        if (session == null) {
            return "home";
        }
        Object loginMember = session.getAttribute(SessionConst.LOGIN_MEMBER);


        //세션에 회원 데이터가 없으면 Home
        if(loginMember==null){
            return "home";
        }

        //세션이 유지되면 로그인으로 이동.
        model.addAttribute("member",loginMember);
        return "loginHome";

    }

//    @GetMapping("/")
    public String homeLoginV3Spring(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        //세션 attribute 존재 여부 간결화 후 넘겨줌 loginMember에 넣어서
        //즉 이미 로그인된 사용자를 찾을 때는 다음과 같이 사용하면됨. 하지만 이 기능은 세션을 생성하진 않음

        //세션에 회원 데이터가 없으면 Home
        if(loginMember==null){
            return "home";
        }

        //세션이 유지되면 로그인으로 이동.
        model.addAttribute("member",loginMember);
        return "loginHome";

    }

    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(
            @Login Member loginMember, Model model) {
        //세션 attribute 존재 여부 간결화 후 넘겨줌 loginMember에 넣어서
        //즉 이미 로그인된 사용자를 찾을 때는 다음과 같이 사용하면됨. 하지만 이 기능은 세션을 생성하진 않음

        //세션에 회원 데이터가 없으면 Home
        if(loginMember==null){
            return "home";
        }

        //세션이 유지되면 로그인으로 이동.
        model.addAttribute("member",loginMember);
        return "loginHome";

    }
}