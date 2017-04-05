package app.dao.impl;

import app.dao.OrderDao;
import app.entity.Order;
import app.pojo.Place;
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
//@Cacheable("orders")
public class OrderDaoImpl implements OrderDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String GET = "SELECT * FROM \"order\" WHERE id=?";
    private static final String INSERT = "INSERT INTO \"order\" (from_lat, from_lng, to_lat, to_lng, distance, price, " +
            "is_fast, start_time, finish_time, feedback, car_id, worker_login, client_login, city, from_address, to_address, seats, extraluggage)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE \"order\" SET from_lat=?, from_lng=?, to_lat=?, to_lng=?, distance=?," +
            "price=?, is_fast=?, start_time=?, finish_time=?, feedback=?, car_id=?, worker_login=?, client_login=?, " +
            "city=?, from_address=?, to_address=?, seats=?, extraluggage=? WHERE id=?";
    private static final String DELETE = "DELETE FROM order WHERE id=?";

    private static Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class.getSimpleName());

    public Order get(int id){
        logger.info("DAO: grabbing object Order from DB");
        return jdbcTemplate.queryForObject(GET, mapper, id);
    }

    public int insert(Order order) {
        logger.info("DAO: inserting object Order into DB");
        return jdbcTemplate.update(INSERT, order.getFrom().getLat(), order.getFrom().getLng(),
                order.getTo().getLat(), order.getTo().getLng(), order.getDistance(), order.getPrice(), order.isFast(),
                order.getStartTime(), order.getFinishTime(), order.getFeedback(), (order.getCar()>=0) ? order.getCar():null, order.getDispatcher(),
                order.getClient(), order.getCity(), order.getFrom().getAddress(), order.getTo().getAddress(),order.getSeats(), order.isExtraLuggage());
    }

    public void update(Order order) {
        logger.info("DAO: updating object Order in DB");
        jdbcTemplate.update(UPDATE, order.getFrom().getLat(), order.getFrom().getLng(),
                order.getTo().getLat(), order.getTo().getLng(), order.getDistance(), order.getPrice(), order.isFast(),
                order.getStartTime(), order.getFinishTime(), order.getFeedback(), (order.getCar()>=0) ? order.getCar():null, order.getDispatcher(),
                order.getClient(), order.getCity(), order.getFrom().getAddress(), order.getTo().getAddress(), order.getSeats(),
                order.isExtraLuggage(), order.getId());
    }

    public void remove(Order order) {
        logger.info("DAO: removing object Order from DB");
        jdbcTemplate.update(DELETE, order.getId());
    }

    private RowMapper<Order> mapper = new RowMapper<Order>() {
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            Place from = new Place();
            from.setLat(rs.getDouble("from_lat"));
            from.setLng(rs.getDouble("from_lng"));
            from.setAddress(rs.getString("from_address"));
            order.setFrom(from);
            Place to = new Place();
            to.setLat(rs.getDouble("to_lat"));
            to.setLng(rs.getDouble("to_lng"));
            to.setAddress(rs.getString("to_address"));
            order.setTo(to);
            order.setDistance(rs.getDouble("distance"));
            order.setPrice(rs.getDouble("price"));
            order.setFast(rs.getBoolean("is_fast"));
            order.setStartTime(rs.getTimestamp("start_time"));
            order.setFinishTime(rs.getTimestamp("finish_time"));
            order.setFeedback(rs.getInt("feedback"));
            order.setCar(rs.getInt("car_id"));
            order.setDispatcher(rs.getString("worker_login"));
            order.setClient(rs.getString("client_login"));
            order.setCity(rs.getString("city"));
            return order;
        }
    };
}