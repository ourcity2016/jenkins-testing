package loan.ppcat.godsky.controller.dao.api;

import com.pi4j.io.gpio.*;

public interface IDeviceDao {
    public void bindDevice(Pin pin, String name, Pin devicePin, boolean pwm, long rate, String deviceName) throws Exception;

    public void bindDevice(GpioPinDigitalInput gpioPinDigitalInput, GpioPinDigitalOutput gpioPinDigitalOutputed) throws Exception;

    public void bindPWMDevice(GpioPinDigitalInput gpioPinDigitalInput, GpioPinPwmOutput gpioPinPwmOutput, long rate) throws Exception;

    public void setupDigital(Pin pin, String name, PinMode pinMode) throws Exception;

    public void setupDigital(GpioPinDigitalInput gpioPinDigitalInput, PinMode pinMode) throws Exception;

    public void setupDigital(GpioPinPwmOutput gpioPinPwmOutput, PinMode pinMode) throws Exception;

    public PinState checkDigitalStatus(Pin pin, String name) throws Exception;

    public PinState checkDigitalStatus(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception;

    public int checkPwmDigitalStatus(Pin pin, String name) throws Exception;

    public int checkPwmDigitalStatus(GpioPinPwmOutput gpioPinPwmOutput) throws Exception;

    public void openNormalDigital(Pin pin, String name) throws Exception;

    public void openNormalDigital(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception;

    public void closeNormalDigital(Pin pin, String name) throws Exception;

    public void closeNormalDigital(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception;

    public void toggleNormalDigital(Pin pin, String name) throws Exception;

    public void toggleNormalDigital(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception;

    public void breathInDigital(Pin pin, long frequency, String name) throws Exception;

    public void breathInDigital(GpioPinPwmOutput gpioPinPwmOutput, long frequency) throws Exception;

    public void breathOutcloseDigital(Pin pin, long frequency, String name) throws Exception;

    public void breathOutcloseDigital(GpioPinPwmOutput gpioPinPwmOutput, long frequency) throws Exception;

    public void togglebreathDigital(Pin pin, long frequency, String name) throws Exception;

    public void togglebreathDigital(GpioPinPwmOutput gpioPinPwmOutput, long frequency) throws Exception;

    public void pointForDigital(Pin pin, long lightness, String name) throws Exception;

    public void pointForDigital(GpioPinPwmOutput gpioPinPwmOutput, long lightness) throws Exception;

    public void pointToPointForDigital(Pin pin, long lightnessstart, long lightnessend, long rate, String name) throws Exception;

    public void pointToPointForDigital(GpioPinPwmOutput gpioPinPwmOutputpoint, long lightnessstart, long lightnessend, long rate) throws Exception;
}
