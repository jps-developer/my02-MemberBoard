package myproject.memberboard.domain.board.repository;

import myproject.memberboard.domain.board.Board;
import myproject.memberboard.web.form.BoardForm;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    void save(Board board);
    List<Board> findAll();
    Optional<Board> findById(Long id);
    Optional<Board> findByAuthor(String author);
    void update(String author, BoardForm param);
    boolean delete(String author);
}
