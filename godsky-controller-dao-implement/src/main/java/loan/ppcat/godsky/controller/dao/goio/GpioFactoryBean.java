package loan.ppcat.godsky.controller.dao.goio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("gpioFactoryBean")
public class GpioFactoryBean {

    public GpioController gpioFactory() {
        return GpioFactory.getInstance();
    }
}
