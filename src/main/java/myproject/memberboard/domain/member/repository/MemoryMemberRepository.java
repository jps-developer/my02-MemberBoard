package myproject.memberboard.domain.member.repository;

import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.exception.MemberNotFoundException;
import myproject.memberboard.web.form.UpdateMemberForm;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public void save(Member member) {
        member.setMemberId(++sequence);
        log.info("save Member={}",member);
        store.put(member.getMemberId(), member);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findById(Long id) {
        return findAll().stream()
                .filter(m -> m.getMemberId().equals(id))
                .findAny();
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m->m.getLoginId().equals(loginId))
                .findAny();
    }

    @Override
    public void update(Long id, UpdateMemberForm updateParam) {
        Member updateMember = findById(id).get();
        updateMember.setRegionTypeCode(updateParam.getRegionTypeCode());
    }

    @Override
    public boolean delete(Long id) {
        if(findById(id).isPresent()){
            Member removeMember = findById(id).get();
            store.remove(removeMember.getMemberId());
            return true;
        }else{
            return false;
        }
    }
}
