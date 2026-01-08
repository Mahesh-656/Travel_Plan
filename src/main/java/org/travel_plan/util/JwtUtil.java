package org.travel_plan.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY="M@heshiiiiiwarmdnfdnfdnjfnds_#$nnfgnfjnfnnfngndn";
    private final long EXP_TIME=1000*60*60;
    private final Key key= Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String email,String role){
        return Jwts
                .builder()
                .subject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXP_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String email, UserDetails userDetails, String token) {
            return email.equals(userDetails.getUsername())&&!isTokenExpired(token);
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
