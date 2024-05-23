package myproject.memberboard.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"loginId", "password"})
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotBlank
    private String memberName;
    @NotNull @Min(value =  19)
    Integer age;
    @NotNull @Enumerated(EnumType.STRING)
    private GenderType genderType;
    private String regionTypeCode;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
}
