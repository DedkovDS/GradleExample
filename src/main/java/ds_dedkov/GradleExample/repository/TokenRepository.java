package ds_dedkov.GradleExample.repository;

import ds_dedkov.GradleExample.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Token findByValue(String value);
    Token deleteByValue(String value);
}
