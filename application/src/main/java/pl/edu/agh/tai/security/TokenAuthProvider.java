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

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenAuthProvider implements AuthenticationProvider {

    @Value("${fpl.auth.token}")
    private String fplKey;

    @Value("${public.auth.token}")
    private String publicKey;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {

        // get the token from the authentication object
        String token = auth.getCredentials().toString();

        // hash the token in order to compare to value in database  / property file
        String hashedToken = new Md5PasswordEncoder().encodePassword(token, LibraryConstants.HASH_SALT);

        // construct a new authentication object with correct grants
        List<GrantedAuthority> grants = new ArrayList<>();
        LibraryAuthentication libAuth = new LibraryAuthentication(token);

        // do the authentication (compare against DB / properties)
        if (hashedToken.equals(fplKey)) {
            grants.add(new SimpleGrantedAuthority(LibraryConstants.ROLE_LIBRARIAN));
            libAuth.setAuthorities(grants);
            libAuth.setAuthenticated(true);
        }
        else if (hashedToken.equals(publicKey)) {
            grants.add(new SimpleGrantedAuthority(LibraryConstants.ROLE_PUBLIC));
            libAuth.setAuthorities(grants);
            libAuth.setAuthenticated(true);
        }
        else    {
            throw new BadCredentialsException("Invalid token " + token);
        }

        return libAuth;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return (LibraryAuthentication.class.isAssignableFrom(arg0));

    }
}
