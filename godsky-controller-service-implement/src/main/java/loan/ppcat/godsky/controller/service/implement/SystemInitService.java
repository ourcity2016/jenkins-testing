package loan.ppcat.godsky.controller.service.implement;

import com.pi4j.io.gpio.*;
import loan.ppcat.godsky.controller.dao.goio.GpioFactoryBean;
import loan.ppcat.godsky.controller.model.Controller;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Service("systemInitService")
public class SystemInitService {
    @Inject
    private GpioFactoryBean gpioFactoryBean;
    private JSONObject jsonObject;
    private Map<String, GpioPin> serviceList = new HashMap<>();
    private Controller controller;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public GpioPin initSystemService(Pin pin, String name, boolean pwm, String type) {
        GpioController gpioController = gpioFactoryBean.gpioFactory();

        if (type.equals("LED") || type.equals("FAN") || type.equals("TV") || type.equals("WATER") || type.equals("AIRCONDITIONER")) {
            if (pwm) {
                GpioPinPwmOutput gpioPinPwmOutput = gpioController.provisionPwmOutputPin(pin, name, 0);
                gpioPinPwmOutput.setShutdownOptions(true, PinState.LOW);
                serviceList.put(name, gpioPinPwmOutput);
                return gpioPinPwmOutput;
            } else {
                GpioPinDigitalOutput gpioPinDigitalOutput = gpioController.provisionDigitalOutputPin(pin, name, PinState.LOW);
                gpioPinDigitalOutput.setShutdownOptions(true, PinState.LOW);
                serviceList.put(name, gpioPinDigitalOutput);
                return gpioPinDigitalOutput;
            }
        } else if (type.equals("SWITCH")) {
            GpioPinDigitalInput gpioPinDigitalInput = gpioController.provisionDigitalInputPin(pin, name);
            serviceList.put(name, gpioPinDigitalInput);
            return gpioPinDigitalInput;
        } else {
            System.out.println("not surrport");
            return null;
        }


    }

    public GpioPinPwmOutput getGpioPinPwmOutputByName(String name) {
        return (GpioPinPwmOutput) serviceList.get(name);
    }

    public GpioPinDigitalInput getGpioPinDigitalInputByName(String name) {
        return (GpioPinDigitalInput) serviceList.get(name);
    }

    public GpioPinDigitalOutput getGpioPinDigitalOutputByName(String name) {
        return (GpioPinDigitalOutput) serviceList.get(name);
    }

    public Map<String, GpioPin> getServiceList() {
        return serviceList;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
