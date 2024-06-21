package io.github.sachithariyathilaka.controller;

import io.github.sachithariyathilaka.entity.Movie;
import io.github.sachithariyathilaka.resource.request.MovieRequest;
import io.github.sachithariyathilaka.resource.response.APIResponse;
import io.github.sachithariyathilaka.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Movie controller class.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
@RestController
@RequestMapping("/api/v1/movie")
@CrossOrigin
public class MovieController {
    private final MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    private static final Logger logger = Logger.getLogger(String.valueOf(MovieController.class));

    /**
     * Movie registration controller method.
     *
     * @param   movieRequest the movie request
     *
     * @return  the new movie
     */
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody MovieRequest movieRequest) {
        logger.info(getClass() + " << Movie register controller >>");

        APIResponse<Movie> apiResponse;
        Movie movie = movieService.register(movieRequest);

        if (movie != null) {
            apiResponse = new APIResponse<>(200, "Movie registered succesfully!", movie);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            apiResponse = new APIResponse<>(500, "Error occurred while registering the movie", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Movie search controller method.
     *
     * @param   name the movie name
     *
     * @return  the movie list
     */
    @GetMapping(value = "/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String name) {
        logger.info(getClass() + " << Movie search controller >>");

        APIResponse<List<Movie>> apiResponse;
        List<Movie> movies = movieService.search(name);

        if (!movies.isEmpty())
        {
            apiResponse = new APIResponse<>(200, "Movies fetched succesfully!", movies);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else
        {
            apiResponse = new APIResponse<>(204, "Cannot found the movies", movies);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Movie delete controller method.
     *
     * @param   id the movie id
     *
     * @return  the movie id
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestParam int id) {
        logger.info(getClass() + " << Movie delete controller >>");

        movieService.delete(id);
        APIResponse<Integer> apiResponse = new APIResponse<>(200, "Movie deleted successfully!", id);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
