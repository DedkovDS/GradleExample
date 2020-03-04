package ds_dedkov.GradleExample.service;

import ds_dedkov.GradleExample.controller.RegistryForm;
import ds_dedkov.GradleExample.domain.Person;
import ds_dedkov.GradleExample.domain.Role;
import ds_dedkov.GradleExample.exception.ExceptionPerson;
import ds_dedkov.GradleExample.repository.PersonRepository;
import ds_dedkov.GradleExample.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PersonRepository personRepository;
    @Autowired
    TokenRepository tokenRepository;
    public ResponseEntity getPersonById(@PathVariable(value = "id") long personId) {
        return new ResponseEntity(personRepository.findById(personId), HttpStatus.OK);
    }
    public ResponseEntity<String> getAllPerson() {
        return new ResponseEntity(personRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity createPerson(RegistryForm RegistryForm) {
        if (personRepository.existsByLogin(RegistryForm.getLogin())) {
            return new ResponseEntity("Exists", HttpStatus.BAD_REQUEST);
        } else {
            Person person = new Person(RegistryForm.getLogin(), RegistryForm.getPassword(), RegistryForm.getName(),Role.USER );
            person.setPassword(passwordEncoder.encode(RegistryForm.getPassword()));
            return new ResponseEntity<>(personRepository.save(person), HttpStatus.CREATED);
        }
    }

    public Person updatePerson(@PathVariable(value = "id") long personId,
                               @RequestBody Person personDetails) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ExceptionPerson("person", "id", personId));

        person.setName(personDetails.getName());
        person.setEmail(personDetails.getEmail());
        person.setPassword(passwordEncoder.encode(personDetails.getPassword()));

        Person updatedPerson = personRepository.save(person);
        return updatedPerson;
    }


    public ResponseEntity<?> deletePerson(@PathVariable(value = "id") long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ExceptionPerson("person", "id", personId));

        personRepository.delete(person);
        return ResponseEntity.ok().build();
    }


}
