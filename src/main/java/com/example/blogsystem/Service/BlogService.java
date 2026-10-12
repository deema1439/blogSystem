package com.example.blogsystem.Service;

import com.example.blogsystem.Api.ApiException;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Repository.BlogRepository;
import com.example.blogsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;


    public List<Blog>getAllBlogs(Integer admin_id){
        User admin = userRepository.findUsersById(admin_id);
        if (admin == null) {
            throw new ApiException("admin not found");
        }
        if (!admin.getRole().equals("ADMIN")) {
            throw new ApiException("only admin can view all blogs");
        }

        return blogRepository.findAll();
    }



    public List<Blog>getMyBlog(Integer user_id){
        User user=userRepository.findUsersById(user_id);
        if(user==null){
            throw new ApiException("User not found");
        }
        return blogRepository.findAllByUser(user);
    }



    public void addBlogToUser(Integer user_id,Blog blog){
        User user=userRepository.findUsersById(user_id);
        if(user==null){
            throw new ApiException("User not found");
        }
        blog.setUser(user);
        blogRepository.save(blog);
    }



    public void updateBlog(Integer user_id, Integer blog_id, Blog blog) {
        User user = userRepository.findUsersById(user_id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        Blog oldBlog = blogRepository.findBlogById(blog_id);
        if (oldBlog == null) {
            throw new ApiException("Blog not found");
        }
        if (!oldBlog.getUser().getId().equals(user_id)) {
            throw new ApiException("you are not allowed to update this blog");
        }
        oldBlog.setTitle(blog.getTitle());
        oldBlog.setBody(blog.getBody());
        blogRepository.save(oldBlog);
    }


    public void deleteBlog(Integer user_id, Integer blog_id) {
        User user = userRepository.findUsersById(user_id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        Blog blog = blogRepository.findBlogById(blog_id);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }
        if (!blog.getUser().getId().equals(user_id)) {
            throw new ApiException("you are not allowed to delete this blog");
        }
        blogRepository.delete(blog);
    }


    public Blog getBlogById(Integer user_id, Integer blog_id) {
        User user = userRepository.findUsersById(user_id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        Blog blog = blogRepository.findBlogById(blog_id);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }
        if (!blog.getUser().getId().equals(user_id) && !user.getRole().equals("ADMIN")) {
            throw new ApiException("you are not allowed to view this blog");
        }
        return blog;
    }


    public Blog getBlogByTitle(Integer user_id, String title) {
        User user = userRepository.findUsersById(user_id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        Blog blog = blogRepository.findBlogByTitleAndUser(title, user);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }
        return blog;
    }






























}
