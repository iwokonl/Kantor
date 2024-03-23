package pl.kantor.backend.MVC.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.kantor.backend.MVC.dto.UserDto;
import pl.kantor.backend.MVC.exeption.AppExeption;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());

    }
//KeycLoak
    public String createToken(UserDto dto) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 360000);
        return JWT.create()
                .withIssuer(dto.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("id", dto.getId())
                .withClaim("firstName", dto.getFirstName())
                .withClaim("lastName", dto.getLastName())
                .sign(Algorithm.HMAC256(secretKey));

    }

    public Authentication validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = verifier.verify(token);

            UserDto user = UserDto.builder()
                    .username(decodedJWT.getIssuer())
                    .id(decodedJWT.getClaim("id").asLong())
                    .firstName(decodedJWT.getClaim("firstName").asString())
                    .lastName(decodedJWT.getClaim("lastName").asString())
                    .build();
            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

        } catch (JWTVerificationException exception) {
            if(exception.getMessage().contains("The Token has expired on")) {
                DecodedJWT decodedJWT = JWT.decode(token);
                UserDto user = UserDto.builder()
                        .username(decodedJWT.getIssuer())
                        .id(decodedJWT.getClaim("id").asLong())
                        .firstName(decodedJWT.getClaim("firstName").asString())
                        .lastName(decodedJWT.getClaim("lastName").asString())
                        .build();
                String newToken = createToken(user);
                return new UsernamePasswordAuthenticationToken(user, newToken, Collections.emptyList());
            }
            else {
                throw new AppExeption("Does not apply to token", HttpStatus.UNAUTHORIZED);
            }
//            throw new AppExeption("Expired token", HttpStatus.UNAUTHORIZED);

        }
    }
}
