package tdm.tdmbackend.auth;

import static tdm.tdmbackend.global.exception.ExceptionCode.NOT_FOR_GUEST;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import tdm.tdmbackend.auth.domain.Accessor;
import tdm.tdmbackend.global.exception.BadRequestException;

@Aspect
@Component
public class MemberOnlyChecker {

    @Before("@annotation(MemberOnly)")
    public void check(final JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
                .filter(Accessor.class::isInstance)
                .map(Accessor.class::cast)
                .filter(Accessor::isMember)
                .findFirst()
                .orElseThrow(()-> BadRequestException.from(NOT_FOR_GUEST));
    }
}
