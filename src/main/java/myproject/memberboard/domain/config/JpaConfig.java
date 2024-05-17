package myproject.memberboard.domain.config;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.domain.board.repository.MemoryBoardRepository;
import myproject.memberboard.domain.board.service.BoardServiceImpl;
import myproject.memberboard.domain.login.LoginService;
import myproject.memberboard.domain.member.repository.JDBCMemberRepository;
import myproject.memberboard.domain.member.repository.JpaMemberRepository;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.domain.member.service.MemberService;
import myproject.memberboard.domain.member.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    private final EntityManager em;

    public JpaConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    MemberRepository memberRepository(){
        return new JpaMemberRepository(em);
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
