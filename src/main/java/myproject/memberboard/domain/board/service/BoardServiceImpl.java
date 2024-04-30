package myproject.memberboard.domain.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.board.Board;
import myproject.memberboard.domain.board.BoardNotFoundException;
import myproject.memberboard.domain.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public void post(Board board) {
        boardRepository.save(board);
        log.info("save board={}",board);
    }

    @Override
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    @Override
    public Board findById(Long id) {
        if(boardRepository.findById(id).isPresent()){
            return boardRepository.findById(id).get();
        }else{
            throw new BoardNotFoundException(id + " : 는 존재 하지않는 게시판 입니다.");
        }
    }

    @Override
    public Board findByAuthor(String author) {
        if(boardRepository.findByAuthor(author).isPresent()){
            return boardRepository.findByAuthor(author).get();
        }else{
            throw new BoardNotFoundException(author + " : 가 작성한 게시판은 존재 하지않습니다.");
        }
    }

    @Override
    public void updateBoard(String author, Board param) {
        if(boardRepository.update(author, param).isPresent()){
            log.info("update Board={}",param);
        }else{
            throw new BoardNotFoundException(author + " : 가 작성한 게시판은 존재 하지않습니다.");
        }
    }

    @Override
    public void deleteBoard(String author) {
        if(boardRepository.delete(author)){
            log.info("delete Board author={}",author);
        }else{
            throw new BoardNotFoundException(author + " : 가 작성한 게시판은 존재 하지않습니다.");
        }
    }
}
