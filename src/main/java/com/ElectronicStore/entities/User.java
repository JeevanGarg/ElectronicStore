package com.ElectronicStore.entities;

import lombok.*;

import javax.persistence.*;


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
