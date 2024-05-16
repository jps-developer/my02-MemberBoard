package myproject.memberboard.domain.member.repository.mybatis;

import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.web.form.UpdateMemberForm;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisMemberRepository implements MemberRepository {

    private final MemberMapper memberMapper;
    @Override
    public void save(Member member) {
        memberMapper.save(member);
    }

    @Override
    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberMapper.findById(id);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return memberMapper.findByLoginId(loginId);
    }

    @Override
    public void update(Long id, UpdateMemberForm updateParam) {
        memberMapper.update(id, updateParam);
    }

    @Override
    public boolean delete(Long id) {
        memberMapper.delete(id);
        return true;
    }
}
