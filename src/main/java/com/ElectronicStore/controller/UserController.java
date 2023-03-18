package com.ElectronicStore.controller;

import com.ElectronicStore.dtos.ApiResponseMessage;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.UserDto;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;

    //create
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto userDto1=this.userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
                                              @PathVariable("userId") String userId) throws ResourceNotFoundException {
        UserDto updateUser=this.userService.updateUser(userDto,userId);
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }

    //delete
    //In responseEntity try to avoid sending String send in object(payloads)
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) throws ResourceNotFoundException {
        this.userService.deleteUser(userId);
        ApiResponseMessage message=ApiResponseMessage
                .builder()
                .message("User is deleted successfully!")
                .success(true).status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(message,HttpStatus.OK);

    }

    //get all
    @GetMapping("/allUsers")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "2",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    //get Single
    @GetMapping("getUser/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") String userId) throws ResourceNotFoundException {
        UserDto userDto=this.userService.getUserById(userId);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    //get By email
    @GetMapping("/Email/{email}")
    public ResponseEntity<UserDto> getByEmail(@PathVariable("email") String email) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    //search User
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable("keywords") String keywords)
    {
        return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
    }

}
