package br.alan.springcompanymeetingagenda.middlewares;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * JwtAuthorizationFilter
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    // == fields ==
    private final String jwtSecret;

    // == constructors ==
    @Autowired
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String jwtSecret) {
        super(authenticationManager);
        this.jwtSecret = jwtSecret;
    }

    // == protected methods ==
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        // Read the Authorization header, where the JWT token should be
        String header = request.getHeader("Authorization");

        // If header does not contain "Bearer" or is null delegate to Spring impl and
        // exit
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // If header is present, try grab user principal
        Authentication authentication = this.getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    // == private methods ==
    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        if (token != null) {
            // parse the token and validate it
            String username = JWT.require(Algorithm.HMAC512(this.jwtSecret.getBytes())).build()
                    .verify(token).getSubject();

            List<? extends GrantedAuthority> authorities =
                    JWT.require(Algorithm.HMAC512(this.jwtSecret.getBytes())).build().verify(token)
                            .getClaim("authorities").asList(String.class).stream()
                            .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            return authenticationToken;
        }

        return null;
    }


}
