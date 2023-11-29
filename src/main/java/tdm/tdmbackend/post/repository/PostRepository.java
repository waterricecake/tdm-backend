package tdm.tdmbackend.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.post.domain.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
}
