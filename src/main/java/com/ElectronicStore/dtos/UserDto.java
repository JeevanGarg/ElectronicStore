package com.ElectronicStore.dtos;

import com.ElectronicStore.validate.ImageNameValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private String userId;


    @Size(min = 3,max = 20,message = "Invalid Name !")
    private String name;


    // @Email(message = "Invalid User Email")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid User Email!")
    @NotBlank(message = "Email is required")
    //@Email(message = "Invalid user Email !")
    private String email;

    @NotBlank
    private String password;

    @Size(min = 4,max = 6,message = "Invalid Gender")
    private String gender;

    @NotBlank(message = "Write something about yourself")
    private String about;


    @ImageNameValid
    private String imageName;
}
