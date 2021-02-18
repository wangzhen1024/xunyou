package com.example.xunyou.service.impl;

import com.example.xunyou.bean.User;
import com.example.xunyou.mapper.UserMapper;
import com.example.xunyou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User getById(int id){
        return userMapper.getById(id);
    }

    @Override
    public User getByName(String username) {
        return userMapper.getByName(username);
    }

    @Override
    public Set<String> getRoles(String username) {

        User user = userMapper.getByName(username);
        return userMapper.getRoles(user.getId());
    }

    @Override
    public Set<String> getPermissions(String username) {
        User user = userMapper.getByName(username);
        return userMapper.getPermission(user.getId());
    }

    @Override
    public String test(String username) {
        return username;
    }
}
