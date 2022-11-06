package main;

import main.model.Car;
import main.model.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CarsController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/cars/{id}")
    public ResponseEntity get(@PathVariable("id") int carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        return optionalCar.map(car -> new ResponseEntity(car, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(optionalCar.get(), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/cars/")
    public List<Car> list() {
        Iterable<Car> carIterable = carRepository.findAll(); // возвращает объект iterable
        List<Car> cars = new ArrayList<>();
        for (Car car : carIterable) {
            cars.add(car);
        }
        return cars;
    }

    @PostMapping("/cars/")
    public int add(Car car) {
        Car newCar = carRepository.save(car);
        return newCar.getId();
    }


    @PutMapping("cars/{id}")
    public Car updateCar(Car carDetails, @PathVariable("carId") int carId) {
        Storage.updateCar(carDetails, carId);
        return carRepository.save(carDetails);
    }

    @DeleteMapping("/cars/{id}")
    public HttpStatus delete(@PathVariable("id") int carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        carRepository.delete(optionalCar.get());
        Car car = Storage.deleteCar(carId);
        if (car != null) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @DeleteMapping("/cars/")
    public HttpStatus deleteAll() {
        Storage.deleteAllCar();
        carRepository.deleteAll();
        if (Storage.getAllCar().isEmpty()) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}