package myproject.memberboard;

import jakarta.servlet.http.HttpServletResponse;
import myproject.memberboard.domain.board.BoardNotFoundException;
import myproject.memberboard.domain.member.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

/*  발생하는 예외를 화면에 던짐
    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found: " + ex.getMessage());
    }*/

    // 상세페이지 에러로 넘어감, 사실상 거의 사용 안함
    // ExceptionHandler는 거의 대부분 api예외처리 할때 사용 화면단은 BasicErrorController로 처리
    @ExceptionHandler(MemberNotFoundException.class)
    public ModelAndView handleMemberNotFoundException(MemberNotFoundException ex) {
        ModelAndView mav = new ModelAndView("error/memberNotFound"); // 에러 페이지 설정
        mav.addObject("errorMessage", ex.getMessage()); // 에러 메시지 전달 (선택적)
        return mav;
    }
    // 발생하는 예외를 화면에 던짐
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleBoardNotFoundException(BoardNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found: " + ex.getMessage());
    }

/*    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalStateException(IllegalStateException ex) {
        return "error-400";
    }*/

    @ExceptionHandler(IllegalStateException.class)
    public void handleIllegalStateException(IllegalStateException ex, HttpServletResponse response) throws IOException {
        response.sendError(400, "잘못된 요청입니다.");
    }
}