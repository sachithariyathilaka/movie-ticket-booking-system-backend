package io.github.sachithariyathilaka.controller;

import io.github.sachithariyathilaka.entity.Booking;
import io.github.sachithariyathilaka.resource.request.BookingRequest;
import io.github.sachithariyathilaka.resource.response.APIResponse;
import io.github.sachithariyathilaka.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Booking controller class.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
@RestController
@RequestMapping("/api/v1/booking")
@CrossOrigin
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    private static final Logger logger = Logger.getLogger(String.valueOf(BookingController.class));

    /**
     * Booking register controller method.
     *
     * @param   bookingRequest the booking request
     *
     * @return  the new ticket booking
     */
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody BookingRequest bookingRequest) {
        logger.info(getClass() + " << Ticket booking controller >>");

        APIResponse<Booking> apiResponse;
        Booking booking = bookingService.register(bookingRequest);

        if (booking != null) {
            apiResponse = new APIResponse<>(200, "Booking made succesfully!", booking);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            apiResponse = new APIResponse<>(500, "Error occurred while booking the ticket", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Booking search controller method.
     *
     * @param   userId the user id
     *
     * @return  the booking list
     */
    @GetMapping(value = "/search")
    public ResponseEntity<?> search(@RequestParam int userId) {
        logger.info(getClass() + " << Booking search controller >>");

        APIResponse<List<Booking>> apiResponse;
        List<Booking> bookings = bookingService.search(userId);

        if (!bookings.isEmpty()) {
            apiResponse = new APIResponse<>(200, "Bookings fetched succesfully!", bookings);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            apiResponse = new APIResponse<>(204, "Cannot found the bookings", bookings);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Booking cancel controller method.
     *
     * @param   id the booking id
     *
     * @return  the booking
     */
    @PutMapping(value = "/cancel")
    public ResponseEntity<?> cancel(@RequestParam int id) {
        logger.info(getClass() + " << Booking cancel controller >>");

        APIResponse<Booking> apiResponse;
        Booking booking = bookingService.cancel(id);

        if (booking != null) {
            apiResponse = new APIResponse<>(200, "Booking cancelled successfully!", booking);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            apiResponse = new APIResponse<>(200, "Error occurred while cancelling the booking", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
