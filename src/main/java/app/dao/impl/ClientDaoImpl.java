package app.dao.impl;

import app.dao.ClientDao;
import app.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alex_Frankiv on 19.03.2017.
 */
@Repository
@Cacheable("clients")
public class ClientDaoImpl implements ClientDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static Logger logger = LoggerFactory.getLogger(ClientDaoImpl.class.getSimpleName());

    private static final String GET = "SELECT * FROM client WHERE login=?";
    private static final String INSERT = "INSERT INTO client (login, real_name, email, home_address, " +
            "phone_num) VALUES (?,?,?,?,?)";
    private static final String UPDATE = "UPDATE client SET real_name=?, email=?, home_address=?," +
            "phone_num=? WHERE login=?";
    private static final String DELETE = "DELETE FROM client WHERE login=?";

    public Client get(String login) {
        logger.info("DAO: grabbing object Client from DB");
        return jdbcTemplate.queryForObject(GET, mapper, login);
    }

    public int insert(Client client) {
        logger.info("DAO: inserting object Client into DB");
        return jdbcTemplate.update(INSERT, client.getLogin(), client.getRealName(), client.getHomeAddress(), client.getPhoneNumber());
    }

    public void update(Client client) {
        logger.info("DAO: updating object Client in DB");
        jdbcTemplate.update(UPDATE, client.getRealName(), client.getHomeAddress(), client.getPhoneNumber(), client.getLogin());
    }

    public void remove(Client client) {
        logger.info("DAO: removing object Client from DB");
        jdbcTemplate.update(DELETE, client.getLogin());
    }

    private RowMapper<Client> mapper = new RowMapper<Client>() {
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client client = new Client();
            client.setLogin(rs.getString("login"));
            client.setRealName(rs.getString("first_name"));
            client.setEmail(rs.getString("email"));
            client.setHomeAddress(rs.getString("home_address"));
            client.setPhoneNumber(rs.getString("phone_num"));
            return client;
        }
    };
}
