package myproject.memberboard.domain.member.repository;

import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.member.GenderType;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.web.form.UpdateMemberForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 저장_ID로찾기() {
        //give
        Member memberA = new Member();

        // when
        memberA.setLoginId("test");
        memberA.setPassword("123");
        memberA.setMemberName("mark");
        memberA.setRegionTypeCode("ETC");
        memberA.setAge(30);
        memberA.setGenderType(GenderType.MALE);
        memberRepository.save(memberA);
        //then
        Member findMember = memberRepository.findById(memberA.getMemberId()).get();
        assertThat(findMember).isEqualTo(memberA);
    }

    @Test
    void 전체조회_삭제() {
        //give
        Member memberA = new Member();
        Member memberB = new Member();
        memberA.setLoginId("testA");
        memberA.setPassword("123");
        memberA.setMemberName("mark");
        memberA.setRegionTypeCode("ETC");
        memberA.setAge(30);
        memberA.setGenderType(GenderType.MALE);
        memberRepository.save(memberA);
        memberB.setLoginId("testB");
        memberB.setPassword("456");
        memberB.setMemberName("kim");
        memberB.setRegionTypeCode("BUSAN");
        memberB.setAge(20);
        memberB.setGenderType(GenderType.FEMALE);
        memberRepository.save(memberB);

        // when
        memberRepository.delete(memberA.getMemberId());
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    void 수정_로그인ID로찾기() {
        //give
        Member memberA = new Member();
        memberA.setLoginId("testA");
        memberA.setPassword("123");
        memberA.setMemberName("mark");
        memberA.setRegionTypeCode("ETC");
        memberA.setAge(30);
        memberA.setGenderType(GenderType.MALE);
        memberRepository.save(memberA);

        // when
        List<Member> members = memberRepository.findAll();
        Member saveMember = memberRepository.findByLoginId("testA").get();
        UpdateMemberForm form = new UpdateMemberForm();
        form.setRegionTypeCode("SEOUL");
        memberRepository.update(saveMember.getMemberId(), form);
        // then
        Member updateMember = memberRepository.findById(saveMember.getMemberId()).get();
        assertThat(updateMember.getRegionTypeCode()).isEqualTo(form.getRegionTypeCode());
    }
}