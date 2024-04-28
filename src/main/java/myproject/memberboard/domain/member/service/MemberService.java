package myproject.memberboard.domain.member.service;

import myproject.memberboard.domain.member.Member;

import java.util.List;

public interface MemberService {

    void join(Member member);
    List<Member> findAllMember();
    Member findById(Long id);
    Member findByLoginId(String loginId);
    Member updateMember(Long id, Member member);
    boolean deleteMember(Long id);
}
