package myproject.memberboard.domain.board.repository;

import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.Board;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.web.form.BoardForm;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JDBCBoardRepository implements BoardRepository{

    private final NamedParameterJdbcTemplate template;
    //private final SimpleJdbcInsert jdbcInsert;

    public JDBCBoardRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
/*        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("board")
                .usingGeneratedKeyColumns("id");*/
    }
/*    @Override
    public void save(Board board){
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(board);
        Number key = jdbcInsert.executeAndReturnKey(param);
        board.setId(key.longValue());
    }*/
    @Override
    public void save(Board board) {
        String sql = "insert into board " +
                "(author, title, contents, board_type_code) " +
                "values(:author, :title, :contents, :boardTypeCode)";

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(board);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        long key = keyHolder.getKey().longValue();
        board.setId(key);
    }

    @Override
    public List<Board> findAll() {
        String sql = "select * from board";
        return template.query(sql, boardRowMapper());
    }

    @Override
    public Optional<Board> findById(Long id) {
        String sql = "select * from board where id = :id";
        try{
            Map<String, Object> param = Map.of("id", id);
            Board board = template.queryForObject(sql, param, boardRowMapper());
            return Optional.of(board);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Board> findByAuthor(String author) {
        String sql = "select * from board where author = :author";
        try{
            Map<String, Object> param = Map.of("author", author);
            Board board = template.queryForObject(sql, param, boardRowMapper());
            return Optional.of(board);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void update(String author, BoardForm updateParam) {
        String sql = "update board set title = :title, contents = :contents where author = :author";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", updateParam.getTitle())
                .addValue("contents", updateParam.getContents())
                .addValue("author", updateParam.getAuthor());
        template.update(sql, param);
    }

    @Override
    public boolean delete(String author) {
        String sql = "delete from board where author = :author";
        Map<String, Object> param = Map.of("author", author);
        template.update(sql, param);
        return true;
    }

    private RowMapper<Board> boardRowMapper(){
        return BeanPropertyRowMapper.newInstance(Board.class);
    }
}
