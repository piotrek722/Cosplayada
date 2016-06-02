package pl.edu.agh.tai.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.agh.tai.util.SecurityConstants;
import pl.edu.agh.tai.util.UserException;

import java.util.ArrayList;

@Service
public class UserService implements IUserService{

    @Override
    public String getRole() throws UserException {

        ArrayList<GrantedAuthority> roles = (ArrayList<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        // this is simplistic because we have only one role per user right now
        if (!roles.isEmpty() && roles.get(0).getAuthority().equals(SecurityConstants.ROLE_USER))
            return SecurityConstants.ROLE_USER;
        else
            throw new UserException("No Authentication");

    }
}
