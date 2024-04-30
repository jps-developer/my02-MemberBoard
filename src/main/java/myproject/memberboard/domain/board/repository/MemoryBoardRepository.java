package myproject.memberboard.domain.board.repository;

import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.board.Board;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemoryBoardRepository implements BoardRepository{

    private static Map<Long, Board> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public void save(Board board) {
        board.setId(++sequence);
        log.info("save Board={}", board);
        store.put(board.getId(), board);
    }

    @Override
    public List<Board> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Board> findById(Long id) {
        return findAll().stream().
                filter(m -> m.getId().equals(id)).
                findFirst();
    }

    @Override
    public Optional<Board> findByAuthor(String author) {
        return findAll().stream().
                filter(m -> m.getAuthor().equals(author)).
                findAny();
    }

    @Override
    public Optional<Board> update(String author, Board param) {
        if(findByAuthor(author).isPresent()){
            Board updateBoard = findByAuthor(author).get();
            updateBoard.setTitle(param.getTitle());
            updateBoard.setContents(param.getContents());
            updateBoard.setBoardTypeCode(param.getBoardTypeCode());
            return Optional.of(updateBoard);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(String author) {
        if(findByAuthor(author).isPresent()){
            Long deleteId = findByAuthor(author).get().getId();
            store.remove(deleteId);
            return true;
        }else{
            return false;
        }
    }
}
