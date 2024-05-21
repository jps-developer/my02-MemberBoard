package myproject.memberboard.domain.member.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.web.form.UpdateMemberForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public List<Member> findAll() {
        String jpql = "select m from Member m";
        TypedQuery<Member> query = em.createQuery(jpql, Member.class);
        return query.getResultList();
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {

        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m WHERE m.loginId = :loginId", Member.class);
        query.setParameter("loginId", loginId);
        Member member = query.getSingleResult();
        return Optional.ofNullable(member);
    }

    @Override
    public void update(Long id, UpdateMemberForm updateParam) {
        Member updateMember = em.find(Member.class, id);
        updateMember.setRegionTypeCode(updateParam.getRegionTypeCode());
    }

    @Override
    public boolean delete(Long id) {
        Member deleteMember = findById(id).get();
        em.remove(deleteMember);
        return true;
    }
}
