package myproject.memberboard.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotBlank
    private String memberName;
    @NotNull
    @Min(value =  19)
    Integer age;
    @NotNull
    @Enumerated(EnumType.STRING)
    private GenderType genderType;
    private String regionTypeCode;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;


}
