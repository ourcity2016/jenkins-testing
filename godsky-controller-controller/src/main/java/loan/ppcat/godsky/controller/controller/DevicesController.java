package loan.ppcat.godsky.controller.controller;

import com.pi4j.io.gpio.RaspiPin;
import loan.ppcat.godsky.controller.model.Device;
import loan.ppcat.godsky.controller.service.api.IDeviceService;
import loan.ppcat.godsky.controller.service.implement.SystemInitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
@RequestMapping("/devices")
public class DevicesController extends RaspiPin {
    @Inject
    private SystemInitService systemInitService;
    @Inject
    private IDeviceService deviceService;

    @RequestMapping("/list")
    @ResponseBody
    public String getDevices() {
        return systemInitService.getJsonObject().toString();
    }

    @RequestMapping("/open")
    @ResponseBody
    public void open(@ModelAttribute Device device) throws Exception {
        if (device.isPwm()) {
            //deviceService.breathInDigital(createDigitalAndPwmPin(Integer.parseInt(device.getFooter()), device.getName()), 1, device.getName());
            deviceService.breathInDigital(systemInitService.getGpioPinPwmOutputByName(device.getName()), 1);
        } else {
            // deviceService.openNormalDigital(createDigitalPin(Integer.parseInt(device.getFooter()), device.getName()), device.getName());
            deviceService.openNormalDigital(systemInitService.getGpioPinDigitalOutputByName(device.getName()));
        }

    }

    @RequestMapping("/close")
    @ResponseBody
    public void close(@ModelAttribute Device device) throws Exception {
        if (device.isPwm()) {
            //deviceService.breathOutcloseDigital(createDigitalAndPwmPin(Integer.parseInt(device.getFooter()), device.getName()), 1, device.getName());
            deviceService.breathOutcloseDigital(systemInitService.getGpioPinPwmOutputByName(device.getName()), 1);
        } else {
            // deviceService.closeNormalDigital(createDigitalPin(Integer.parseInt(device.getFooter()), device.getName()), device.getName());
            deviceService.closeNormalDigital(systemInitService.getGpioPinDigitalOutputByName(device.getName()));
        }

    }

    @RequestMapping("/toggle")
    @ResponseBody
    public void toggleDevice(@ModelAttribute Device device) throws Exception {
        if (device.isPwm()) {
            // deviceService.togglebreathDigital(createDigitalAndPwmPin(Integer.parseInt(device.getFooter()), device.getName()), 1, device.getName());
            deviceService.togglebreathDigital(systemInitService.getGpioPinPwmOutputByName(device.getName()), 1);
        } else {
            //deviceService.toggleNormalDigital(createDigitalPin(Integer.parseInt(device.getFooter()), device.getName()), device.getName());
            deviceService.toggleNormalDigital(systemInitService.getGpioPinDigitalOutputByName(device.getName()));
        }
    }


    @RequestMapping("/point")
    @ResponseBody
    public void point(@ModelAttribute Device device, int lightness) throws Exception {
        if (device.isPwm()) {
            //deviceService.pointForDigital(createDigitalAndPwmPin(Integer.parseInt(device.getFooter()), device.getName()), lightness, device.getName());
            deviceService.pointForDigital(systemInitService.getGpioPinPwmOutputByName(device.getName()), lightness);
        } else {
            throw new Exception("Device not be surported!");
        }
    }

    @RequestMapping("/pointToPoint")
    @ResponseBody
    public void point(@ModelAttribute Device device, int lightnessstart, int lightnessend) throws Exception {
        if (device.isPwm()) {
            //deviceService.pointToPointForDigital(createDigitalAndPwmPin(Integer.parseInt(device.getFooter()), device.getName()), lightnessstart, lightnessend, 1, device.getName());
            deviceService.pointToPointForDigital(systemInitService.getGpioPinPwmOutputByName(device.getName()), lightnessstart, lightnessend, 1);
        } else {
            throw new Exception("Device not be surported!");
        }
    }
}
