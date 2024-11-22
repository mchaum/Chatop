package com.mchaum.Chatop.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mchaum.Chatop.model.User;
import com.mchaum.Chatop.repository.UserRepository;
import com.mchaum.Chatop.security.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		 if (userRepository.findByEmail(user.getEmail()).isPresent()) {
	            return ResponseEntity.badRequest().body("This email is already in use");
	        }
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
		return ResponseEntity.ok(userRepository.save(user));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
					if (authentication.isAuthenticated()) {
						Map<String, Object> authData = new HashMap<>(); // DTO possible aussi //
						authData.put("token", jwtUtils.generateToken(user.getEmail()));
						authData.put("type", "Bearer");
						return ResponseEntity.ok(authData);
					}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
		}
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> getAuthenticatedUser(Authentication authentication) {
	    String email = authentication.getName(); // Obtenu depuis Security //
	    Optional<User> userOptional = userRepository.findByEmail(email);
	    
	    if (userOptional.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }
	    
	    User user = userOptional.get();
	    Map<String, Object> userInfo = new HashMap<>();
	    userInfo.put("id", user.getId());
	    userInfo.put("name", user.getName());
	    userInfo.put("email", user.getEmail());
	    userInfo.put("created_at", user.getCreatedAt());
	    userInfo.put("updated_at", user.getUpdatedAt());

	    return ResponseEntity.ok(userInfo);
	}

}
