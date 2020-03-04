package ds_dedkov.GradleExample.repository;


import ds_dedkov.GradleExample.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long> {
    Boolean existsByLogin(String login);
    Optional<Person>findByEmail(String login);
    Person findByLogin(String username);

}
