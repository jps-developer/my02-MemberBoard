package myproject.memberboard.domain.member.service;

import myproject.memberboard.domain.member.Member;
import myproject.memberboard.web.form.UpdateMemberForm;

import java.util.List;

public interface MemberService {

    Member join(Member member);
    List<Member> findAllMember();
    Member findById(Long id);
    Member findByLoginId(String loginId);
    void updateMember(Long id, UpdateMemberForm updateParam);
    void deleteMember(Long id);
}
