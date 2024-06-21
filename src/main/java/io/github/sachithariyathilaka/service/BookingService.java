package io.github.sachithariyathilaka.service;

import io.github.sachithariyathilaka.entity.Booking;
import io.github.sachithariyathilaka.resource.request.BookingRequest;

import java.util.List;

/**
 * Booking service interface.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
public interface BookingService {
    Booking register(BookingRequest bookingRequest);
    List<Booking> search(int userId);
    Booking cancel(int id);
}
