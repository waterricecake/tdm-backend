package tdm.tdmbackend.member.dto.request;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class InterestRequest {

    private List<Long> interests;
}
