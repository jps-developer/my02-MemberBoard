package myproject.memberboard;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.Board;
import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.domain.board.service.BoardService;
import myproject.memberboard.domain.member.GenderType;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @PostConstruct
    public void init(){
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("123");
        member.setMemberName("mark");
        member.setRegionTypeCode("ETC");
        member.setAge(30);
        member.setGenderType(GenderType.MALE);
        memberRepository.save(member);

        Board board = new Board();
        board.setTitle("문의");
        board.setContents("이거 품절 인가요?");
        board.setAuthor("test");
        board.setBoardTypeCode("ITEM");
        boardRepository.save(board);
    }
}
