package myproject.memberboard.domain.config.member;

import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.login.LoginService;
import myproject.memberboard.domain.member.repository.JDBCMemberRepository;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.domain.member.service.MemberService;
import myproject.memberboard.domain.member.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JDBCMemberTemplateConfig {

    private final DataSource dataSource;
    @Bean
    MemberRepository memberRepository(){
        return new JDBCMemberRepository(dataSource);
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
