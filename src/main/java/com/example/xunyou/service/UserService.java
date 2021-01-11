package com.example.xunyou.service;

import com.example.xunyou.bean.User;

import java.util.Set;

public interface UserService {

    public User getById(int id);
    public User getByName(String username);
    public Set<String> getRoles(String username);
    public Set<String> getPermissions(String username);
}
