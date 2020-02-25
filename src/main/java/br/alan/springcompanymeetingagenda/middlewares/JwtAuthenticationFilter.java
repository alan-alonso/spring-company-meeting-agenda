package br.alan.springcompanymeetingagenda.middlewares;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import br.alan.springcompanymeetingagenda.web.models.LoginDto;
import br.alan.springcompanymeetingagenda.web.models.UserPrincipal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * JwtAuthenticationFilter
 */
@Getter
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // == fields ==
    private final long jwtExpirationTime;
    private final String jwtSecret;
    private final AuthenticationManager authenticationManager;

    // == public methods ==
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        // Grab credentials and map them to login viewmodel
        LoginDto loginDto;
        try (ServletInputStream servletInputStream = request.getInputStream()) {
            loginDto = new ObjectMapper().readValue(servletInputStream, LoginDto.class);
        } catch (IOException e) {
            throw new BadCredentialsException("Bad credentials!");
        }

        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                        loginDto.getPassword(), new ArrayList<>());

        // Authenticate user
        Authentication auth = this.authenticationManager.authenticate(authenticationToken);

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        // Grab principal
        UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();

        // Add user authorities to token claims
        String[] authorities = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toArray(String[]::new);

        // Create JWT Token
        String token = JWT.create().withSubject(userPrincipal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.getJwtExpirationTime()))
                .withArrayClaim("authorities", authorities)
                .sign(Algorithm.HMAC512(this.getJwtSecret().getBytes()));

        // Setup response
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Authorization", "Bearer " + token);
    }

}
