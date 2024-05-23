package myproject.memberboard.web.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.service.MemberService;
import myproject.memberboard.web.form.UpdateMemberForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class JsonMemberController {

    private final MemberService memberService;
    @PostMapping("/members")
    public ResponseEntity<Member> joinMember(@Valid @RequestBody Member member){
        Member joinMember = memberService.join(member);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(joinMember.getMemberId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/members/{id}")
    public Member retrieveUser(@PathVariable Long id){
        return memberService.findById(id);
    }
    @GetMapping("/members")
    public List<Member> retrieveUsers(){
        return memberService.findAllMember();
    }
    @PutMapping("/members/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @Valid @RequestBody UpdateMemberForm param){
        memberService.updateMember(id, param);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Member> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
 }

 //{"memberName":"testB", "age":20, "genderType":"FEMALE", "regionTypeCode":"SEOUL", "loginId":"qqq", "password":"123"}