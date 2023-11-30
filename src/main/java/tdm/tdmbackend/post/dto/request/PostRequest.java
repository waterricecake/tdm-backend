package tdm.tdmbackend.post.dto.request;

import static lombok.AccessLevel.PROTECTED;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    private List<Long> tags;

    private List<String> images;
}

