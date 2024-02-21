package tdm.tdmbackend.post.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import tdm.tdmbackend.auth.Auth;
import tdm.tdmbackend.auth.MemberOnly;
import tdm.tdmbackend.auth.domain.Accessor;
import tdm.tdmbackend.post.dto.request.CommentRequest;
import tdm.tdmbackend.post.dto.response.CommentResponse;
import tdm.tdmbackend.post.service.CommentService;

@Tag(name = "03. Comment API", description = "댓글 생성 수정 삭제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;


    @Operation(
            summary = "댓글 작성",
            description = "로그인 필요",
            security = {@SecurityRequirement(name = AUTHORIZATION), @SecurityRequirement(name = "refreshToken")}
    )
    @PostMapping("/posts/{postId}")
    @MemberOnly
    public ResponseEntity<CommentResponse> createComment(
            @Auth Accessor accessor,
            @RequestBody final CommentRequest commentRequest,
            @PathVariable final Long postId
    ) {
        final CommentResponse commentResponse = commentService.create(commentRequest, postId, accessor.getMemberId());
        final URI located = URI.create("/comments/" + commentResponse.getId());
        return ResponseEntity.created(located).body(commentResponse);
    }

    @Operation(
            summary = "댓글 수정",
            description = "로그인 필요",
            security = {@SecurityRequirement(name = AUTHORIZATION), @SecurityRequirement(name = "refreshToken")}
    )
    @PutMapping("/{commentId}")
    @MemberOnly
    public ResponseEntity<CommentResponse> updateComment(
            @Auth Accessor accessor,
            @RequestBody final CommentRequest commentRequest,
            @PathVariable final Long commentId
    ) {
        final CommentResponse commentResponse = commentService.update(commentRequest, commentId);
        return ResponseEntity.ok(commentResponse);
    }

    @Operation(
            summary = "댓글 삭제",
            description = "로그인 필요",
            security = {@SecurityRequirement(name = AUTHORIZATION), @SecurityRequirement(name = "refreshToken")}
    )
    @DeleteMapping("/{commentId}")
    @MemberOnly
    public ResponseEntity<Void> deleteComment(
            @Auth Accessor accessor,
            @PathVariable final Long commentId
    ) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}
