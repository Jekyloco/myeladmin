package com.jekyloco.service.impl;

import com.jekyloco.repository.UserRepository;
import com.jekyloco.service.UserService;
import com.jekyloco.service.dto.UserDto;
import com.jekyloco.service.mapstruct.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;


    @Override
    public UserDto findByName(String userName) {
        return userMapper.toDto(userRepository.findByUsername(userName));
    }
}
