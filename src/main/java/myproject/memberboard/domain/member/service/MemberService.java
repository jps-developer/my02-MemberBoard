package myproject.memberboard.domain.member.service;

import myproject.memberboard.domain.member.Member;

import java.util.List;

public interface MemberService {

    void join(Member member);
    List<Member> findAllMember();
    Member findById(Long id);
    Member findByLoginId(String loginId);
    Member updateMember(String loginId, Member member);
    boolean deleteMember(String loginId);
}
