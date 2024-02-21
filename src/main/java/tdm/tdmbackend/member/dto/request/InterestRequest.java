package tdm.tdmbackend.member.dto.request;

import static lombok.AccessLevel.PROTECTED;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "Member interest update API parameter", description = "회원 관심사 수정 API 파라미터")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class InterestRequest {

    @Schema(name = "interests", description = "파라미터 리스트, 없는 Tag 포함시 예외처리", example = "[]")
    private List<Long> interests;
}
