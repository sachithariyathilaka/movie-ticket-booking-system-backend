package io.github.sachithariyathilaka.controller;

import io.github.sachithariyathilaka.entity.User;
import io.github.sachithariyathilaka.resource.JwtRequest;
import io.github.sachithariyathilaka.resource.request.UserRequest;
import io.github.sachithariyathilaka.resource.response.APIResponse;
import io.github.sachithariyathilaka.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * Authentication controller class.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
@RestController
@RequestMapping("/api/v1/authentication")
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    private static final Logger logger= Logger.getLogger(String.valueOf(AuthenticationController.class));

    /**
     * User registration controller method.
     *
     * @param   userRequest the user request
     *
     * @return  the new user id
     */
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        logger.info(getClass() +" << User register controller >>");

        APIResponse<Integer> apiResponse;
        User user = authenticationService.register(userRequest);

        if (user != null)
        {
            apiResponse = new APIResponse<>(200, "User registration success", user.getId());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            apiResponse = new APIResponse<>(500, "Error occurred while registering user", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * User login controller method.
     *
     * @param   jwtRequest the jwt request
     * @param   position the position
     *
     * @return  the jwt token
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest, @RequestParam String position) {
        logger.info(getClass() + " << User login controller >>");

        APIResponse<String> apiResponse;
        String token = authenticationService.login(jwtRequest, position);

        if (token != null) {
            apiResponse = new APIResponse<>(200, "User authenticated", token);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            apiResponse = new APIResponse<>(401, "User credentials invalid", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
        }
    }
}
