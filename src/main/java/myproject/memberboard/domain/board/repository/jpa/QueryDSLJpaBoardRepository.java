package myproject.memberboard.domain.board.repository.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.Board;
import myproject.memberboard.domain.board.QBoard;
import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.web.form.BoardForm;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static myproject.memberboard.domain.board.QBoard.*;

@Repository
@Transactional
@RequiredArgsConstructor
public class QueryDSLJpaBoardRepository implements BoardRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public QueryDSLJpaBoardRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public void save(Board board) {
        em.persist(board);
    }

    @Override
    public List<Board> findAll() {
        return query.select(board)
                .from(board)
                .fetch();
    }

    @Override
    public Optional<Board> findById(Long id) {
        Board board = em.find(Board.class, id);
        return Optional.ofNullable(board);
    }

    @Override
    public Optional<Board> findByAuthor(String author) {
        Board findBoard = query.select(board)
                .from(board)
                .where(board.author.eq(author))
                .fetchOne();
        return Optional.ofNullable(findBoard);
    }

    @Override
    public void update(String author, BoardForm param) {
        Board findBoard = findByAuthor(author).orElseThrow(
                () -> new EntityNotFoundException("Author not found: " + author));
        findBoard.setBoardTypeCode(param.getBoardTypeCode());
        findBoard.setTitle(param.getTitle());
        findBoard.setContents(param.getContents());
    }

    @Override
    public boolean delete(String author) {
        Board deleteBoard = findByAuthor(author).get();
        em.remove(deleteBoard);
        return true;
    }
}
