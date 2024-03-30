package cinema.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ReturnRequest {

    private UUID token;

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public ReturnRequest(UUID token) {
        this.token = token;
    }

    public ReturnRequest() {

    }
}
