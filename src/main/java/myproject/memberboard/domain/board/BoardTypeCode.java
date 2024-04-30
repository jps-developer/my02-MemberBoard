package myproject.memberboard.domain.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardTypeCode {
    private String code;
    private String displayName;
}
