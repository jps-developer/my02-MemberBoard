package myproject.memberboard.domain.member.admin;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import myproject.memberboard.domain.member.GenderType;
import myproject.memberboard.domain.member.Member;

@JsonFilter("MemberInfo")
@Getter
@JsonIgnoreProperties(value ={}, allowGetters = true)
public class AdminMemberV1 extends Member {

    private String adminCode;

    public AdminMemberV1(){
        super();
    }
    public AdminMemberV1(String adminCode){
        super();
        this.adminCode = adminCode;
    }
    public AdminMemberV1(Long memberId, String memberName, Integer age, GenderType genderType, String regionTypeCode, String loginId, String password, String adminCode) {
        super(memberId, memberName, age, genderType, regionTypeCode, loginId, password);
        this.adminCode = adminCode;
    }
}
