package pl.edu.agh.tai.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.agh.tai.model.User;
import pl.edu.agh.tai.services.IUserService;
import pl.edu.agh.tai.util.UserException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenAuthProvider implements AuthenticationProvider {

    @Autowired
    private IUserService userService;


    @Override
    public Authentication authenticate(Authentication authToken)
            throws AuthenticationException {

        // get the token from the authentication object
        String username = authToken.getPrincipal().toString();
        String password = authToken.getCredentials().toString();

        // get the user from DB, check credentials and create new token
        User user = null;
        UsernamePasswordAuthenticationToken newToken = null;


        try {
            user = userService.getUserByUsername(username);
        } catch (UserException e) {
            e.printStackTrace();
        }


        if (user == null){
            throw new BadCredentialsException("Invalid username");
        }
        else{
            String role = user.getRole();
            List<GrantedAuthority> authorities = new ArrayList<>();

            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            authorities.add(authority);
            if (new BCryptPasswordEncoder().matches(password,user.getPassword())) {
                newToken = new UsernamePasswordAuthenticationToken(username, password, authorities);
            }
            else    {
                throw new BadCredentialsException("Invalid password");
            }

        }
        return newToken;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(arg0));
    }
}

