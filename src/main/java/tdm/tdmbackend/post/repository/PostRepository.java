package tdm.tdmbackend.post.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostsBy(final Pageable pageable);

    List<Post> findPostsByMember(final Member member);
}
