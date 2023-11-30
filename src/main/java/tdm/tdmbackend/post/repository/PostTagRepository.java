package tdm.tdmbackend.post.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.post.domain.Post;
import tdm.tdmbackend.post.domain.PostTag;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    List<PostTag> findPostTagsByPost(final Post post);

    void deleteAllByPostId(final Long postId);
}
