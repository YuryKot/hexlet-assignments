package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> getPosts() {
        return posts;
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getPostById(@PathVariable String id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody Post post) {
        Optional<Post> optionalPostForUpdate = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (optionalPostForUpdate.isPresent()) {
            Post postForUpdate = optionalPostForUpdate.get();
            postForUpdate.setId(post.getId());
            postForUpdate.setBody(post.getBody());
            postForUpdate.setTitle(post.getTitle());
        }
        return post;
    }

    @DeleteMapping("/posts/{id}")
    public void deleteById(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
    // END
}
