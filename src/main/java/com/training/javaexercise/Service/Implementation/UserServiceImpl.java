package com.training.javaexercise.Service.Implementation;

import com.training.javaexercise.Model.Role;
import com.training.javaexercise.Model.User;
import com.training.javaexercise.Repository.RoleRepository;
import com.training.javaexercise.Repository.UserRepository;
import com.training.javaexercise.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleCode(roleName);
//        user.getRoles().add(role);
//        role.getRoleUser().add(user);
        user.getRoles().add(role);
        log.info("User: {}, Role: {}",user.getUsername(),user.getRoles());

    }
}
