package myproject.memberboard.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private GenderType genderType;
    private String regionTypeCode;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;


}
