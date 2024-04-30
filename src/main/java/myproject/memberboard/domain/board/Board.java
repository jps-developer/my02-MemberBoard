package myproject.memberboard.domain.board;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Board {
    private Long id;
    private String author;
    private String title;
    private String contents;
    private String boardTypeCode;
}
