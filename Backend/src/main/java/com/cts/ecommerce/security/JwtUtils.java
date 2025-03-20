package com.cts.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cts.ecommerce.entity.User;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;


// JwtUtils is a utility class that provides methods for generating, parsing, and validating JWT tokens
@Service
@Slf4j
public class JwtUtils {

    private static final long EXPIRATION_TIME_IN_MILLISEC = 1000L * 60L *60L *24L * 30L * 6L; //Expires 6 months
    private SecretKey key;

    @Value("${secretJwtString}") //Value in the application properties of 32characters
    private String secreteJwtString; 

    // Initializes the secret key for signing JWT tokens
    @PostConstruct
    private void init(){
        byte[] keyBytes = secreteJwtString.getBytes(StandardCharsets.UTF_8);	
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    // Generates a JWT token for the given user
    public String generateToken(User user){
        String username = user.getEmail();
        return generateToken(username);
    }
    
    // Generates a JWT token for the given UserName
    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MILLISEC))
                .signWith(key)
                .compact();
    }

    // Extracts the UserName from the given JWT token
    public String getUsernameFromToken(String token){
        return extractClaims(token, Claims::getSubject);
    }

    // Extracts claims from the given JWT token using the provided claims function
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }

    // Validates the given JWT token against the user details
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Checks if the given JWT token is expired
    private boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}