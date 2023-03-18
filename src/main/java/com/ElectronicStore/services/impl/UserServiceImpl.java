package com.ElectronicStore.services.impl;

import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.UserDto;
import com.ElectronicStore.entities.User;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.repository.UserRepository;
import com.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public UserDto updateUser(UserDto userDto, String userId) throws ResourceNotFoundException {
        Optional<User> user=this.userRepository.findById(userId);
        if(user.isPresent())
        {
            this.modelMapper.map(userDto,user);
            this.userRepository.save(user.get());
            return userDto;
        }
        else {
            throw new ResourceNotFoundException("Cannot found userId");
        }

    }

    @Override
    public void deleteUser(String userId) throws ResourceNotFoundException {
        Optional<User> userOptional=this.userRepository.findById(userId);

        if(userOptional.isPresent())
        {
            this.userRepository.delete(userOptional.get());
        }
        else {
            throw new ResourceNotFoundException("Cannot found userId");
        }

    }

    @Override
    public PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir)
    {
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);

        Page<User> page=this.userRepository.findAll(pageable);

        List<User> userList=page.getContent();

        List<UserDto> userDtoList=userList.stream().map(user->{
            UserDto userDto=new UserDto();
            this.modelMapper.map(user,userDto);
            return userDto;
        }).collect(Collectors.toList());

        PageableResponse<UserDto> pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(userDtoList);
        pageableResponse.setPageNumber(page.getNumber());
        pageableResponse.setPageSize(page.getSize());
        pageableResponse.setTotalElements(page.getTotalElements());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setLastPage(page.isLast());

        return pageableResponse;
    }

    @Override
    public UserDto getUserById(String userId) throws ResourceNotFoundException {
        Optional<User> userOptional=this.userRepository.findById(userId);
        if(userOptional.isPresent())
        {
            UserDto userDto=new UserDto();
            this.modelMapper.map(userOptional.get(),userDto);
            return userDto;
        }
        else {
            throw new ResourceNotFoundException("Cannot found userId");
        }
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
    public UserDto getUserByEmail(String email) throws ResourceNotFoundException {
        UserDto userDto=new UserDto();
        Optional<User> userOptional=this.userRepository.findByEmail(email);
        if(userOptional.isPresent())
        {
            this.modelMapper.map(userOptional.get(),userDto);
            return userDto;
        }

        else {
            throw new ResourceNotFoundException("Email is not found");
        }
    }
}
