package ds_dedkov.GradleExample.controller;

import ds_dedkov.GradleExample.domain.Person;
import ds_dedkov.GradleExample.repository.PersonRepository;
import ds_dedkov.GradleExample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class PersonController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    UserService userService;

    @PostMapping("add")
    public ResponseEntity createPerson(@RequestBody RegistryForm RegistryForm) {
        return userService.createPerson(RegistryForm);
    }
    @GetMapping("getAll")
    public ResponseEntity getAllPerson() {
        return userService.getAllPerson();
    }

    @GetMapping("get/{id}")
    public ResponseEntity getPersonById(@PathVariable(value = "id") long personId) {
        return userService.getPersonById(personId);
    }

    @PutMapping("update/{id}")
    public Person updatePerson(@PathVariable(value = "id") long personId,
                               @RequestBody Person personDetails) {

        return userService.updatePerson(personId, personDetails);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable(value = "id") long personId) {
        return userService.deletePerson(personId);
    }


}
