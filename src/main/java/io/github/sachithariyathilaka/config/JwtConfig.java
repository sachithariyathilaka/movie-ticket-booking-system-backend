package io.github.sachithariyathilaka.config;

import io.github.sachithariyathilaka.authentication.JwtAuthenticationEntryPoint;
import io.github.sachithariyathilaka.filter.JwtRequestFilter;
import io.github.sachithariyathilaka.util.JwtTokenUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * JWT configuration bean class.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
@Configuration
public class JwtConfig {

    @Value("${jwt-token-secret}")
    private String tokenSecret;

    @Value("${jwt-token-validity-period}")
    private long tokenValidityPeriod;

    private final UserDetailsService userDetailsService;

    public JwtConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(tokenValidityPeriod, tokenSecret, SignatureAlgorithm.HS512);
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(jwtTokenUtil(), userDetailsService);
    }
}
