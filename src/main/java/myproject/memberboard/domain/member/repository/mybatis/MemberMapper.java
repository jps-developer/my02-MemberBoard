package myproject.memberboard.domain.member.repository.mybatis;

import myproject.memberboard.domain.member.Member;
import myproject.memberboard.web.form.UpdateMemberForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    void save(Member member);
    List<Member> findAll();
    Optional<Member> findById(Long id);
    Optional<Member> findByLoginId(String loginId);
    void update(@Param("id") Long id, @Param("updateParam") UpdateMemberForm updateParam);
    void delete(Long id);
}
