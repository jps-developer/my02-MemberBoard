package myproject.memberboard;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.member.GenderType;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init(){
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("123");
        member.setMemberName("mark");
        member.setRegionTypeCode("ETC");
        member.setAge(30);
        member.setGenderType(GenderType.MALE);
        memberRepository.save(member);
    }
}
