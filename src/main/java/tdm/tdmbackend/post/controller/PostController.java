package tdm.tdmbackend.post.controller;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdm.tdmbackend.post.dto.request.PostCreateRequest;
import tdm.tdmbackend.post.service.PostService;

@Tag(name = "Post API", description = "게시물 생성, 조회, 수정 삭제 API")
@RestController
@RequiredArgsConstructor(access = PRIVATE)
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시물 생성")
    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody PostCreateRequest postCreateRequest
    ) {
        // todo: 인가 필요
        final Long postId = postService.create(postCreateRequest, 1L);
        return ResponseEntity.created(URI.create("/posts/" + postId)).build();
    }
}
