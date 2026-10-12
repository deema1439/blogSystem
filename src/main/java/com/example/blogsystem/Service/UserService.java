package com.example.blogsystem.Service;

import com.example.blogsystem.Api.ApiException;
import com.example.blogsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.example.blogsystem.Model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
private final UserRepository userRepository;

//admin
 public List<User>getAllUsers(Integer admin_id){
     User admin=userRepository.findUsersById(admin_id);
     if(admin==null){
         throw new ApiException("Admin not found");
     }
     if (!admin.getRole().equals("ADMIN")) {
         throw new ApiException("only admin can view all users");
     }
     return userRepository.findAll();
 }

 public void register(User user){
     if (userRepository.findUsersByUserName(user.getUsername()) != null) {
         throw new ApiException("username already exists");
     }
   user.setRole("USER");
   String hash=new BCryptPasswordEncoder().encode(user.getPassword());
   user.setPassword(hash);
   userRepository.save(user);
 }

 //user can update him self
 public void updateUser(Integer user_id,User user){
     User oldUser=userRepository.findUsersById(user_id);
     if(oldUser==null){
         throw new ApiException("user not found");
     }
     if(!user.getUsername().equals(oldUser.getUsername())){
         if(userRepository.findUsersByUserName(user.getUsername())!=null){
             throw new ApiException("username already exists");
         }
     }
     oldUser.setUserName(user.getUsername());
     String hash=new BCryptPasswordEncoder().encode(user.getPassword());
     oldUser.setPassword(hash);
     userRepository.save(oldUser);
 }

 //Admin update user

    public void updateUserByAdmin(Integer admin_id,Integer user_id,User user){
     User admin=userRepository.findUsersById(admin_id);
     if(admin==null){
         throw new ApiException("Admin not found");
     }
     User olduser=userRepository.findUsersById(user_id);
     if(olduser==null){
         throw new ApiException("user not found");
     }
     if(!admin.getRole().equals("ADMIN")){
         throw new ApiException("only admin can update users");
     }

     if(!olduser.getUsername().equals(user.getUsername())){
        if(userRepository.findUsersByUserName(user.getUsername())!=null) {
            throw new ApiException("username already exists");
        }
     }

     olduser.setUserName(user.getUsername());
     String hash=new BCryptPasswordEncoder().encode(user.getPassword());
     olduser.setPassword(hash);
     userRepository.save(olduser);

    }



    //user delete him self
    public void delete(Integer user_id){
     User deleteUser=userRepository.findUsersById(user_id);
     if(deleteUser==null){
         throw new ApiException("user not found");
     }
        userRepository.delete(deleteUser);
    }




    //admin delete user

    public void deleteUserByAdmin(Integer admin_id,Integer user_id){
     User admin=userRepository.findUsersById(admin_id);
     if(admin==null){
         throw new ApiException("admin not found");
     }
     if(!admin.getRole().equals("ADMIN")){
         throw new ApiException("only admin can delete users ");
     }
        User deleteUser=userRepository.findUsersById(user_id);
        if(deleteUser==null){
            throw new ApiException("user not found");
        }
        userRepository.delete(deleteUser);
    }

























}
