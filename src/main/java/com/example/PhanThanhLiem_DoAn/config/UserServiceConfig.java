package com.example.PhanThanhLiem_DoAn.config;

import com.example.PhanThanhLiem_DoAn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class UserServiceConfig implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.PhanThanhLiem_DoAn.model.User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find username");
        }

        if (user.isEnabled() == false) {
            throw new DisabledException("User is not enabled");
        }

        return new User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, // Account is not expired
                true, // Account is not locked
                true, // Credentials are not expired
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
    }

}
