package webform;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;

@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;
    private String email;
    private String login;
    private String name;
    private String surname;
    private String birthdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void update(User user) {
        for (Field field : User.class.getDeclaredFields()) {
            try {
                Object value = field.get(user);
                if (value.toString().length() > 0) {
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public String validate() {
        if (id == null) {
            return "id";
        }
        if (email == null || (email.length() > 0 && !email.matches(".+@.+"))) {
            return "email";
        }
        if (login == null || (login.length() > 0 && !login.matches("[a-zA-Z0-9_]{1,20}"))) {
            return "login";
        }
        if (name == null || (name.length() > 0 && !name.matches("[a-zA-Z-]{1,20}"))) {
            return "name";
        }
        if (surname == null || (surname.length() > 0 && !surname.matches("[a-zA-Z-]{1,20}"))) {
            return "surname";
        }
        if (birthdate == null || (birthdate.length() > 0 && !birthdate.matches("\\d{4}-\\d{2}-\\d{2}"))) {
            return "birthdate";
        }
        return "ok";
    }

}
