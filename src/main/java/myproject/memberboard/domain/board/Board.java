package myproject.memberboard.domain.board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;
    private String title;
    private String contents;
    private String boardTypeCode;
}
//create table board(board_id bigint, author varchar(10), title varchar(10), content varchar(30), board_type_code varchar(10), primary key (board_id));