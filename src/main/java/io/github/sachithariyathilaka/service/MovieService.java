package io.github.sachithariyathilaka.service;

import io.github.sachithariyathilaka.entity.Movie;
import io.github.sachithariyathilaka.resource.request.MovieRequest;

import java.util.List;

/**
 * Movie service interface.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
public interface MovieService {
    Movie register(MovieRequest movieRequest);
    List<Movie> search(String name);
    void delete(int id);
}
