package webform.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionResponse {
    @JsonProperty("error")
    private String error;

    @JsonProperty("token")
    private String token;

    public SessionResponse(String error, String token) {
        this.error = error;
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
