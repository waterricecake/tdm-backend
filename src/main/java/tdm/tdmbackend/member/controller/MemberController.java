package tdm.tdmbackend.member.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdm.tdmbackend.auth.Auth;
import tdm.tdmbackend.auth.MemberOnly;
import tdm.tdmbackend.auth.domain.Accessor;
import tdm.tdmbackend.member.dto.request.InterestRequest;
import tdm.tdmbackend.member.dto.request.SchoolRequest;
import tdm.tdmbackend.member.dto.response.MyPageResponse;
import tdm.tdmbackend.member.service.MemberService;

@Tag(name = "01. Mypage API", description = "mypage 조회 수정 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "마이페이지 조회",
            description = "로그인 필요",
            security = {@SecurityRequirement(name = AUTHORIZATION), @SecurityRequirement(name = "refreshToken")}
    )
    @GetMapping
    @MemberOnly
    public ResponseEntity<MyPageResponse> getMyPage(
            @Auth Accessor accessor
    ) {
        final MyPageResponse myPageResponse = memberService.getMyPage(accessor.getMemberId());
        return ResponseEntity.ok(myPageResponse);
    }

    @Operation(
            summary = "학교 정보 수정",
            description = "로그인 필요",
            security = {@SecurityRequirement(name = AUTHORIZATION), @SecurityRequirement(name = "refreshToken")}
    )
    @PutMapping("/school")
    @MemberOnly
    public ResponseEntity<Void> updateSchool(
            @Auth Accessor accessor,
            @RequestBody final SchoolRequest schoolRequest
    ) {
        memberService.updateSchoolInfo(accessor.getMemberId(), schoolRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "관심사 정보 수정",
            description = "로그인 필요",
            security = {@SecurityRequirement(name = AUTHORIZATION), @SecurityRequirement(name = "refreshToken")}
    )
    @PutMapping("/interest")
    @MemberOnly
    public ResponseEntity<Void> updateInterest(
            @Auth Accessor accessor,
            @RequestBody final InterestRequest interestRequest
    ) {
        memberService.updateInterests(accessor.getMemberId(), interestRequest);
        return ResponseEntity.noContent().build();
    }
}
