package com.example.xunyou.mapper;


import com.example.xunyou.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface UserMapper {

    public User getById(int id);
    public Set<String> getRoles(int id);
    public User getByName(String username);
    public Set<String> getPermission(int id);

}
