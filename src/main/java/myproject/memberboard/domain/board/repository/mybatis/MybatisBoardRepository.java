package myproject.memberboard.domain.board.repository.mybatis;

import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.Board;
import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.web.form.BoardForm;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisBoardRepository implements BoardRepository {

    private final BoardMapper mapper;
    @Override
    public void save(Board board) {
        mapper.save(board);
    }

    @Override
    public List<Board> findAll() {
        return mapper.findAll();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public Optional<Board> findByAuthor(String author) {
        return mapper.findByAuthor(author);
    }

    @Override
    public void update(String author, BoardForm param) {
        mapper.update(author, param);
    }

    @Override
    public boolean delete(String author) {
        return mapper.delete(author);
    }
}
