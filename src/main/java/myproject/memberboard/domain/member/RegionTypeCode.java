package myproject.memberboard.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegionTypeCode {
    private String code;
    private String displayName;
}
