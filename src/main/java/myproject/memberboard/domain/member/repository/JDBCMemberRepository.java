
package myproject.memberboard.domain.member.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.member.GenderType;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.web.form.UpdateMemberForm;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JDBCMemberRepository implements MemberRepository{

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JDBCMemberRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("member_id");
    }

    @Override
    public void save(Member member){
        log.info("@@@@@@@@@@@@@ member={}",member);
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(member);
        log.info("@@@@@@@@@@@@@ param={}",param);

        Number key = jdbcInsert.executeAndReturnKey(param);
        log.info("@@@@@@@@@@@@@ key={}", key);
        member.setMemberId(key.longValue());
        //template.getJdbcTemplate().execute("COMMIT");
    }
/*    @Override
    public void save(Member member){
        String sql = "insert into member" +
                "(login_id, password, member_name, age, gender_type, region_type_code)" +
                " values(:loginId, :password, :memberName, :age, :genderType, :regionTypeCode)";

        // 열거형을 문자열로 변환하여 저장
        String genderTypeString = member.getGenderType().toString();
        member.setGenderType(genderTypeString);

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(member);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        Long key = keyHolder.getKey().longValue();
        member.setMemberId(key);

    }*/

    @Override
    public List<Member> findAll(){
        String sql = "select * from member";
        return template.query(sql, memberRowMapper());
    }

    @Override
    public Optional<Member> findById(Long id){

        String sql = "select * from member where member_id = :member_id";
        try{
            Map<String, Object> param = Map.of("member_id", id);
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
    @Override
    public Optional<Member> findByLoginId(String loginId){
        String sql = "select * from member where login_id = :login_id";
        try{
            Map<String, Object> param = Map.of("login_id", loginId);
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void update(Long id, UpdateMemberForm updateParam){

        String sql = "update member set region_type_code= :region_type_code where member_id= :member_id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("region_type_code", updateParam.getRegionTypeCode())
                .addValue("member_id", id);
        template.update(sql, param);
    }

    @Override
    public boolean delete(Long id){

        String sql = "delete from member where member_id = :member_id";
        Map<String, Object> param = Map.of("member_id", id);
        template.update(sql, param);
        return true;
    }
    private RowMapper<Member> memberRowMapper(){
        return BeanPropertyRowMapper.newInstance(Member.class);
    }
}
