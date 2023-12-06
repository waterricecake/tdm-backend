package tdm.tdmbackend.post.dto.request;

import static lombok.AccessLevel.PROTECTED;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class CommentRequest {

    @NotNull
    private String content;
}
