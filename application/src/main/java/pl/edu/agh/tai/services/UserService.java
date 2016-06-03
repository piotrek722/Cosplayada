package pl.edu.agh.tai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.tai.model.LoginInfo;
import pl.edu.agh.tai.model.User;
import pl.edu.agh.tai.repository.UserRepository;
import pl.edu.agh.tai.util.UserException;

import java.util.Collection;
import java.util.Iterator;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean addUser(LoginInfo info) throws UserException {
        if(info.getRole().equalsIgnoreCase("user")){
            User user = new User(info.getUsername(), new BCryptPasswordEncoder().encode(info.getPassword()), "ROLE_" + info.getRole().toUpperCase());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public String getRole() throws UserException {

        Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator<? extends GrantedAuthority> it = roles.iterator();

        String role = "";
        // this is simplistic because we have only one role per user right now
        if(it.hasNext()){
           role = it.next().getAuthority(); // only 1 role
        }

        return role;
    }

    @Override
    public User getUserByUsername(String username) throws UserException {
        return userRepository.findByNickname(username);
    }
}
