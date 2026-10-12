package com.example.blogsystem.Service;

import com.example.blogsystem.Api.ApiException;
import com.example.blogsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.example.blogsystem.Model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
 private final UserRepository userRepository;
   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findUsersByUserName(username);
       if(user==null){
           throw new ApiException("Wrong username or password");
       }
       return user;

    }
}
