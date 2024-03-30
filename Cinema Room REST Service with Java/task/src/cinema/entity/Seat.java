package cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Seat {

    private int row;
    private int column;
    private boolean purchased;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return getRow() > 4 ? 8 : 10;
    }

    @JsonIgnore
    public boolean isPurchased() {
        return purchased;
    }

    @JsonIgnore
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public Seat(@JsonProperty int row, @JsonProperty int column) {
        this.row = row;
        this.column = column;
    }

    public Seat() {

    }
}
