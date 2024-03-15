package com.template.eazypos.security;


import com.template.eazypos.model.Pengguna;
import com.template.eazypos.model.User;
import com.template.eazypos.repository.PenggunaRepository;
import com.template.eazypos.repository.UserRepository;
import com.template.eazypos.service.auth.UserDetail;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.util.Date;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private String jwtSecret = "eazypos";
    private int jwtExpirationMs = 604800000;
    private static final String SECRET_KEY = "eazypos";

    @Autowired
    PenggunaRepository userRepository;

    public String generateToken(Authentication authentication) {
        UserDetail adminPrincipal = (UserDetail) authentication.getPrincipal();
        Pengguna user = userRepository.findByUsername(adminPrincipal.getUsername()).get();
        return Jwts.builder()
                .claim("name", user.getNamaPengguna())
                .claim("last_login" , user.getLastLogin())
                .setSubject(adminPrincipal.getUsername())
                .claim("id" , adminPrincipal.getId())
                .setAudience(user.getLevelPengguna())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    public static Claims decodeJwt(String jwtToken) {
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwtToken);

        return jwsClaims.getBody();
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
