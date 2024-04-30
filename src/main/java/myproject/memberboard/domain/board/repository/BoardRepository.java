package myproject.memberboard.domain.board.repository;

import myproject.memberboard.domain.board.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    void save(Board board);
    List<Board> findAll();
    Optional<Board> findById(Long id);
    Optional<Board> findByAuthor(String author);
    Optional<Board> update(String author, Board param);
    boolean delete(String author);
}
