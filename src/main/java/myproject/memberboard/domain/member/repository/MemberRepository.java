package myproject.memberboard.domain.member.repository;

import myproject.memberboard.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void save(Member member);
    List<Member> findAll();
    Optional<Member> findById(Long id);
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> update(String loginId, Member member);
    boolean delete(String loginId);
}
