package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L;
    public MemberRepository() {

    }

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save member: {}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // MAP안에 Member가 list로 변하여 반환됨.
    }

    public Optional<Member> findByLoginId(String loginId) { //optional?
//        List<Member> all = findAll();
//        for (Member member : all) {
//            if (member.getLoginId().equals(loginId)) {
//                return Optional.of(member); // 로그인한 아이디와 member에 들어있는 id비교분석
//            }
//        }
//        return Optional.empty(); // 못 찾으면 null 느낌.

        return findAll().stream().filter(member -> member.getLoginId().equals(loginId)).findFirst();
        //이게 위에 것과 동일한 로직임.
        //list를 stream으로 바꾸고, 루프를 돌면서 해당 로그인한 id와 member에 들어있는 id가 같다면 그것을 첫번쨰로 내보내라 이런 느낌임.

    } //optinoal이란 것은 그 안에 회원 객체가 있을 수도 있고 없을 수도 있고, 껍데기 통이라고 보면 됨.
    //요즘엔 null 대신 optional.empty로 반환한다?

    public void clearStore() {
        store.clear();
    }
}
