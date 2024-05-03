package myproject.memberboard.domain;

import myproject.memberboard.domain.board.BoardNotFoundException;
import myproject.memberboard.domain.member.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

/*  발생하는 예외를 화면에 던짐
    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found: " + ex.getMessage());
    }*/

    @ExceptionHandler(MemberNotFoundException.class) // 상세페이지 에러로 넘어감
    public ModelAndView handleMemberNotFoundException(MemberNotFoundException ex) {
        ModelAndView mav = new ModelAndView("error/memberNotFound"); // 에러 페이지 설정
        mav.addObject("errorMessage", ex.getMessage()); // 에러 메시지 전달 (선택적)
        return mav;
    }

    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleBoardNotFoundException(MemberNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found: " + ex.getMessage());
    }
}



/*@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleMemberNotFoundException(MemberNotFoundException ex) {
        // 예외를 로깅합니다.
        logger.error("Member not found: " + ex.getMessage(), ex);
    }
}*/
