package com.example.techshop4.config.security;

import com.example.techshop4.entity.User;
import com.example.techshop4.entity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(s);
        if(user.isPresent()) {
            return new CustomPrincipal(user.get());
        }

        throw new UsernameNotFoundException("Пользователь не найден");
    }
}
