package io.github.sachithariyathilaka.service.Impl;

import io.github.sachithariyathilaka.entity.Booking;
import io.github.sachithariyathilaka.entity.Movie;
import io.github.sachithariyathilaka.repository.MovieRepository;
import io.github.sachithariyathilaka.repository.BookingRepository;
import io.github.sachithariyathilaka.resource.request.BookingRequest;
import io.github.sachithariyathilaka.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Booking service implementation class.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, MovieRepository movieRepository) {
        this.bookingRepository = bookingRepository;
        this.movieRepository = movieRepository;
    }

    private static final Logger logger= Logger.getLogger(String.valueOf(BookingServiceImpl.class));

    /**
     * Booking registration service method.
     *
     * @param   bookingRequest the booking request
     *
     * @return  the new booking
     */
    @Override
    public Booking register(BookingRequest bookingRequest) {
        logger.info(getClass() +" << Ticket booking service >>");

        Booking booking = new Booking();
        booking.setMovie(bookingRequest.getMovie());
        booking.setDate(bookingRequest.getDate());
        booking.setPaymentStatus(bookingRequest.isPaymentStatus());
        booking.setShowTime(bookingRequest.getShowTime());
        booking.setTicketCount(bookingRequest.getTicketCount());
        booking.setUserId(bookingRequest.getUserId());

        List<Movie> movies = movieRepository.findByName(bookingRequest.getMovie());

        if (!movies.isEmpty() && (movies.get(0).getAvailableTickets() > booking.getTicketCount() || movies.get(0).getAvailableTickets() == booking.getTicketCount())) {
            Movie movie = movies.get(0);
            movie.setAvailableTickets(movie.getAvailableTickets() - booking.getTicketCount());
            movieRepository.saveAndFlush(movie);

            return bookingRepository.saveAndFlush(booking);
        } else
            return null;
    }

    /**
     * Booking search service method.
     *
     * @param   userId the user id
     *
     * @return  the booking list
     */
    @Override
    public List<Booking> search(int userId) {
        return bookingRepository.findAllByUserId(userId);
    }

    /**
     * Booking cancel service method.
     *
     * @param   id the movie id
     */
    @Override
    public Booking cancel(int id) {
        Optional<Booking> optionalTicket = bookingRepository.findById(id);

        if (optionalTicket.isPresent()) {
            Booking booking = optionalTicket.get();
            List<Movie> movies = movieRepository.findByName(booking.getMovie());

            if (movies.isEmpty())
                return null;

            Movie movie = movies.get(0);
            movie.setAvailableTickets(movie.getAvailableTickets() + booking.getTicketCount());

            movieRepository.saveAndFlush(movie);
            bookingRepository.deleteById(id);

            return booking;
        } else
            return null;
    }
}
