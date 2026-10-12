package com.example.blogsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Blog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @NotEmpty(message = "title Should Not Be Empty")
    @Size(min = 3, max = 100, message = "title must be between 3 and 100 characters")
    @Column(columnDefinition = "varchar(100) not null")
    private String title;

    @NotEmpty(message = "body Should Not Be Empty")
    @Size(min = 10, message = "body must be at least 10 characters")
    @Column(columnDefinition = "text not null")
    private String body;


    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;












}
