package com.ElectronicStore.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Builder
public class User
{
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;

    @Column(name = "user_name")
    @Size(min = 3,max = 20,message = "Invalid Name !")
    private String name;

    @Column(name = "user_email",unique = true)
   // @Email(message = "Invalid User Email")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Invalid User Email!")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank
    private String password;

    @Size(min = 4,max = 6,message = "Invalid Gender")
    private String gender;

    @NotBlank(message = "Write something about yourself")
    private String about;


    private String imageName;

    //@Pattern
    //custom validator
}
