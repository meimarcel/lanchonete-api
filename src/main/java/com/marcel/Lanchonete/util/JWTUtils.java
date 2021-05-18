package com.marcel.Lanchonete.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.marcel.Lanchonete.config.SecurityConstant;
import com.marcel.Lanchonete.error.InvalidTokenException;
import com.marcel.Lanchonete.model.Manager;
import com.marcel.Lanchonete.service.ManagerDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {

    @Autowired
    private ManagerDetailService managerDetailService;
    
    public String generateAccessToken(String email) {
        return SecurityConstant.TOKEN_PREFIX + JWT.create().withIssuedAt(new Date(System.currentTimeMillis()))
            .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(SecurityConstant.EXPIRATION_TIME, TimeUnit.MINUTES)))
            .withSubject(email)
            .withIssuer(SecurityConstant.ISSUER)
            .sign(Algorithm.HMAC512(SecurityConstant.SECRET));
    }

    public Manager decodeToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(SecurityConstant.SECRET))
                .withIssuer(SecurityConstant.ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            String email = jwt.getSubject();
            return (Manager) managerDetailService.loadUserByUsername(email);
            
        } catch (JWTVerificationException ex) {
            throw new InvalidTokenException("Token expirado ou inválido.");
        }
    }
}
