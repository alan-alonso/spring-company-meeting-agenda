package br.alan.springcompanymeetingagenda.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.alan.springcompanymeetingagenda.middlewares.JwtAuthenticationFilter;
import br.alan.springcompanymeetingagenda.middlewares.JwtAuthorizationFilter;
import br.alan.springcompanymeetingagenda.utils.Mappings;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * SecurityConfig
 */
@RequiredArgsConstructor
@EnableWebSecurity
@ConfigurationProperties(value = "app", ignoreUnknownFields = false)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // == fields ==
    private final UserDetailsService userDetailsService;

    @Setter
    private long jwtExpirationTime;

    @Setter
    private String jwtSecret;

    // == protected methods ==
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // remove csrf and state in session because in jwt we do not need them
        http.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
                this.jwtExpirationTime, this.jwtSecret, super.authenticationManager());
        jwtAuthenticationFilter.setFilterProcessesUrl(Mappings.AUTH_PATH + "/login");
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());

        JwtAuthorizationFilter jwtAuthorizationFilter =
                new JwtAuthorizationFilter(super.authenticationManager(), this.jwtSecret);

        // add jwt filters (1. authentication, 2. authorization)
        http.addFilter(jwtAuthenticationFilter).addFilter(jwtAuthorizationFilter);

        // configure access rules
        http.authorizeRequests()
                .antMatchers(Mappings.RESOURCE_TYPES_PATH + "/**", Mappings.RESOURCES_PATH + "/**")
                .hasRole("ADMIN").antMatchers(HttpMethod.GET, Mappings.MEETINGS_PATH + "/**")
                .permitAll().antMatchers(Mappings.AUTH_PATH + "/login").permitAll().anyRequest()
                .authenticated();
    }

    // == bean methods ==
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthencationProvider = new DaoAuthenticationProvider();
        daoAuthencationProvider.setPasswordEncoder(this.passwordEncoder());
        daoAuthencationProvider.setUserDetailsService(this.userDetailsService);

        return daoAuthencationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
