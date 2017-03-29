package app.service;

import app.entity.User;

/**
 * Created by Alex_Frankiv on 19.03.2017.
 */
public interface UserService {
    User get(String login);
    int insert(User user);
    void update(User user);
    void remove(User user);
    boolean ifExists(User user);
}
