package com.example.blogsystem.Controller;

import com.example.blogsystem.Api.ApiResponse;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    //admin
    @GetMapping("/getall")
    public ResponseEntity<?> getAllBlogs(@AuthenticationPrincipal User admin) {
        return ResponseEntity.status(200).body(blogService.getAllBlogs(admin.getId()));
    }

    //user
    @GetMapping("/myblogs")
    public ResponseEntity<?> getMyBlog(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(blogService.getMyBlog(user.getId()));
    }

    //user
    @PostMapping("/add")
    public ResponseEntity<?> addBlog(@AuthenticationPrincipal User user, @Valid @RequestBody Blog blog) {
        blogService.addBlogToUser(user.getId(), blog);
        return ResponseEntity.status(200).body(new ApiResponse("Blog has been added"));
    }

    //user
    @PutMapping("/update/{blog_id}")
    public ResponseEntity<?> updateBlog(@AuthenticationPrincipal User user, @PathVariable Integer blog_id, @Valid @RequestBody Blog blog) {
        blogService.updateBlog(user.getId(), blog_id, blog);
        return ResponseEntity.status(200).body(new ApiResponse("Blog has been updated"));
    }

    //user و admin
    @DeleteMapping("/delete/{blog_id}")
    public ResponseEntity<?> deleteBlog(@AuthenticationPrincipal User user, @PathVariable Integer blog_id) {
        blogService.deleteBlog(user.getId(), blog_id);
        return ResponseEntity.status(200).body(new ApiResponse("Blog has been deleted"));
    }

    //user و admin
    @GetMapping("/getbyid/{blog_id}")
    public ResponseEntity<?> getBlogById(@AuthenticationPrincipal User user, @PathVariable Integer blog_id) {
        return ResponseEntity.status(200).body(blogService.getBlogById(user.getId(), blog_id));
    }

    //user
    @GetMapping("/getbytitle/{title}")
    public ResponseEntity<?> getBlogByTitle(@AuthenticationPrincipal User user, @PathVariable String title) {
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(user.getId(), title));
    }
}
