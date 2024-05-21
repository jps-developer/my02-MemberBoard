package myproject.memberboard.domain.board.repository.mybatis;


import myproject.memberboard.domain.board.Board;
import myproject.memberboard.web.form.BoardForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    void save(Board board);
    List<Board> findAll();
    Optional<Board> findById(Long id);
    Optional<Board> findByAuthor(String author);
    void update(@Param("author") String author,@Param("param") BoardForm param);
    boolean delete(String author);
}
