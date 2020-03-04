package ds_dedkov.GradleExample.controller;

import ds_dedkov.GradleExample.domain.Person;
import ds_dedkov.GradleExample.repository.PersonRepository;
import ds_dedkov.GradleExample.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("users")
public class LoginController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    LoginService loginService;


    @PostMapping("login")
    public ResponseEntity login(@RequestBody SighUpForm sighUpForm){
        ResponseEntity login = loginService.login(sighUpForm);

        return login;
    }
}
