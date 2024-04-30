package myproject.memberboard.web.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import myproject.memberboard.domain.member.GenderType;

@Getter @Setter
public class UpdateMemberForm {
    String regionTypeCode;
}
