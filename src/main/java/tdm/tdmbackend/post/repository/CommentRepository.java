package tdm.tdmbackend.post.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tdm.tdmbackend.post.domain.Comment;
import tdm.tdmbackend.post.domain.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByPost(final Post post);

    @Modifying
    @Query(
            """
                    DELETE FROM Comment comment
                    WHERE comment.member.id = :postId
                    """
    )
    void deleteCommentsByPostId(@Param("postId") final Long postId);
}
