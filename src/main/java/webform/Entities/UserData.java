package webform.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Date;

@Entity
@Table(name = "user_data")
public class UserData implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String login;
    private String name;
    private String surname;
    private Date birthdate;
    private Long userid;

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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Long getUserId() {
        return userid;
    }

    public void setUserId(Long userid) {
        this.userid = userid;
    }

    public void update(UserData user) {
        for (Field field : UserData.class.getDeclaredFields()) {
            try {
                Object value = field.get(user);
                if (value != null && value.toString().length() > 0) {
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public String validate() {
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
        if (birthdate == null) {
            return "birthdate";
        }
        return "ok";
    }

}
