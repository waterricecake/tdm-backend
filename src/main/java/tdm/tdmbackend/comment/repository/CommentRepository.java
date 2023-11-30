package tdm.tdmbackend.comment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tdm.tdmbackend.comment.domain.Comment;
import tdm.tdmbackend.post.domain.Post;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findCommentsByPost(final Post post);
}
