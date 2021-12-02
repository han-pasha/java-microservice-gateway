package com.training.javaexercise.Service.Implementation;

import com.training.javaexercise.Model.User;
import com.training.javaexercise.Repository.UserRepository;
import com.training.javaexercise.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
