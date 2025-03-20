package com.cts.ecommerce.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

// JwtAuthFilter is a filter that processes JWT authentication for incoming requests.
// It extracts the JWT token from the request, validates it, and sets the authentication in the security context.
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final  CustomUserDetailsService customUserDetailsService;

    
    // Filters incoming requests to perform JWT authentication
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request); // Extracts the JWT token from the request
        
        // Checks if the token is not null then retrieving the respective user's data
        if (token != null){
            String username = jwtUtils.getUsernameFromToken(token);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Checks if the UserName is not empty and the token is valid
            if (StringUtils.hasText(username) && jwtUtils.isTokenValid(token, userDetails)) {
                log.info("VALID JWT FOR {}", username);

                // Creates an authentication token with the user details and sets it in the security context
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request, response); // Continues the filter chain
    }

    // Extracts the JWT token from the request header
    private String getTokenFromRequest(HttpServletRequest request){
    	
        String token = request.getHeader("Authorization"); // Retrieves the token from the Authorization header
        
        // Checks if the token is not empty and starts with "Bearer "
        if (StringUtils.hasText(token) && StringUtils.startsWithIgnoreCase(token, "Bearer ")) {
            return token.substring(7); // Returns the token without the "Bearer" prefix
        }
        return null;
    }
}
