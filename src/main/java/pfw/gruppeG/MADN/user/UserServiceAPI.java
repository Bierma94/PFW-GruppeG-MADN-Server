package pfw.gruppeG.MADN.user;


import pfw.gruppeG.MADN.user.exception.UserException;

import java.util.Map;

/**
 * UserServiceAPI
 * Interface for the UserModule
 *
 * @author Jannes Bierma,Dalila Rustemovic
 * @version 1.0 - 20.10.2024
 */
public interface UserServiceAPI {
    /**
     * Creates a new user
     * @param username username of the user
     * @param password password of the user
     * @param email email of the user
     * @param role role of the user
     * @return Map<String, String> user
     * @throws UserException if user already exists
     */
    Map<String,String> create(String username, String password, String email, String role)
            throws UserException;

    /**
     * Gets a user by id
     * @param id id of the user
     * @return Map<String, String> user
     * @throws UserException if user not found
     */
    Map<String,String> getUser(Long id) throws UserException;

    /**
     * Gets a user by username
     * @param username username of the user
     * @return Map<String, String> user
     * @throws UserException if user not found
     */
    Map<String,String> getUser(String username) throws UserException;

    /**
     * Updates a user
     * @param id id of the user
     * @param username username of the user
     * @param password password of the user
     * @param email email of the user
     * @param role role of the user
     * @throws UserException exception
     */
    void update(Long id, String username, String password, String email, String role)
            throws UserException;

    /**
     * Deletes a user
     * @param id id of the user
     * @throws UserException if user not found
     */
    void delete(Long id) throws UserException;
}
