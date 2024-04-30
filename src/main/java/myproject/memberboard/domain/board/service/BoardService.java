package myproject.memberboard.domain.board.service;

import myproject.memberboard.domain.board.Board;

import java.util.List;

public interface BoardService {
    void create(Board board);
    List<Board> findAllBoards();
    Board findById(Long id);
    Board findByAuthor(String author);
    void updateBoard(String author, Board param);
    void deleteBoard(String author);
}
