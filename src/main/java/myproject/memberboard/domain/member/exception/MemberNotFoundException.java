package myproject.memberboard.domain.member.exception;

public class MemberNotFoundException extends IllegalArgumentException{
    public MemberNotFoundException(String message){
        super(message);
    }
}
