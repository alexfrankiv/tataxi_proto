package app.dao;

import app.entity.Car;
import app.entity.Worker;

import java.util.List;

/**
 * Created by Alex_Frankiv on 19.03.2017.
 */
public interface CarDao {

    Car get(int id);
    int insert(Car car);
    void update(Car car);
    void remove(Car car);
    List<Car> getFreeCarsByDispatcher(Worker dispatcher) throws Exception;
}
