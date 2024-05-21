package myproject.memberboard.domain.config.board;

import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.domain.board.repository.MemoryBoardRepository;
import myproject.memberboard.domain.board.repository.mybatis.BoardMapper;
import myproject.memberboard.domain.board.repository.mybatis.MybatisBoardRepository;
import myproject.memberboard.domain.board.service.BoardServiceImpl;
import myproject.memberboard.domain.login.LoginService;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.domain.member.repository.mybatis.MemberMapper;
import myproject.memberboard.domain.member.repository.mybatis.MybatisMemberRepository;
import myproject.memberboard.domain.member.service.MemberService;
import myproject.memberboard.domain.member.service.MemberServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@MapperScan("myproject.memberboard.domain.board.repository.mybatis")
public class MybatisBoardConfig {

    private final BoardMapper boardMapper;
    @Bean
    BoardRepository boardRepository(){
        return new MybatisBoardRepository(boardMapper);
    }
    @Bean
    BoardServiceImpl boardService(){
        return new BoardServiceImpl(boardRepository());
    }
}
