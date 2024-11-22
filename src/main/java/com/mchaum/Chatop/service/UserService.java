package com.mchaum.Chatop.service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mchaum.Chatop.model.User;
import com.mchaum.Chatop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), 
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
    
 // Méthode pour obtenir l'ID utilisateur //
    public Long findUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));
        return user.getId();
    }
    
    
    // Méthode pour obtenir un utilisateur via son ID (Rentals) //
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> 
            new UsernameNotFoundException("User not found with id: " + id)
        );
    }
}
