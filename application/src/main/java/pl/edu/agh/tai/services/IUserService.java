package pl.edu.agh.tai.services;

import pl.edu.agh.tai.model.LoginInfo;
import pl.edu.agh.tai.model.User;
import pl.edu.agh.tai.util.UserException;

public interface IUserService {

    String getRole() throws UserException;
    boolean addUser(LoginInfo info) throws UserException;
    User getUserByUsername(String username) throws UserException;

}
