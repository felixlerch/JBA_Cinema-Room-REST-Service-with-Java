package cinema.service;

import cinema.entity.Cinema;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {

    private final Cinema cinema;

    public Cinema getCinema() {
        return cinema;
    }

    public CinemaService() {
        this.cinema = new Cinema(9,9, "super_secret");
    }

}
