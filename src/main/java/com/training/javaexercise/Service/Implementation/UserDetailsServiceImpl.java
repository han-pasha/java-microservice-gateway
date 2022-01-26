package com.training.javaexercise.Service.Implementation;

import com.training.javaexercise.Model.Role;
import com.training.javaexercise.Model.User;
import com.training.javaexercise.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new UsernameNotFoundException("User not authorized.");
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), grantedAuthorities);
    }
}
