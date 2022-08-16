package com.gt.express.service;


import com.gt.express.domain.Role;
import com.gt.express.domain.UserDao;
import com.gt.express.repo.RoleRepo;
import com.gt.express.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDao userDao = userRepo.findByUsername(username);
        if(userDao == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            userDao.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(userDao.getUsername(), userDao.getPassword(), authorities);
        }
    }

    @Override
    public UserDao saveUser(UserDao userDao) {
        log.info("Saving new user {} to the database", userDao.getName());
        userDao.setPassword(passwordEncoder.encode(userDao.getPassword()));
        UserDao user = userRepo.findByUsername(userDao.getUsername());
        if (null == user){
            userDao = userRepo.save(userDao);
        }
        return userDao;
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        Role roleDao = roleRepo.findByName(role.getName());
        if(null == roleDao){
            role = roleRepo.save(role);
        }
        return role;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        UserDao userDao = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        userDao.getRoles().add(role);
    }

    @Override
    public UserDao getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<UserDao> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }
}
