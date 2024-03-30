package cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

public class Return {

    private final Purchase purchase;

    private final LocalDateTime date = LocalDateTime.now();

    @JsonIgnore
    public Purchase getPurchase() {
        return purchase;
    }

    public Seat getTicket() {
        return getPurchase().getTicket();
    }

    public Return(Purchase purchase) {
        this.purchase = purchase;
        this.returnPurchase();
    }

    private void returnPurchase() {
        this.getPurchase().setReturned(true);
        this.getPurchase().getTicket().setPurchased(false);
    }

}
