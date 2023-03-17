package com.ElectronicStore.entities;

import com.ElectronicStore.validate.ImageNameValid;
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
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email",unique = true)
    private String email;

    @Column(name = "user_password",length = 15)
    private String password;

    private String gender;

    @Column(length = 1000)
    private String about;


    @Column(name = "user_image_name")
    private String imageName;

}
