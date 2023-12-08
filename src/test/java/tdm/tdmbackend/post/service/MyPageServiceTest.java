package tdm.tdmbackend.post.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.global.ServiceTest;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.dto.request.SchoolRequest;
import tdm.tdmbackend.member.repository.MemberRepository;
import tdm.tdmbackend.member.service.MemberService;

@Import(MemberService.class)
class MyPageServiceTest extends ServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("유저의 학교 및 학년 정보를 수정한다")
    void updateSchoolInfo() {
        // given
        final SchoolRequest request = DtoCreater.create(
                SchoolRequest.class,
                "updatedSchool",
                3L
        );

        // when
        memberService.updateSchoolInfo(1L, request);
        final Member expect = memberRepository.findById(1L).orElseThrow();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(expect.getSchool()).isEqualTo(request.getSchool());
                    softly.assertThat(expect.getGrade()).isEqualTo(request.getGrade());
                }
        );
    }
}
