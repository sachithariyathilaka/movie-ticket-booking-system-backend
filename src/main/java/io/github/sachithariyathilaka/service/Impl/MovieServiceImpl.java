package io.github.sachithariyathilaka.service.Impl;

import io.github.sachithariyathilaka.entity.Movie;
import io.github.sachithariyathilaka.repository.MovieRepository;
import io.github.sachithariyathilaka.resource.request.MovieRequest;
import io.github.sachithariyathilaka.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

/**
 * Movie service implementation class.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    private static final Logger logger= Logger.getLogger(String.valueOf(MovieServiceImpl.class));

    /**
     * Movie registration service method.
     *
     * @param   movieRequest the movie request
     *
     * @return  the new movie
     */
    @Override
    public Movie register(MovieRequest movieRequest) {
        logger.info(getClass() +" << Movie register service >>");

        Movie movie = new Movie();
        movie.setName(movieRequest.getName());
        movie.setOwner(movieRequest.getOwner());
        movie.setPrice(movieRequest.getPrice());
        movie.setDate(movieRequest.getDate());
        movie.setShowTime(movieRequest.getShowTime());
        movie.setStatus(true);
        movie.setAvailableTickets(movieRequest.getAvailableTickets());
        return movieRepository.saveAndFlush(movie);
    }

    /**
     * Movie search service method.
     *
     * @param   name the movie name
     *
     * @return  the movie list
     */
    @Override
    public List<Movie> search(String name) {
        logger.info(getClass() +" << Movie search service >>");

        if (name != null && !name.isEmpty())
            return movieRepository.findByName(name);
        else
            return movieRepository.getAllByStatusTrue();
    }

    /**
     * Movie delete service method.
     *
     * @param   id the movie id
     */
    @Override
    public void delete(int id) {
        movieRepository.deleteById(id);
    }
}
