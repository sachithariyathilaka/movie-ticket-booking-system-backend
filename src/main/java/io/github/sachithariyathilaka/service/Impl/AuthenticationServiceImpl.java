package io.github.sachithariyathilaka.service.Impl;

import io.github.sachithariyathilaka.entity.User;
import io.github.sachithariyathilaka.repository.UserRepository;
import io.github.sachithariyathilaka.resource.JwtRequest;
import io.github.sachithariyathilaka.resource.request.UserRequest;
import io.github.sachithariyathilaka.service.AuthenticationService;
import io.github.sachithariyathilaka.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Authentication service implementation class.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder bcryptEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    private static final Logger logger= Logger.getLogger(String.valueOf(AuthenticationService.class));

    /**
     * User registration service method.
     *
     * @param   userRequest the user request
     *
     * @return  the new user
     */
    @Override
    public User register(UserRequest userRequest) {
        logger.info(getClass() +" << User register service >>");

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
        user.setName(userRequest.getName());
        user.setPosition(userRequest.getPosition());
        user.setMobile(userRequest.getMobile());
        return userRepository.saveAndFlush(user);
    }

    /**
     * User login service method.
     *
     * @param   jwtRequest the jwt request
     * @param   position the position
     *
     * @return  the jwt token
     */
    @Override
    public String login(JwtRequest jwtRequest, String position) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));

            Optional<User> optionalUser = userRepository.findByUsernameAndPosition(jwtRequest.getUsername(), position);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());

                HashMap<String, Object> claimMap = new HashMap<>();
                claimMap.put("user", user);

                String token = jwtTokenUtil.generateToken(claimMap, userDetails.getUsername());

                logger.info(getClass() +" << User authenticated >>");
                return token;
            } else {
                logger.info(getClass() +" << User credentials invalid >>");
                return null;
            }
        } catch (BadCredentialsException e) {
            logger.info(getClass() +" << User credentials invalid >>");
            return null;
        }
    }
}
