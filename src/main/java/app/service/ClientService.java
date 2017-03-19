package app.service;

import app.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Alex_Frankiv on 19.03.2017.
 */
public interface ClientService {

    Client get(String login);
    Client insert(Client client);
    void update(Client client);
    void remove(Client client);
}
