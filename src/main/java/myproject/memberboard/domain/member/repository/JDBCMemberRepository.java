package myproject.memberboard.domain.member.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.member.GenderType;
import myproject.memberboard.domain.member.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// create table member(member_id long, loginId varchar(50), password varchar(50), name varchar(50), age int, gender varchar(50), region varchar(50));
@Slf4j
//@Repository
@RequiredArgsConstructor
public class H2DBMemberRepository implements MemberRepository{
    private final DataSource dataSource;
    private static Long sequence = 0L;

    private SQLExceptionTranslator exTranslator;

    @Override
    public void save(Member member){

        String sql = "insert into member" +
                "(member_id, loginId, password, name, age, gender, region)" +
                " values(?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            setDBMember(member, pstmt);
            int check = pstmt.executeUpdate();
            log.info("check save={}",check);
        } catch (SQLException e) {
            log.error("db error", e);
            throw exTranslator.translate("save", sql, e);
        }finally {
            close(con, pstmt, null);
        }
    }

    private static void setDBMember(Member member, PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, ++sequence);
        pstmt.setString(2, member.getLoginId());
        pstmt.setString(3, member.getPassword());
        pstmt.setString(4, member.getMemberName());
        pstmt.setInt(5, member.getAge());
        pstmt.setString(6, member.getGenderType().toString());
        pstmt.setString(7, member.getRegionTypeCode());
    }

    @Override
    public List<Member> findAll(){
        String sql = "select * from member";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Member> members = new ArrayList<>();
        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                members.add(getDBMember(rs));
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw exTranslator.translate("findAll", sql, e);
        } finally {
            close(con, pstmt, rs);
        }
        return members;
    }

    @Override
    public Optional<Member> findById(Long id){

        String sql = "select * from member where member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member findMember = getDBMember(rs);
                return Optional.of(findMember);
            }else{
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw exTranslator.translate("findById", sql, e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    private Member getDBMember(ResultSet rs) throws SQLException {

        try{
            Member member = new Member();
            member.setMemberId(rs.getLong("member_id"));
            member.setLoginId(rs.getString("loginId"));
            member.setPassword(rs.getString("password"));
            member.setMemberName(rs.getString("name"));
            member.setAge(rs.getInt("age"));
            String genderTypeString = rs.getString("gender");
            member.setGenderType(GenderType.valueOf(genderTypeString));
            member.setRegionTypeCode(rs.getString("region"));
            return member;
        }catch (SQLException e){
            throw e;
        }
    }

    @Override
    public Optional<Member> findByLoginId(String loginId){
        String sql = "select * from member where loginId = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, loginId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member findMember = getDBMember(rs);
                return Optional.of(findMember);
            }else{
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw exTranslator.translate("findByLoginId", sql, e);
        } finally {
            close(con, pstmt, rs);
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

    private Connection getConnection(){
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("getConnection={}, class={}", con, con.getClass());
        return con;
    }

    private void close(Connection con, Statement stmt, ResultSet rs){
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        DataSourceUtils.releaseConnection(con, dataSource);
    }
}
