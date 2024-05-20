package myproject.memberboard.domain.config;

import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.domain.board.repository.JDBCBoardRepository;
import myproject.memberboard.domain.board.repository.MemoryBoardRepository;
import myproject.memberboard.domain.board.service.BoardServiceImpl;
import myproject.memberboard.domain.login.LoginService;
import myproject.memberboard.domain.member.repository.JDBCMemberRepository;
import myproject.memberboard.domain.member.repository.SQLExceptionTranslatorMemberRepository;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.domain.member.service.MemberService;
import myproject.memberboard.domain.member.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JDBCTemplateConfig {

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
    BoardRepository boardRepository(){
        return new JDBCBoardRepository(dataSource);
    }
    @Bean
    BoardServiceImpl boardService(){
        return new BoardServiceImpl(boardRepository());
    }
    @Bean
    LoginService loginService(){
        return new LoginService(memberRepository());
    }
}
