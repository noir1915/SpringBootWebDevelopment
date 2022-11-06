package main;

import main.model.Car;
import main.model.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class DefaultController {

    @Autowired
    private CarRepository carRepository;

    @Value("${someParameter}")
    private Integer someParameter;

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<Car> iterable = carRepository.findAll();
        ArrayList<Car> cars = new ArrayList<>();
        for (Car car : iterable) {
            cars.add(car);
        }
        model.addAttribute("cars", cars);
        model.addAttribute("carsCount", cars.size());
        model.addAttribute("someParameter", someParameter);
        return "index";
    }

}
