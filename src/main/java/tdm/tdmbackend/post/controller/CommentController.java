package tdm.tdmbackend.post.controller;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdm.tdmbackend.post.dto.request.CommentRequest;
import tdm.tdmbackend.post.dto.response.CommentResponse;
import tdm.tdmbackend.post.service.CommentService;

@Tag(name = "Comment API", description = "댓글 생성 수정 삭제 API")
@RestController
@RequiredArgsConstructor(access = PRIVATE)
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;


    @Operation(summary = "댓글 작성")
    @PostMapping("/posts/{postId}")
    public ResponseEntity<CommentResponse> createComment(
            @RequestBody final CommentRequest commentRequest,
            @PathVariable final Long postId
    ) {
        // todo : 인가
        final CommentResponse commentResponse = commentService.create(commentRequest, postId, 1L);
        final URI located = URI.create("/comments/" + commentResponse.getId());
        return ResponseEntity.created(located).body(commentResponse);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @RequestBody final CommentRequest commentRequest,
            @PathVariable final Long commentId
    ) {
        // todo : 인가
        final CommentResponse commentResponse = commentService.update(commentRequest, commentId);
        return ResponseEntity.ok(commentResponse);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable final Long commentId
    ) {
        // todo : 인가
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}
