package exercise.controller.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api/users")
public class PostsController {

    List<Post> posts = Data.getPosts();

    @GetMapping("/{id}/posts")
    public List<Post> getPostsByUserId(@PathVariable int id) {
        return posts.stream().filter(p -> p.getUserId() == id).collect(Collectors.toList());
    }

    @PostMapping("/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@PathVariable int id, @RequestBody Post post) {
        Post newPost = new Post();
        newPost.setUserId(id);
        newPost.setTitle(post.getTitle());
        newPost.setSlug(post.getSlug());
        newPost.setBody(post.getBody());
        posts.add(newPost);
        return newPost;
    }
}
// END
