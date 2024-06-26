package myproject.memberboard.domain.config.member;

import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.domain.board.repository.MemoryBoardRepository;
import myproject.memberboard.domain.board.service.BoardServiceImpl;
import myproject.memberboard.domain.login.LoginService;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.domain.member.repository.MemoryMemberRepository;
import myproject.memberboard.domain.member.service.MemberService;
import myproject.memberboard.domain.member.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryMemberConfig {

    @Bean
    MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
    @Bean
    MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    LoginService loginService(){
        return new LoginService(memberRepository());
    }
}
/*
drop table if exists member CASCADE;
create table member
        (
                member_id bigint generated by default as identity,
                login_id varchar(10),
password varchar(10),
member_name varchar(10),
age integer,
gender_type varchar(10),
region_type_code varchar(10),
primary key (member_id)
);*/
