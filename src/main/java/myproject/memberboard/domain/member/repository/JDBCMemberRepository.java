package myproject.memberboard.domain.member.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.member.GenderType;
import myproject.memberboard.domain.member.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
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
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(member);
        Number key = jdbcInsert.executeAndReturnKey(param);
        member.setMemberId(key.longValue());
    }

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
    public Optional<Member> update(Long id, Member member){

        String sql = "update member set region=? where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getRegionTypeCode());
            pstmt.setLong(2, id);
            //rs = pstmt.executeQuery();
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);

            if(resultSize > 0){
                return findById(id);
            }else{
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw exTranslator.translate("update", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    @Override
    public boolean delete(Long id){

        String sql = "delete from member where member_id =?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);
            int success = pstmt.executeUpdate();
            return success > 0;
        } catch (SQLException e) {
            log.error("db error", e);
            throw exTranslator.translate("delete", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }
    private RowMapper<Member> memberRowMapper(){
        return BeanPropertyRowMapper.newInstance(Member.class);
    }
}
