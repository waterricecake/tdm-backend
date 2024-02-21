package tdm.tdmbackend.member.dto.request;

import static lombok.AccessLevel.PROTECTED;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "Member School info update API parameter", description = "회원 학교 정보 수정 API 파라미터")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SchoolRequest {

    @Schema(name = "school", description = "학교이름", example = "부천고")
    private String school;

    @Schema(name = "grade", description = "학년", example = "1")
    private Long grade;
}
