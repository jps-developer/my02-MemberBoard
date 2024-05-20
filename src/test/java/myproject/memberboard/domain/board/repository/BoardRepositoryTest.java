package myproject.memberboard.domain.board.repository;

import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.board.Board;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.web.form.BoardForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
        assertThat(findBoard).isEqualTo(board);
    }

    @Test
    void findAll() {
        //given
        Board board1 = new Board();
        board1.setAuthor("kim");
        board1.setTitle("title1");
        board1.setContents("contents1");
        board1.setBoardTypeCode("ETC");
        Board board2 = new Board();
        board2.setAuthor("PARK");
        board2.setTitle("title2");
        board2.setContents("contents2");
        board2.setBoardTypeCode("JEJU");
        boardRepository.save(board1);
        boardRepository.save(board2);

        //when
        List<Board> boards = boardRepository.findAll();

        //then
        assertThat(boards.size()).isEqualTo(2);
    }

    @Test
    void update() {
        //given
        Board board = new Board();
        board.setAuthor("kim");
        board.setTitle("title1");
        board.setContents("contents1");
        board.setBoardTypeCode("ETC");
        boardRepository.save(board);

        //when
        BoardForm form = new BoardForm();
        form.setAuthor("kim");
        form.setTitle("change");
        form.setContents("test");
        form.setBoardTypeCode("SEOUL");
        boardRepository.update("kim", form);

        //then
        Board updateBoard = boardRepository.findByAuthor("kim").get();
        assertThat(updateBoard.getTitle()).isEqualTo(form.getTitle());
        assertThat(updateBoard.getContents()).isEqualTo(form.getContents());
        assertThat(updateBoard.getBoardTypeCode()).isEqualTo(form.getBoardTypeCode());
    }
}