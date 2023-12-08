package tdm.tdmbackend.tag.controller;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdm.tdmbackend.tag.dto.response.TagResponse;
import tdm.tdmbackend.tag.service.TagService;

@Tag(name = "Tag API", description = "태그 생성 수정 삭제 API")
@RestController
@RequiredArgsConstructor(access = PRIVATE)
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "전체 태그 조회")
    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        final List<TagResponse> tagResponses = tagService.getAllTags();
        return ResponseEntity.ok(tagResponses);
    }
}
