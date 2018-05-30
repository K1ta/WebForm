package webform.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
