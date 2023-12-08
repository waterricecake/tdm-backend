package tdm.tdmbackend.member.controller;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdm.tdmbackend.member.dto.response.MyPageResponse;
import tdm.tdmbackend.member.service.MemberService;

@Tag(name = "Mypage API", description = "mypage 조회 수정 API")
@RestController
@RequiredArgsConstructor(access = PRIVATE)
@RequestMapping("/mypage")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "마이페이지 조회")
    @GetMapping
    public ResponseEntity<MyPageResponse> getMyPage() {
        // todo : 인증 인가 필요
        final MyPageResponse myPageResponse = memberService.getMyPage(1L);
        return ResponseEntity.ok(myPageResponse);
    }
}
