package tdm.tdmbackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

@ExtendWith(MockitoExtension.class)
@Sql(
        value = {
                "classpath:testdata/truncate.sql",
                "classpath:testdata/member.sql",
                "classpath:testdata/tag.sql",
                "classpath:testdata/post.sql",
                "classpath:testdata/post_tag.sql",
                "classpath:testdata/member_tag.sql",
                "classpath:testdata/image.sql"

        }
)
public abstract class ServiceTest {

}
