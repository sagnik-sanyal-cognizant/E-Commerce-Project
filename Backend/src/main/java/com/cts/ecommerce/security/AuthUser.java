package com.cts.ecommerce.security;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cts.ecommerce.entity.User;

import java.util.Collection;
import java.util.List;

// AuthUser is a custom implementation of UserDetails used for Spring Security authentication
// It wraps a User entity and provides the necessary methods for authentication and authorization
@Data
@Builder
public class AuthUser implements UserDetails {

    private User user;
    
    // @return returns a collection of granted authorities to the user.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}