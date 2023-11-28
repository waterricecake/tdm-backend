package tdm.tdmbackend.global;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
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
public abstract class RepositoryTest {

}
