package tdm.tdmbackend.post.dto.request;

import static lombok.AccessLevel.PROTECTED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name ="Post API parameter", description = "게시물 생성 요청 api 파라미터")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostRequest {

    @Schema(name = "title", example = "title")
    @NotNull
    private String title;

    @Schema(name = "content", example = "content")
    @NotNull
    private String content;

    @Schema(name = "tags", description = "입력되지 않은 테그는 에러처리됨", example = "[]")
    private List<Long> tags;

    @Schema(name = "images", description = "이미지 업로드 후 응답받은 이름값 입력", example ="[]")
    private List<String> images;
}

