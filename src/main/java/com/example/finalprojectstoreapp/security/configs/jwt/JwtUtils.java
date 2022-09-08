package com.example.finalprojectstoreapp.security.configs.jwt;

import com.example.finalprojectstoreapp.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Log4j2
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Method to generate Jwt token
     *
     * @param authentication The Authentication object on the basic of which the token is generated
     * @return Jwt token
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        String username = userPrincipal.getUsername();

        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    /**
     * Method to validate Jwt Token
     *
     * @param jwt Jwt token string
     * @return true is valid, false is invalid
     */
    public boolean validateJwtToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException | IllegalArgumentException e) {
            log.info(e.getMessage());
        }
        return false;
    }

    /**
     * Method to get username from jwt token
     *
     * @param jwt Jwt token string
     * @return user`s username
     */
    public String getUserNameFromJwtToken(String jwt) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
    }

    /**
     * Method to parse Jwt
     *
     * @param request HttpServletRequest
     * @return token value string
     */
    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    /**
     * Method to get username by request token
     *
     * @param request HttpServletRequest
     * @return user`s username
     */
    public String getUsernameByRequestToken(HttpServletRequest request) {
        String token = parseJwt(request);

        return getUserNameFromJwtToken(token);
    }
}