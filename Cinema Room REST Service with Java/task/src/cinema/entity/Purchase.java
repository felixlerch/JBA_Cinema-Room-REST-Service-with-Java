package cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

public class Purchase {

    private final UUID token;

    private final Seat ticket;

    private boolean returned = false;

    private final LocalDateTime date = LocalDateTime.now();

    public UUID getToken() {
        return token;
    }

    public Seat getTicket() {
        return ticket;
    }

    @JsonIgnore
    public boolean isReturned() {
        return returned;
    }

    @JsonIgnore
    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public Purchase(Seat seat) {
        this.token = UUID.randomUUID();
        this.ticket = seat;
        this.setSeatPurchased();
    }

    private void setSeatPurchased() {
        getTicket().setPurchased(true);
    }

}
