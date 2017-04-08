package app.service;

import app.entity.Car;
import app.entity.Worker;
import app.pojo.CarDriver;

import java.util.List;

/**
 * Created by Alex_Frankiv on 08.04.2017.
 */
public interface CarDriverService {

    List<CarDriver> getCarDriversForDispatcher(Worker worker);
    int createCarDriverPair(Car car, Worker driver, Worker dispatcher);
    int cancelCarDriverPair(Car car, Worker driver, Worker dispatcher);
}
