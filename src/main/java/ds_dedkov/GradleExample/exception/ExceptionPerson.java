package ds_dedkov.GradleExample.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Data
public class ExceptionPerson extends RuntimeException {
    private String name;
    private String email;
    private Object fieldValue;

    public ExceptionPerson(String name, String email, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", name, email, fieldValue));
        this.name = name;
        this.email = email;
        this.fieldValue = fieldValue;
    }


}
