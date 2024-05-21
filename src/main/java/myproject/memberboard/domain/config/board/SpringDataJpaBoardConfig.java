package myproject.memberboard.domain.config.board;

import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.domain.board.repository.MemoryBoardRepository;
import myproject.memberboard.domain.board.repository.jpa.SpringDataJPADIBoardRepository;
import myproject.memberboard.domain.board.repository.jpa.SpringDataJpaBoardRepository;
import myproject.memberboard.domain.board.service.BoardServiceImpl;
import myproject.memberboard.domain.login.LoginService;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.domain.member.repository.jpa.SpringDataDIJpaMemberRepository;
import myproject.memberboard.domain.member.repository.jpa.SpringDataJpaMemberRepository;
import myproject.memberboard.domain.member.service.MemberService;
import myproject.memberboard.domain.member.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringDataJpaBoardConfig {

    private final SpringDataJpaBoardRepository repository;

    @Bean
    BoardRepository boardRepository(){
        return new SpringDataJPADIBoardRepository(repository);
    }
    @Bean
    BoardServiceImpl boardService(){
        return new BoardServiceImpl(boardRepository());
    }
}
