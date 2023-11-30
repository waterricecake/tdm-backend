package tdm.tdmbackend.image.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.image.domain.Image;
import tdm.tdmbackend.post.domain.Post;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findImagesByPost(final Post post);
}
