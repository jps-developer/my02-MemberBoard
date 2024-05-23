package myproject.memberboard.web.member.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.memberboard.domain.member.Member;
import myproject.memberboard.domain.member.admin.AdminMemberV1;
import myproject.memberboard.domain.member.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/v1")
@Slf4j
public class JsonAdminV1MemberController {

    private final MemberService memberService;

    @GetMapping("/users")
    public MappingJacksonValue retrieveMembers(){
        List<Member> members = memberService.findAllMember();
        List<AdminMemberV1> adminMembers = new ArrayList<>();

        AdminMemberV1 adminMember = null;
        for (Member member : members) {
            adminMember = new AdminMemberV1();
            BeanUtils.copyProperties(member, adminMember);
            adminMembers.add(adminMember);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("memberId", "memberName", "age", "genderType", "regionTypeCode", "loginId", "password");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("MemberInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminMembers);
        mapping.setFilters(filters);
        return  mapping;
    }
    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveMember(@PathVariable Long id){
        Member member = memberService.findById(id);
        AdminMemberV1 adminMember = new AdminMemberV1("admin");
        BeanUtils.copyProperties(member, adminMember);
        log.info("adminCode={}",adminMember.getAdminCode());

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept( "adminCode", "memberId", "memberName", "loginId", "password");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("MemberInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminMember);
        mapping.setFilters(filters);
        return mapping;
    }
}
