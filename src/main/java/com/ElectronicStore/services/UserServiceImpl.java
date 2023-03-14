package com.ElectronicStore.services;

import com.ElectronicStore.dtos.UserDto;
import com.ElectronicStore.entities.User;
import com.ElectronicStore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto)
    {
        //Generate unique Id in string format
        String userId= UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user=new User();
        this.modelMapper.map(userDto,user);
        User user1= this.userRepository.save(user);
        this.modelMapper.map(user1,userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId)
    {
        Optional<User> user=this.userRepository.findById(userId);
        if(user.isPresent())
        {
            this.modelMapper.map(userDto,user);
            this.userRepository.save(user.get());
        }
        return userDto;
    }

    @Override
    public void deleteUser(String userId)
    {
        Optional<User> userOptional=this.userRepository.findById(userId);

        if(userOptional.isPresent())
        {
            this.userRepository.delete(userOptional.get());
        }

    }

    @Override
    public List<UserDto> getAllUser()
    {

        List<User> userList= this.userRepository.findAll();
        List<UserDto> userDtoList=userList.stream().map(user->{
            UserDto userDto=new UserDto();
            this.modelMapper.map(user,userDto);
            return userDto;
        }).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDto getUserById(String userId)
    {
        Optional<User> userOptional=this.userRepository.findById(userId);
        if(userOptional.isPresent())
        {
            UserDto userDto=new UserDto();
            this.modelMapper.map(userOptional.get(),userDto);
            return userDto;
        }
        return null;
    }

    @Override
    public List<UserDto> searchUser(String keyword)
    {
        List<User> userList=this.userRepository.findByNameContaining(keyword);
        List<UserDto> userDtoList=userList.stream().map(user -> {
            UserDto userDto=new UserDto();
            this.modelMapper.map(user,userDto);
            return userDto;
        }).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDto getUserByEmail(String email)
    {
        UserDto userDto=new UserDto();
        Optional<User> userOptional=this.userRepository.findByEmail(email);
        if(userOptional.isPresent())
        {
            this.modelMapper.map(userOptional.get(),userDto);
            return userDto;
        }

        return null;
    }
}
