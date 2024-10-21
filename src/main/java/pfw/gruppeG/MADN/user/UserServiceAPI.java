package pfw.gruppeG.MADN.user;


import pfw.gruppeG.MADN.user.exception.UserException;

import java.util.Map;

/**
 * UserServiceAPI
 *
 * @author Jannes Bierma,Dalila Rustemovic
 * @version 1.0 - 20.10.2024
 */
public interface UserServiceAPI {

    Map<String,String> create(String username, String password, String email, String role)
            throws UserException;

    Map<String,String> getUser(Long id) throws UserException;

    Map<String,String> getUser(String username) throws UserException;

    void update(Long id, String username, String password, String email, String role)
            throws UserException;

    void delete(Long id) throws UserException;
}
