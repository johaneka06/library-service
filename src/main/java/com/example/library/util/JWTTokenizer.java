package com.example.library.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 /*
    Project     : Library Service
    Class       : JWTTokenizer.java
    Author      : Johan Eka Santosa
    Created On  : Tue, 08-06-2021

    Version History:
        v1.0.0 => Initial Work (Tue, 08-06-2021 by Johan Eka Santosa)
 */

public class JWTTokenizer {
    private static String keyStr = "167Upf7hFKL5NqF1i86KNEirnh55BwciddiVzhXnP0gV0rMD81fNTdXMVFYxLmYF";
    private static SecretKey key = Keys.hmacShaKeyFor(keyStr.getBytes());

    public static String generateJWT(HashMap<String, String> claims) {
        JwtBuilder jwt = Jwts.builder();
        for(Map.Entry<String, String> claim : claims.entrySet()) {
            jwt.claim(claim.getKey(), claim.getValue());
        }
        return jwt.signWith(key).compact();
    }

    public static String generateObjectJWT(HashMap<String, Object> claims) {
        JwtBuilder jwt = Jwts.builder();
        for(Map.Entry<String, Object> claim : claims.entrySet()) {
            jwt.claim(claim.getKey(), claim.getValue());
        }
        return jwt.signWith(key).compact();
    }

    public static String generateListJwt(List<HashMap<String, String>> claims) {
        JwtBuilder jwt = Jwts.builder();
        int i = 1;
        for(HashMap<String, String> claim : claims) {
            jwt.claim(Integer.toString(i), claim);
            i++;
        }
        return jwt.signWith(key).compact();
    }

    public static Claims validateJWT(String jwtString) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(keyStr.getBytes()).parseClaimsJws(jwtString);
            return claims.getBody();
        } catch (JwtException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
