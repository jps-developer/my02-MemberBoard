package myproject.memberboard.domain.member.repository.jpa;

import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.web.form.UpdateMemberForm;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Repository
public class SpringDataDIJpaMemberRepository implements MemberRepository {

    private final SpringDataJpaMemberRepository repository;

    @Override
    public void save(Member member) {
        repository.save(member);
    }

    @Override
    public List<Member> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return repository.findByLoginId(loginId);
    }

    @Override
    public void update(Long id, UpdateMemberForm updateParam) {
        Member member = repository.findById(id).get();
        member.setRegionTypeCode(updateParam.getRegionTypeCode());
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }
}
