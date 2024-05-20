package myproject.memberboard.domain.board.service;

import myproject.memberboard.domain.board.Board;
import myproject.memberboard.web.form.BoardForm;

import java.util.List;

public interface BoardService {
    void create(Board board);
    List<Board> findAllBoards();
    Board findById(Long id);
    Board findByAuthor(String author);
    void updateBoard(String author, BoardForm param);
    void deleteBoard(String author);
}
