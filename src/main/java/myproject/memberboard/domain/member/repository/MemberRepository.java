package myproject.memberboard.domain.member.repository;

import myproject.memberboard.domain.member.Member;
import myproject.memberboard.web.form.UpdateMemberForm;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void save(Member member);
    List<Member> findAll();
    Optional<Member> findById(Long id);
    Optional<Member> findByLoginId(String loginId);
    void update(Long id, UpdateMemberForm updateParam);
    boolean delete(Long id);
}
