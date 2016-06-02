package pl.edu.agh.tai.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthFilter tokenAuthFilter;

    @Autowired
    private TokenAuthProvider tokenAuthProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // no session management required
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // the following URLs should be permitted without any authentication
                // this includes our static resources
                // **Note** : Our landing page ("/") is the login page that
                //          should not be authenticated, so we add it here
                .antMatchers("/","/webjars/**", "/*.html").permitAll()
                // all other requests must be authenticated
                .antMatchers("/user/**").hasRole("PUBLIC")
                .antMatchers("/events").hasRole("LIBRARIAN")
                .and().authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                // remove basic HTTP authentication - we are writing our own login page
                //.httpBasic()
                //.and()
                // disable Cross Site Request Forgery token
                // we do not rely on cookie based auth and are completely stateless so we are safe
                .csrf().disable()
                // authentication for token based authentication
                .authenticationProvider(tokenAuthProvider)
                .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class);

    }

}