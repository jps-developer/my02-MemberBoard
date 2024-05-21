package myproject.memberboard.domain.board.repository.jpa;

import myproject.memberboard.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaBoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByAuthor(String author);
    void deleteByAuthor(String author);
}
