package pl.edu.agh.tai.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pl.edu.agh.tai.util.SecurityConstants;

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenAuthProvider implements AuthenticationProvider {

    @Value("${user.auth.token}")
    private String userKey;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {

        // get the token from the authentication object
        String token = auth.getCredentials().toString();

        // hash the token in order to compare to value in database  / property file
        String hashedToken = new Md5PasswordEncoder().encodePassword(token, SecurityConstants.HASH_SALT);

        // construct a new authentication object with correct grants
        List<GrantedAuthority> grants = new ArrayList<>();
        UserAuthentication userAuth = new UserAuthentication(token);

        // do the authentication (compare against DB / properties)
        if (hashedToken.equals(userKey)) {
            grants.add(new SimpleGrantedAuthority(SecurityConstants.ROLE_USER));
            userAuth.setAuthorities(grants);
            userAuth.setAuthenticated(true);
        }
        else    {
            throw new BadCredentialsException("Invalid token " + token);
        }

        return userAuth;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return (UserAuthentication.class.isAssignableFrom(arg0));

    }
}
