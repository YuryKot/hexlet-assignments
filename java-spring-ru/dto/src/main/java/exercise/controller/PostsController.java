package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.stream.Collectors;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostsController {
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    @GetMapping
    public List<PostDTO> getPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream()
                .map(p -> postDtoMapper(p, commentRepository.findByPostId(p.getId())
                                                            .stream()
                                                            .map(this::commentDtoMapper)
                                                            .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PostDTO getPost(@PathVariable long id) {
        return postDtoMapper(postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Post with id %d not found", id))),
                commentRepository.findByPostId(id).stream().map(this::commentDtoMapper).collect(Collectors.toList()));
    }

    private CommentDTO commentDtoMapper(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

    private PostDTO postDtoMapper(Post post, List<CommentDTO> commentDTOList) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setBody(post.getBody());
        postDTO.setComments(commentDTOList);
        return postDTO;
    }
}
// END
