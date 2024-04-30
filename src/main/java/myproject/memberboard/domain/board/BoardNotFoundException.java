package myproject.memberboard.domain.board;

public class BoardNotFoundException extends IllegalArgumentException{
    public BoardNotFoundException(String s) {
        super(s);
    }
}
