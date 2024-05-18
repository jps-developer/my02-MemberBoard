package myproject.memberboard.domain.config;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.domain.board.repository.MemoryBoardRepository;
import myproject.memberboard.domain.board.service.BoardServiceImpl;
import myproject.memberboard.domain.login.LoginService;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.domain.member.repository.jpa.JpaMemberRepository;
import myproject.memberboard.domain.member.repository.jpa.QueryDSLJpaMemberRepository;
import myproject.memberboard.domain.member.service.MemberService;
import myproject.memberboard.domain.member.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QueryDSLConfig {

    private final EntityManager em;

    @Bean
    MemberRepository memberRepository(){
        return new QueryDSLJpaMemberRepository(em);
    }
    @Bean
    MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    BoardRepository boardRepository(){
        return new MemoryBoardRepository();
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
