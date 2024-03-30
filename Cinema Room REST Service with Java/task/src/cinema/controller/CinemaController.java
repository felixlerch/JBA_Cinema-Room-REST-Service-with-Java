package cinema.controller;

import cinema.entity.Cinema;
import cinema.entity.Purchase;
import cinema.entity.Return;
import cinema.entity.Seat;
import cinema.exceptions.SeatAlreadyPurchasedException;
import cinema.exceptions.WrongPasswordException;
import cinema.exceptions.WrongPurchaseTockenException;
import cinema.objects.ReturnRequest;
import cinema.objects.Statistics;
import cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CinemaController {

    @Autowired
    private CinemaService service;

    @RequestMapping(method = RequestMethod.GET,value = "/seats")
    public Cinema getCinema() {
        return service.getCinema();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/purchase")
    public Purchase purchaseSeat(@RequestBody Seat seat) throws SeatAlreadyPurchasedException, IndexOutOfBoundsException {
        return service.getCinema().purchaseSeat(seat);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/return")
    public Return returnPurchase(@RequestBody ReturnRequest request) throws WrongPurchaseTockenException {
        return service.getCinema().returnPurchase(request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats")
    public Statistics getStatistics(@RequestParam(required = false) String password) throws WrongPasswordException {
        return service.getCinema().generateStatistics(password);
    }

}
