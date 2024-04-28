package myproject.memberboard.web.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.member.GenderType;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.RegionTypeCode;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.domain.member.service.MemberService;
import myproject.memberboard.web.member.UpdateMemberForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ModelAttribute("genderTypes")
    public GenderType[] genderTypes(){
        return GenderType.values();
    }

    @ModelAttribute("regionTypeCodes")
    public List<RegionTypeCode> regionTypeCodes(){
        List<RegionTypeCode> regionTypeCodes = new ArrayList<>();
        regionTypeCodes.add(new RegionTypeCode("SEOUL", "서울"));
        regionTypeCodes.add(new RegionTypeCode("BUSAN", "부산"));
        regionTypeCodes.add(new RegionTypeCode("JEJU", "제주"));
        regionTypeCodes.add(new RegionTypeCode("ETC", "기타"));
        return regionTypeCodes;
    }

    @GetMapping("/member/{memberId}")
    public String showMember(@PathVariable Long memberId, Model model){
        Member member = memberService.findById(memberId);
        model.addAttribute("member", member);
        return "members/member";
    }

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("member") Member member){
        return "/members/joinMemberForm";
    }

    @PostMapping("/join")
    public String joinMember(@Validated @ModelAttribute Member member, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "/members/joinMemberForm";
        }
        memberService.join(member);
        redirectAttributes.addAttribute("id",member.getId());
        return "redirect:/members/member/{id}";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model){
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "/members/updateMemberForm";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @Validated @ModelAttribute Member form,
                         BindingResult bindingResult){
        Member member = new Member();
        member.setRegionTypeCode(form.getRegionTypeCode());
        memberService.updateMember(id, member);
        return "redirect:/";
    }
}
