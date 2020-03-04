package ds_dedkov.GradleExample.service;

import ds_dedkov.GradleExample.controller.SighUpForm;
import ds_dedkov.GradleExample.domain.Person;
import ds_dedkov.GradleExample.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    TokenProvider provider;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity login(SighUpForm signUpForm) {
        if (personRepository.existsByLogin(signUpForm.getLogin())) {
            Person person = personRepository.findByLogin(signUpForm.getLogin());

            if (passwordEncoder.matches(signUpForm.getPassword(),person.getPassword())) {
                return new ResponseEntity(provider.generateToken(person), HttpStatus.OK);

            }

        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
