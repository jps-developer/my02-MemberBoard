package myproject.memberboard.domain.board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class board {
    private Long id;
    private String author;
    private String title;
    private String contents;
}
