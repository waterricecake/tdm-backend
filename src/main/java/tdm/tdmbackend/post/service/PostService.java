package tdm.tdmbackend.post.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tdm.tdmbackend.comment.domain.Comment;
import tdm.tdmbackend.comment.repository.CommentRepository;
import tdm.tdmbackend.image.domain.Image;
import tdm.tdmbackend.image.repository.ImageRepository;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.repository.MemberRepository;
import tdm.tdmbackend.post.domain.Post;
import tdm.tdmbackend.post.domain.PostTag;
import tdm.tdmbackend.post.dto.request.PostCreateRequest;
import tdm.tdmbackend.post.dto.response.PostDetailResponse;
import tdm.tdmbackend.post.repository.PostRepository;
import tdm.tdmbackend.post.repository.PostTagRepository;
import tdm.tdmbackend.tag.domain.Tag;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostTagRepository postTagRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;

    public Long create(PostCreateRequest request, Long memberId) {
        // todo: event 처리
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalAccessError::new);
        Post savePost = Post.of(member, request.getTitle(), request.getContent());
        Post post = postRepository.save(savePost);
        postTagRepository.saveAll(
                request.getTags().stream()
                        .map(tagId -> PostTag.of(Post.from(post.getId()), Tag.from(tagId)))
                        .toList()
        );
        return post.getId();
    }

    public PostDetailResponse read(final Long postId) {
        // todo: 예외처리
        final Post post = postRepository.findById(postId)
                .orElseThrow();
        final List<PostTag> tags = postTagRepository.findPostTagsByPost(post);

        final List<Image> images = imageRepository.findImagesByPost(post);
        final List<Comment> comments = commentRepository.findCommentsByPost(post);
        return PostDetailResponse.of(post, tags, images, comments);
    }
}
