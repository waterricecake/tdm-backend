package tdm.tdmbackend.post.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.post.domain.Image;
import tdm.tdmbackend.post.domain.Post;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findImagesByPost(final Post post);

    void deleteAllByPostId(final Long postId);
}
