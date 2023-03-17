package com.ElectronicStore.services;

import com.ElectronicStore.dtos.UserDto;
import com.ElectronicStore.exceptions.ResourceNotFoundException;

import java.util.List;

public interface UserService
{
    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId) throws ResourceNotFoundException;

    //delete
    void deleteUser(String userId) throws ResourceNotFoundException;

    //get all users
    List<UserDto> getAllUser();

    //get single user by id
    UserDto getUserById(String userId) throws ResourceNotFoundException;

    //serach user
    List<UserDto> searchUser(String keyword);

    UserDto getUserByEmail(String email) throws ResourceNotFoundException;

    //oth user specific feature
}
