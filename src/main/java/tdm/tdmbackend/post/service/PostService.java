package tdm.tdmbackend.post.service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdm.tdmbackend.member.domain.Member;
import tdm.tdmbackend.member.repository.MemberRepository;
import tdm.tdmbackend.post.domain.Comment;
import tdm.tdmbackend.post.domain.Image;
import tdm.tdmbackend.post.domain.Post;
import tdm.tdmbackend.post.domain.PostTag;
import tdm.tdmbackend.post.dto.request.PostRequest;
import tdm.tdmbackend.post.dto.response.PostDetailResponse;
import tdm.tdmbackend.post.dto.response.PostResponse;
import tdm.tdmbackend.post.repository.CommentRepository;
import tdm.tdmbackend.post.repository.ImageRepository;
import tdm.tdmbackend.post.repository.PostRepository;
import tdm.tdmbackend.post.repository.PostTagRepository;
import tdm.tdmbackend.tag.domain.Tag;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostTagRepository postTagRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;

    public Long create(final PostRequest postRequest, final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalAccessError::new);
        final Post savePost = Post.of(member, postRequest.getTitle(), postRequest.getContent());
        final Post post = postRepository.save(savePost);
        postTagRepository.saveAll(
                postRequest.getTags().stream()
                        .map(tagId -> PostTag.of(Post.from(post.getId()), Tag.from(tagId)))
                        .toList()
        );
        final List<Image> images = postRequest.getImages().stream()
                .map(name -> Image.of(post, name))
                .toList();
        imageRepository.saveAll(images);
        return post.getId();
    }

    @Transactional(readOnly = true)
    public PostDetailResponse read(final Long postId) {
        // todo : 예외처리
        final Post post = postRepository.findById(postId)
                .orElseThrow();
        final List<PostTag> tags = postTagRepository.findPostTagsByPost(post);
        final List<Image> images = imageRepository.findImagesByPost(post);
        final List<Comment> comments = commentRepository.findCommentsByPost(post);
        return PostDetailResponse.of(post, tags, images, comments);
    }

    public void update(final PostRequest postRequest, final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow();
        post.updateTitle(postRequest.getTitle());
        post.updateContent(postRequest.getContent());
        final List<Image> images = postRequest.getImages().stream()
                .map(name -> Image.of(post, name))
                .toList();
        imageRepository.deleteAllByPostId(postId);
        post.updateImages(images);
        final List<PostTag> postTags = postRequest.getTags().stream()
                .map(tagId -> PostTag.of(post, Tag.from(tagId)))
                .toList();
        postTagRepository.deleteAllByPostId(postId);
        post.updatePostTags(postTags);
    }

    public void delete(final Long postId) {
        imageRepository.deleteAllByPostId(postId);
        postTagRepository.deleteAllByPostId(postId);
        commentRepository.deleteCommentsByPostId(postId);
        postRepository.deleteById(postId);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPosts(Pageable pageable){
        List<Post> posts = postRepository.findPostsBy(pageable);
        return posts.stream()
                .map(PostResponse::from)
                .toList();
    }
}
