package com.training.javaexercise.Service;

import com.training.javaexercise.Model.Role;
import com.training.javaexercise.Model.User;

public interface UserService {

    User saveUser(User user);
    User findUsername(String username);

    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
}
