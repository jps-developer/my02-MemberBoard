package myproject.memberboard.domain.board.repository;

import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.board.Board;
import myproject.memberboard.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void save() {
        //given
        Board board = new Board();
        board.setAuthor("kim");
        board.setTitle("title1");
        board.setContents("contents1");
        board.setBoardTypeCode("ETC");

        //when
        boardRepository.save(board);
        List<Board> boards = boardRepository.findAll();
        for (Board board1 : boards) {
            log.info("boards={}",board1.toString());
        }

        //then
        Board findBoard = boardRepository.findById(board.getId()).get();
        log.info("board id={}, findBoard id={}",board.getId(), findBoard.getId());
        Assertions.assertThat(findBoard).isEqualTo(board);
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }
}