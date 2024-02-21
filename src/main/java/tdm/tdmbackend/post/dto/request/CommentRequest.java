package tdm.tdmbackend.post.dto.request;

import static lombok.AccessLevel.PROTECTED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "Comment create API parameter", description = "댓글 생성 API 파라미터")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class CommentRequest {

    @Schema(name = "content", description = "댓글 내용", example = "content")
    @NotNull
    private String content;
}
