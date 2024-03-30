package cinema.entity;

import cinema.exceptions.SeatAlreadyPurchasedException;
import cinema.exceptions.WrongPasswordException;
import cinema.exceptions.WrongPurchaseTockenException;
import cinema.objects.ReturnRequest;
import cinema.objects.Statistics;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class Cinema {

    private final int rows;

    private final int columns;

    private final String password;

    private List<Seat> seats;

    private final List<Purchase> purchases = new ArrayList<>();

    private final List<Return> returned = new ArrayList<>();

    @JsonIgnore
    public List<Purchase> getPurchases() {
        return purchases;
    }

    @JsonIgnore
    public List<Return> getReturned() {
        return returned;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Cinema(int rows, int columns, String password) {
        this.rows = rows;
        this.columns = columns;
        this.password = password;
        this.generateNewSeats();
    }

    private void generateNewSeats() {
        this.seats = new ArrayList<>();
        for (int i = 1; i <= this.rows; i++) {
            for (int j = 1; j <= this.columns; j++) {
                this.seats.add(new Seat(i, j));
            }
        }
    }

    public boolean checkSeatIfPurchased(Seat seat) throws IndexOutOfBoundsException {
        return checkSeatIfPurchased(seat.getRow(), seat.getColumn());
    }

    public boolean checkSeatIfPurchased(int row, int column) throws IndexOutOfBoundsException {
        return getSeats()
                .stream()
                .filter(s -> s.getRow() == row && s.getColumn() == column)
                .map(Seat::isPurchased)
                .findFirst()
                .orElseThrow(this::getRowOutOfBoundsException);
    }

    public Seat getSeat(Seat seat) {
        return getSeat(seat.getRow(), seat.getColumn());
    }

    public Seat getSeat(int row, int column) throws IndexOutOfBoundsException {
        return getSeats()
                .stream()
                .filter(s -> s.getRow() == row && s.getColumn() == column)
                .findFirst()
                .orElseThrow(this::getRowOutOfBoundsException);
    }

    public Purchase purchaseSeat(Seat seat) throws SeatAlreadyPurchasedException, IndexOutOfBoundsException {
        boolean purchased = checkSeatIfPurchased(seat.getRow(), seat.getColumn());
        if (purchased) {
            throw new SeatAlreadyPurchasedException("The ticket has been already purchased!");
        } else {
            Seat purchasedSeat = getSeat(seat.getRow(), seat.getColumn());
            Purchase purchase = new Purchase(purchasedSeat);
            getPurchases().add(purchase);
            return purchase;
        }
    }

    public Purchase getPurchase(UUID token) throws WrongPurchaseTockenException {
        return getPurchases()
                .stream()
                .filter(p -> p.getToken().equals(token) && !p.isReturned())
                .findFirst()
                .orElseThrow(this::getWrongPurchaseTockenException);
    }

    public Return returnPurchase(ReturnRequest request) throws WrongPurchaseTockenException {
        return returnPurchase(request.getToken());
    }

    public Return returnPurchase(UUID token) throws WrongPurchaseTockenException {
        Purchase purchase = getPurchase(token);
        Return purchaseReturn = new Return(purchase);
        getReturned().add(purchaseReturn);
        return purchaseReturn;
    }

    public Statistics generateStatistics(String password) throws WrongPasswordException {
        boolean checkedPassword = checkPassword(password);
        if (!checkedPassword) {
            throw getWrongPasswordException();
        }

        Statistics statistics = new Statistics();
        statistics.setIncome(getNumOfIncomeTickets());
        statistics.setAvailable(getNumOfAvailableTickets());
        statistics.setPurchased(getNumOfPurchasedTickets());
        return statistics;
    }

    private int getNumOfIncomeTickets() {
        return getSeats().stream().filter(Seat::isPurchased).map(Seat::getPrice).mapToInt(Integer::intValue).sum();
    }

    private int getNumOfAvailableTickets() {
        return Math.toIntExact(getSeats().stream().filter(p -> !p.isPurchased()).count());
    }

    private int getNumOfPurchasedTickets() {
        return Math.toIntExact(getSeats().stream().filter(Seat::isPurchased).count());
    }

    public boolean checkPassword(String password) {
        return Objects.equals(this.password, password);
    }

    private WrongPasswordException getWrongPasswordException() {
        return new WrongPasswordException("The password is wrong!");
    }

    private WrongPurchaseTockenException getWrongPurchaseTockenException() {
        return new WrongPurchaseTockenException("Wrong token!");
    }

    private IndexOutOfBoundsException getRowOutOfBoundsException() {
        return new IndexOutOfBoundsException("The number of a row or a column is out of bounds!");
    }

}
