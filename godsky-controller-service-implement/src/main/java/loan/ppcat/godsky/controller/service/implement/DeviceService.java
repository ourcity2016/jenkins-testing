package loan.ppcat.godsky.controller.service.implement;

import com.pi4j.io.gpio.*;
import loan.ppcat.godsky.controller.dao.api.IDeviceDao;
import loan.ppcat.godsky.controller.service.api.IDeviceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("deviceService")
public class DeviceService implements IDeviceService {
    @Inject
    private IDeviceDao deviceDao;

    @Override
    @Deprecated
    public synchronized void bindDevice(Pin pin, String name, Pin devicePin, boolean pwm, long rate, String deviceName) throws Exception {
        deviceDao.bindDevice(pin, name, devicePin, pwm, rate, deviceName);
    }

    @Override
    public synchronized void bindDevice(GpioPinDigitalInput gpioPinDigitalInput, GpioPinDigitalOutput gpioPinDigitalOutputed) throws Exception {
        deviceDao.bindDevice(gpioPinDigitalInput, gpioPinDigitalOutputed);
    }

    @Override
    public synchronized void bindPWMDevice(GpioPinDigitalInput gpioPinDigitalInput, GpioPinPwmOutput gpioPinPwmOutput, long rate) throws Exception {
        deviceDao.bindPWMDevice(gpioPinDigitalInput, gpioPinPwmOutput, rate);
    }

    @Override
    @Deprecated
    public synchronized void setupDigital(Pin pin, String name, PinMode pinMode) throws Exception {
        deviceDao.setupDigital(pin, name, pinMode);
    }

    @Override
    public synchronized void setupDigital(GpioPinDigitalInput gpioPinDigitalInput, PinMode pinMode) throws Exception {
        deviceDao.setupDigital(gpioPinDigitalInput, pinMode);
    }

    @Override
    public synchronized void setupDigital(GpioPinPwmOutput gpioPinPwmOutput, PinMode pinMode) throws Exception {
        deviceDao.setupDigital(gpioPinPwmOutput, pinMode);
    }

    @Override
    @Deprecated
    public synchronized PinState checkDigitalStatus(Pin pin, String name) throws Exception {
        return deviceDao.checkDigitalStatus(pin, name);
    }

    @Override
    public synchronized PinState checkDigitalStatus(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception {
        return deviceDao.checkDigitalStatus(gpioPinDigitalOutput);
    }

    @Override
    @Deprecated
    public synchronized int checkPwmDigitalStatus(Pin pin, String name) throws Exception {
        return deviceDao.checkPwmDigitalStatus(pin, name);
    }

    @Override
    public int checkPwmDigitalStatus(GpioPinPwmOutput gpioPinPwmOutput) throws Exception {
        return deviceDao.checkPwmDigitalStatus(gpioPinPwmOutput);
    }

    @Override
    @Deprecated
    public synchronized void openNormalDigital(Pin pin, String name) throws Exception {
        deviceDao.openNormalDigital(pin, name);
    }

    @Override
    public synchronized void openNormalDigital(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception {
        deviceDao.openNormalDigital(gpioPinDigitalOutput);
    }

    @Override
    @Deprecated
    public synchronized void closeNormalDigital(Pin pin, String name) throws Exception {
        deviceDao.closeNormalDigital(pin, name);
    }

    @Override
    public synchronized void closeNormalDigital(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception {
        deviceDao.closeNormalDigital(gpioPinDigitalOutput);
    }

    @Override
    @Deprecated
    public synchronized void toggleNormalDigital(Pin pin, String name) throws Exception {
        deviceDao.toggleNormalDigital(pin, name);
    }

    @Override
    public synchronized void toggleNormalDigital(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception {
        deviceDao.toggleNormalDigital(gpioPinDigitalOutput);
    }

    @Override
    @Deprecated
    public synchronized void breathInDigital(Pin pin, long frequency, String name) throws Exception {
        deviceDao.breathInDigital(pin, frequency, name);
    }

    @Override
    public synchronized void breathInDigital(GpioPinPwmOutput gpioPinPwmOutput, long frequency) throws Exception {
        deviceDao.breathInDigital(gpioPinPwmOutput, frequency);
    }

    @Override
    @Deprecated
    public synchronized void breathOutcloseDigital(Pin pin, long frequency, String name) throws Exception {
        deviceDao.breathOutcloseDigital(pin, frequency, name);
    }

    @Override
    public void breathOutcloseDigital(GpioPinPwmOutput gpioPinPwmOutput, long frequency) throws Exception {
        deviceDao.breathOutcloseDigital(gpioPinPwmOutput, frequency);
    }

    @Override
    @Deprecated
    public synchronized void togglebreathDigital(Pin pin, long frequency, String name) throws Exception {
        deviceDao.togglebreathDigital(pin, frequency, name);
    }

    @Override
    public synchronized void togglebreathDigital(GpioPinPwmOutput gpioPinPwmOutput, long frequency) throws Exception {
        deviceDao.togglebreathDigital(gpioPinPwmOutput, frequency);
    }

    @Override
    @Deprecated
    public synchronized void pointForDigital(Pin pin, long lightness, String name) throws Exception {
        deviceDao.pointForDigital(pin, lightness, name);
    }

    @Override
    public synchronized void pointForDigital(GpioPinPwmOutput gpioPinPwmOutput, long lightness) throws Exception {
        deviceDao.pointForDigital(gpioPinPwmOutput, lightness);
    }

    @Override
    @Deprecated
    public synchronized void pointToPointForDigital(Pin pin, long lightnessstart, long lightnessend, long rate, String name) throws Exception {
        deviceDao.pointToPointForDigital(pin, lightnessstart, lightnessend, rate, name);
    }

    @Override
    public synchronized void pointToPointForDigital(GpioPinPwmOutput gpioPinPwmOutputpoint, long lightnessstart, long lightnessend, long rate) throws Exception {
        deviceDao.pointToPointForDigital(gpioPinPwmOutputpoint, lightnessstart, lightnessend, rate);
    }
}
