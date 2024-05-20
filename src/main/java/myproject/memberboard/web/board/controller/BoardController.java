package myproject.memberboard.web.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.board.Board;
import myproject.memberboard.domain.board.BoardTypeCode;
import myproject.memberboard.domain.board.service.BoardService;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.web.form.BoardForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @ModelAttribute("boardTypeCodes")
    public List<BoardTypeCode> boardTypeCodes(){
        List<BoardTypeCode> boardTypeCodes = new ArrayList<>();
        boardTypeCodes.add(new BoardTypeCode("ACCOUNT", "계정 문의"));
        boardTypeCodes.add(new BoardTypeCode("ITEM", "상품 문의"));
        boardTypeCodes.add(new BoardTypeCode("DELIVERY", "배송 문의"));
        boardTypeCodes.add(new BoardTypeCode("ETC", "기타 문의"));
        return boardTypeCodes;
    }

    @GetMapping("/board/{id}")
    public String showBoard(@PathVariable Long id, Model model){
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "/boards/board";
    }

    @GetMapping
    public String boardHome(Model model, HttpServletRequest request){
        List<Board> boards = boardService.findAllBoards();
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("loginMember");
        log.info("boardHome Member={}",member);
        model.addAttribute("boards", boards);
        model.addAttribute("member", member);
        return "/boards/boards";
    }

    @GetMapping("/create")
    public String createForm(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("loginMember");
        log.info("member login ID={}",member.getLoginId());
        Board board = new Board();
        board.setAuthor(member.getLoginId());
        model.addAttribute("board", board);
        return "/boards/createBoardForm";
    }

    @PostMapping("/create")
    public String createBoard(@Validated @ModelAttribute("board") BoardForm form, BindingResult bindingResult){
        //특정 필드 예외가 아닌 전체 예외
        if(form.getTitle() != null && form.getContents() != null){
            int minlen = form.getTitle().length() + form.getContents().length();
            if(minlen < 5){
                bindingResult.reject("totalLenMin", new Object[]{5, minlen}, null);
            }
        }

        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/boards/createBoardForm";
        }

        Board board = setBoardByForm(form);
        boardService.create(board);
        return "redirect:/";
    }
    private static Board setBoardByForm(BoardForm form) {
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContents(form.getContents());
        board.setAuthor(form.getAuthor());
        board.setBoardTypeCode(form.getBoardTypeCode());
        return board;
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("loginMember");
        String author = member.getLoginId();
        log.info("update Board author={}",author);
        Board board = boardService.findById(id);

        if(author.equals(board.getAuthor())){
            model.addAttribute("board", board);
            return "/boards/updateBoardForm";
        }else{
            log.info("update Board author={}",board.getAuthor());
            redirectAttributes.addAttribute("boardId", board.getId());
            return "redirect:/boards/board/{boardId}";
        }
    }

    @PostMapping("/{id}/update")
    public String updateBoard(@PathVariable Long id, @Validated @ModelAttribute("board") BoardForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/boards/updateBoardForm";
        }
        String author = boardService.findById(id).getAuthor();
        boardService.updateBoard(author, form);
        return "redirect:/";
    }

    @PostMapping("{id}/delete")
    public String deleteBoard(@PathVariable Long id, HttpServletRequest request, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("loginMember");
        String author = member.getLoginId();
        log.info("update Board author={}",author);
        Board board = boardService.findById(id);

        if(author.equals(board.getAuthor())){
            boardService.deleteBoard(author);
            return "redirect:/";
        }else{
            log.info("update Board author={}",board.getAuthor());
            redirectAttributes.addAttribute("boardId", board.getId());
            return "redirect:/boards/board/{boardId}";
        }
    }
}
