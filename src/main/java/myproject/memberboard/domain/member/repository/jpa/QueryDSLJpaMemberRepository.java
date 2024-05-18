package myproject.memberboard.domain.member.repository.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.QMember;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.web.form.UpdateMemberForm;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static myproject.memberboard.domain.member.QMember.member;

@Transactional
@Repository
public class QueryDSLJpaMemberRepository implements MemberRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public QueryDSLJpaMemberRepository(EntityManager em) {
        this.em = em;
        query = new JPAQueryFactory(em);
    }

    @Override
    public void save(Member member) {
        em.persist(member);
    }

/*    @Override
    public List<Member> findAll() {
        String jpql = "select m from Member m";
        TypedQuery<Member> query = em.createQuery(jpql, Member.class);
        return query.getResultList();
    }*/


    @Override
    public List<Member> findAll(){
        return query.select(member)
                .from(member)
                .fetch();
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

/*    @Override
    public Optional<Member> findByLoginId(String loginId) {

        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m WHERE m.loginId = :loginId", Member.class);
        query.setParameter("loginId", loginId);
        Member member = query.getSingleResult();
        return Optional.ofNullable(member);
    }*/


    @Override
    public Optional<Member> findByLoginId(String loginId) {
        Member findMember = query.selectFrom(member)
                            .where(member.loginId.eq(loginId))
                            .fetchOne();
        return Optional.ofNullable(findMember);
    }

    @Override
    public void update(Long id, UpdateMemberForm updateParam) {
        Member updateMember = em.find(Member.class, id);
        updateMember.setRegionTypeCode(updateParam.getRegionTypeCode());
    }

    @Override
    public boolean delete(Long id) {
        em.remove(id);
        return true;
    }
}
