package myproject.memberboard.domain.board.repository.jpa;

import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.Board;
import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.web.form.BoardForm;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
@RequiredArgsConstructor
public class SpringDataJPADIBoardRepository implements BoardRepository {

    private final SpringDataJpaBoardRepository repository;

    @Override
    public void save(Board board) {
        repository.save(board);
    }

    @Override
    public List<Board> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Board> findByAuthor(String author) {
        return repository.findByAuthor(author);
    }

    @Override
    public void update(String author, BoardForm param) {
        Board board = findByAuthor(author).get();
        board.setTitle(param.getTitle());
        board.setContents(param.getContents());
        board.setBoardTypeCode(param.getBoardTypeCode());
    }

    @Override
    public boolean delete(String author) {
        repository.deleteByAuthor(author);
        return true;
    }
}
