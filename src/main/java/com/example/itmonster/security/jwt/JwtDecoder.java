package com.example.itmonster.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.itmonster.exceptionHandler.CustomException;
import com.example.itmonster.exceptionHandler.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static com.example.itmonster.security.jwt.JwtTokenUtils.*;


@Component
public class JwtDecoder {


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public String decodeUsername(String token) {
        DecodedJWT decodedJWT = isValidToken(token)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_AUTH_TOKEN));

        Date expiredDate = decodedJWT
            .getClaim(CLAIM_EXPIRED_DATE)
            .asDate();

        Date now = new Date();
        if (expiredDate.before(now)) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        return decodedJWT
            .getClaim(CLAIM_USER_NAME)
            .asString();
    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT
                .require(algorithm)
                .build();

            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }
}
