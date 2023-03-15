package com.ElectronicStore.services;

import com.ElectronicStore.dtos.UserDto;

import java.util.List;

public interface UserService
{
    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId);

    //delete
    void deleteUser(String userId);

    //get all users
    List<UserDto> getAllUser();

    //get single user by id
    UserDto getUserById(String userId);

    //serach user
    List<UserDto> searchUser(String keyword);

    UserDto getUserByEmail(String email);

    //oth user specific feature
}
