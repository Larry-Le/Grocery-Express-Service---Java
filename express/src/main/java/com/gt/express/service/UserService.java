package com.gt.express.service;

import com.gt.express.domain.Role;
import com.gt.express.domain.UserDao;

import java.util.List;


public interface UserService {
    UserDao saveUser(UserDao userDao);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    UserDao getUser(String username);
    List<UserDao> getUsers();
}
