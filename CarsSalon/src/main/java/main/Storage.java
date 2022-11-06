package main;

import main.model.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Storage {

    private static final ConcurrentHashMap<Integer, Car> cars = new ConcurrentHashMap<>();

    public static List<Car> getAllCar() {
        List<Car> carArrayList = Collections.synchronizedList(new ArrayList<Car>());
        carArrayList.addAll(cars.values());
        return carArrayList;
    }

    public static int addCar(Car car) {
        int id = cars.size() + 1;
        car.setId(id);
        cars.put(id, car);
        return id;
    }

    public static Car getCar(int carId) {
        if (cars.containsKey(carId)) {
            return cars.get(carId);
        }
        return null;
    }

    public static void updateCar(Car car, int carId) {
        ArrayList<Car> carArrayList = new ArrayList<Car>();
        carArrayList.stream().map(c -> {
            if (c.getId() == carId) {
                c.setName(car.getName());
                c.setYear(car.getYear());
            }
            return c;
        }).collect(Collectors.toList());
    }

    public static Car deleteCar(int carId) {
        if (cars.containsKey(carId)) {
            cars.remove(carId, cars.get(carId));
            return cars.get(carId);
        }
        return null;
    }

    public static ConcurrentHashMap<Car, Integer> deleteAllCar() {
        cars.clear();
        return new ConcurrentHashMap<>();
    }
}