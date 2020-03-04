package ds_dedkov.GradleExample.security;

import ds_dedkov.GradleExample.domain.Person;
import ds_dedkov.GradleExample.repository.PersonRepository;
import ds_dedkov.GradleExample.service.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider provider;

    @Autowired
    PersonRepository personRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authorizationHeader = request.getHeader("ACCESS");
            Person personById = provider.getPersonById(authorizationHeader);
            log.info("Profile name {}, password {}, roles {}", personById.getLogin(), personById.getPassword(),personById.getRole());
            if (provider.validate(authorizationHeader)) {
                Authentication auth = new UsernamePasswordAuthenticationToken(
                   personById.getLogin(),personById.getPassword(), Collections.singleton(personById.getRole()));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

}
