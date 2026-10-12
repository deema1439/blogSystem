package com.example.blogsystem.Controller;

import com.example.blogsystem.Api.ApiResponse;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Repository.UserRepository;
import com.example.blogsystem.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
private final UserService userService;
//admin
    @GetMapping("/get")
public ResponseEntity<?>getAllUsers(@AuthenticationPrincipal User user){
    return ResponseEntity.status(200).body(userService.getAllUsers(user.getId()));
}

//all of them
@PostMapping("/register")
public ResponseEntity<?>register(@Valid @RequestBody User user){
    userService.register(user);
    return  ResponseEntity.status(200).body(new ApiResponse("User has been register"));
}

//user
@PutMapping("/update")
public ResponseEntity<?>update(@AuthenticationPrincipal User user,@Valid @RequestBody User newuser){
        userService.updateUser(user.getId(),newuser);
        return ResponseEntity.status(200).body(new ApiResponse("User has been updated"));
}

//admin
    @PutMapping("/updatebyadmin/{user_id}")
public ResponseEntity<?>updateUserByAdmin(@AuthenticationPrincipal User admin,@PathVariable Integer user_id,@Valid @RequestBody User user){
        userService.updateUserByAdmin(admin.getId(),user_id,user);
        return ResponseEntity.status(200).body(new ApiResponse("User has been updated"));
}

//user
@DeleteMapping("/delete")
public ResponseEntity<?>delete(@AuthenticationPrincipal User user){
        userService.delete(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("user has been deleted"));
}

//admin
@DeleteMapping("/deletebyadmin/{user_id}")
public ResponseEntity<?>deleteByAdmin(@AuthenticationPrincipal User user,@PathVariable Integer user_id){
        userService.deleteUserByAdmin(user.getId(),user_id);
        return ResponseEntity.status(200).body(new ApiResponse("user has been deleted"));

}
























}
