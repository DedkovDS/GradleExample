package ds_dedkov.GradleExample.service;

import ds_dedkov.GradleExample.domain.Person;
import ds_dedkov.GradleExample.domain.Token;
import ds_dedkov.GradleExample.repository.PersonRepository;
import ds_dedkov.GradleExample.repository.TokenRepository;
import io.jsonwebtoken.*;
import lombok.Value;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class TokenProvider {


    private long validityMiliSeconds = 3600000;


    private static final byte[] SECRET_KEY = {21, 32, 1};
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TokenRepository tokenRepository;


    public Token generateToken(Person person) {
        Set<Token> tokens = person.getTokens();

        Token token = new Token(createToken(person));

        tokens.add(token);

        person.setTokens(tokens);

        tokenRepository.save(token);
        personRepository.save(person);

        return token;
    }

    private String createToken(Person person) {
        Claims claims = Jwts.claims().setSubject(person.getName());
        claims.put("email", person.getEmail());
        Date validity = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(person.getId().toString())
                .setExpiration(new Date(validity.getTime() + validityMiliSeconds))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    public Person getPersonById(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        return personRepository.findById(Long.parseLong(claimsJws.getBody().getSubject())).orElse(null);
    }

    public Boolean validate(String tokenValue) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().
                    setSigningKey(SECRET_KEY).parseClaimsJws(tokenValue);
            {
                if (claimsJws.getBody().getExpiration().before(new Date())) {
                    return false;
                }
                return true;
            }

        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Expired or invalid JWT token");
        }
    }
}