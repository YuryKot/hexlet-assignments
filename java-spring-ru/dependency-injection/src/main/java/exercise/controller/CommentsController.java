package exercise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentRepository commentRepository;

    @GetMapping
    public List<Comment> getAllPosts() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment getById(@PathVariable long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    @PutMapping("/{id}")
    public Comment updateById(@PathVariable long id, @RequestBody Comment comment) {
        Comment commentForUpdate = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not Found"));
        commentForUpdate.setBody(comment.getBody());
        commentForUpdate.setPostId(comment.getPostId());
        return commentRepository.save(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        commentRepository.deleteById(id);
    }
}
// END
