package io.github.sachithariyathilaka.service;

import io.github.sachithariyathilaka.entity.User;
import io.github.sachithariyathilaka.resource.JwtRequest;
import io.github.sachithariyathilaka.resource.request.UserRequest;

/**
 * Authentication service interface.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
public interface AuthenticationService {
    User register(UserRequest userRequest);
    String login(JwtRequest jwtRequest, String position);
}
