package tdm.tdmbackend.member.dto.request;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class SchoolRequest {

    private String school;
    private Long grade;
}
