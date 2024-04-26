package myproject.memberboard.domain.member.repository;

import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public void save(Member member) {
        member.setId(++sequence);
        log.info("save Member={}",member);
        store.put(member.getId(), member);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findById(Long id) {
        return findAll().stream()
                .filter(m -> m.getId().equals(id))
                .findAny();
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m->m.getLoginId().equals(loginId))
                .findAny();
    }

    @Override
    public Optional<Member> update(String loginId, Member member) {
        if(findByLoginId(loginId).isPresent()){
            Member existMember = findByLoginId(loginId).get();
            member.setId(existMember.getId());
            store.put(member.getId(), member);
            return Optional.of(member);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(String loginId) {
        if(findByLoginId(loginId).isPresent()){
            Member removeMember = findByLoginId(loginId).get();
            store.remove(removeMember.getId());
            return  true;
        }else{
            return false;
        }
    }
}
