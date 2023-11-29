package tdm.tdmbackend.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.post.domain.PostTag;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
