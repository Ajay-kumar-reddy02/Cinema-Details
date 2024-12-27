package com.cinemaDetails.security;

import com.cinemaDetails.exception.APIException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

//Utility Class
@Component
public class JWTTokenProvider {

    @Value(value = "${app.jwt-secret}")
    private String jwtSecret;
    @Value(value = "${app.jwt-expiration-milliseconds}")
    private long expirationTime;

    //Generate JWT Toke
    public String generateJWTToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + expirationTime);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expiryDate)
                .signWith(key())
                .compact();

        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //Get Username from the JWT token
    public String getUserNameFromJWT(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //Validate JWT Token
    public boolean validateJWTToken(String token){
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
            return true;
        }catch (Exception exception){
            throw new APIException(HttpStatus.BAD_REQUEST, "JWT Error");
        }
    }
}
