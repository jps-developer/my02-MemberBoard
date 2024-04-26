package myproject.memberboard.domain.member;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {

    private Long id;
    @NotBlank
    private String memberName;
    @NotNull
    @Min(value =  19)
    Integer age;
    private String region;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
}
