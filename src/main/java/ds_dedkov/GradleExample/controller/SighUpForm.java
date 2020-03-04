package ds_dedkov.GradleExample.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SighUpForm {
    private String login;
    private String password;
}
