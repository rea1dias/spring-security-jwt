package com.example.systemauthorization.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "259caafb4a6c92bc4ce22fdc043c5ff5cca57c3455ed8af4ee621c65f2c79d2ef6fcf61aad8ed3aee19fa4c8bf90fb5eae686096d9f6829e0cd71f0f511842b137877f96b3b59fa6c7dc7372f80372d82ab8570edb45e120e1ed403a08c77899f622407d697d941d1573917bc2d29507df2b1f4ae102eac35767031cb2f43e264e98210bc05df99d638cc532bc7bc60172b8c4a3c7751788a46ee9cf999f57aa351d6af1633d1839c690b5ab9eac40a840503b17fae2135c84091ee95bae858253645a831c0830d581337fc06e16bf485daeb129894ef54fbde77533e132d9c6936faac072af5ea895c942d1cb266defbfe5bc54b34ee619312059f66d7a4d97";
    private static final long EXPIRATION_TIME = 89990000L;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public static Date extractExpirationDate(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public static boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

}
