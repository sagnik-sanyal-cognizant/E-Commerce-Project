package com.cts.ecommerce.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.ecommerce.entity.User;
import com.cts.ecommerce.exception.NotFoundException;
import com.cts.ecommerce.repository.UserRepo;

//CustomUserDetailsService is a service that implements UserDetailsService to provide custom user authentication
// It loads user details from the database using UserRepo
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    
    // Loads the user by their UserName (email).
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    	// Retrieves the user from the database by their email
    	// Throws NotFoundException if the user is not found
        User user = userRepo.findByEmail(username)
                .orElseThrow(()-> new NotFoundException("User/ Email Not found"));

        // Builds and returns an AuthUser object containing the user's details
        return AuthUser.builder()
                .user(user)
                .build();
    }
}
