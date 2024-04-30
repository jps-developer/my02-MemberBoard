package myproject.memberboard.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BoardForm {
    @NotBlank
    private String author;
    @NotBlank
    private String title;
    @Size(min = 3, message = "최소 3글자 이상 입력하세요")
    @NotBlank
    private String contents;
    @NotBlank
    private String boardTypeCode;
}
