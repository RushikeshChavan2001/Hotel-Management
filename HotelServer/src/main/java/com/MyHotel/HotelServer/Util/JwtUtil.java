package com.MyHotel.HotelServer.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtUtil {

    public String generateToken(Map<String, Object> extraClaim, UserDetails details){

        return Jwts.builder().setClaims(extraClaim).setSubject(details.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith( SignatureAlgorithm.HS256 ,getSigningKey()).compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);

    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName= extractUserName(token);
        return  (userName.equals(userDetails.getUsername()))  && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
    }

    private  <T> T extractClaim(String token , Function<Claims , T> claimsResolvers){
         final Claims claim = extractAllClaims(token);
         return claimsResolvers.apply(claim);
    }

    public String extractUserName(String token){

        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration( String token){
         return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }



    private Key getSigningKey(){

        byte[] keyBytes = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
