
package com.example.blogsystem.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean//filter //مهم جدا
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf->csrf.disable())
                .sessionManagement(sm->
                        sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .authorizeHttpRequests(auth-> auth
                        // all of them
                        .requestMatchers("/api/v1/user/register").permitAll()
                        .requestMatchers(
                                "/api/v1/user/get",
                                "/api/v1/user/updatebyadmin/**",
                                "/api/v1/user/deletebyadmin/**",
                                "/api/v1/blog/getall"
                        ).hasAuthority("ADMIN")
                        .requestMatchers(
                                "/api/v1/user/update",
                                "/api/v1/user/delete",
                                "/api/v1/blog/**"
                        ).hasAnyAuthority("USER", "ADMIN")

                        .anyRequest().authenticated()

                )
                .logout(logout->logout
                        .logoutUrl("/api/v1/user/logout")
                        .invalidateHttpSession(true)
                )
                .httpBasic(basic->{})
                .build();//هنا السيمكولين مجموعه من التاشين


    }












}
