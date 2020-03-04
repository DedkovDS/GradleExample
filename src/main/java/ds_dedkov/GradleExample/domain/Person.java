package ds_dedkov.GradleExample.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;

    private String login;
    @Column(length = 100)
    private String password;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Token> tokens;


    @Enumerated(EnumType.STRING)
    private Role role;


    public Person(String login, String password, String name,Role role) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role=role;

    }


}