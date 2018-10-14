package loan.ppcat.godsky.controller.dao.implement;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import loan.ppcat.godsky.controller.dao.api.IDeviceDao;
import loan.ppcat.godsky.controller.dao.goio.GpioFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository("deviceDao")
public class DeviceDao implements IDeviceDao {
    private Logger logger = Logger.getLogger(DeviceDao.class);
    @Inject
    private GpioFactoryBean gpioFactoryBean;

    @Override
    public synchronized void bindDevice(Pin pin, String name, Pin devicePin, boolean pwm, long rate, String deviceName) throws Exception {
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinDigitalInput gpioPinDigitalInput = gpioController.provisionDigitalInputPin(pin, name);//PinPullResistance.PULL_DOWN);
            gpioPinDigitalInput.setShutdownOptions(true);
            gpioPinDigitalInput.addListener(new GpioPinListenerDigital() {
                private long millSeconds = 0;

                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (((currentTimeMillis - millSeconds) > 1000) && event.getState().getValue() == 1) {
                        millSeconds = System.currentTimeMillis();
                        if (pwm) {
                            try {
                                togglebreathDigital(devicePin, rate, deviceName);
                            } catch (Exception e) {
                                logger.error("toggle device error e = {}", e);
                            }
                        } else {
                            try {
                                toggleNormalDigital(devicePin, deviceName);
                            } catch (Exception e) {
                                logger.error("toggle device error e = {}", e);
                            }
                        }

                    }
                }

            });
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void bindDevice(GpioPinDigitalInput gpioPinDigitalInput, GpioPinDigitalOutput gpioPinDigitalOutputed) throws Exception {
        try {
            gpioPinDigitalInput.addListener(new GpioPinListenerDigital() {
                private long millSeconds = 0;

                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (((currentTimeMillis - millSeconds) > 1000) && event.getState().getValue() == 1) {
                        millSeconds = System.currentTimeMillis();
                        try {
                            toggleNormalDigital(gpioPinDigitalOutputed);
                        } catch (Exception e) {
                            logger.error("toggle device error e = {}", e);
                        }

                    }
                }

            });
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void bindPWMDevice(GpioPinDigitalInput gpioPinDigitalInput, GpioPinPwmOutput gpioPinPwmOutput, long rate) throws Exception {
        try {
            gpioPinDigitalInput.addListener(new GpioPinListenerDigital() {
                private long millSeconds = 0;

                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (((currentTimeMillis - millSeconds) > 1000) && event.getState().getValue() == 1) {
                        millSeconds = System.currentTimeMillis();
                        try {
                            togglebreathDigital(gpioPinPwmOutput, rate);
                        } catch (Exception e) {
                            logger.error("toggle device error e = {}", e);
                        }

                    }
                }

            });
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void setupDigital(Pin pin, String name, PinMode pinMode) throws Exception {
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinDigitalInput gpioPinDigitalInput = gpioController.provisionDigitalInputPin(pin, name);
            gpioPinDigitalInput.setMode(pinMode);
            gpioController.unprovisionPin(gpioPinDigitalInput);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void setupDigital(GpioPinDigitalInput gpioPinDigitalInput, PinMode pinMode) throws Exception {
        try {
            gpioPinDigitalInput.setMode(pinMode);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void setupDigital(GpioPinPwmOutput gpioPinPwmOutput, PinMode pinMode) throws Exception {
        try {
            gpioPinPwmOutput.setMode(pinMode);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized PinState checkDigitalStatus(Pin pin, String name) throws Exception {
        PinState pinState = null;
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinDigitalOutput gpioPinDigitalOutput = gpioController.provisionDigitalOutputPin(pin, name);
            pinState = gpioPinDigitalOutput.getState();
            gpioController.unprovisionPin(gpioPinDigitalOutput);
        } catch (Exception e) {
            throw e;
        }
        return pinState;
    }

    @Override
    public synchronized PinState checkDigitalStatus(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception {
        PinState pinState = null;
        try {
            pinState = gpioPinDigitalOutput.getState();
        } catch (Exception e) {
            throw e;
        }
        return pinState;
    }


    @Override
    public synchronized int checkPwmDigitalStatus(Pin pin, String name) throws Exception {
        int value = -999;
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinPwmOutput gpioPinPwmOutput = gpioController.provisionPwmOutputPin(pin, name);
            value = gpioPinPwmOutput.getPwm();
            gpioController.unprovisionPin(gpioPinPwmOutput);
        } catch (Exception e) {
            throw e;
        }
        return value;
    }

    @Override
    public int checkPwmDigitalStatus(GpioPinPwmOutput gpioPinPwmOutput) throws Exception {
        int value = -999;
        try {
            value = gpioPinPwmOutput.getPwm();
        } catch (Exception e) {
            throw e;
        }
        return value;
    }

    @Override
    public synchronized void openNormalDigital(Pin pin, String name) throws Exception {
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinDigitalOutput gpioPinDigitalOutput = gpioController.provisionDigitalOutputPin(pin, name);
            gpioPinDigitalOutput.high();
            gpioController.unprovisionPin(gpioPinDigitalOutput);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void openNormalDigital(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception {
        try {
            gpioPinDigitalOutput.high();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void closeNormalDigital(Pin pin, String name) throws Exception {
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinDigitalOutput gpioPinDigitalOutput = gpioController.provisionDigitalOutputPin(pin, name);
            gpioPinDigitalOutput.low();
            gpioController.unprovisionPin(gpioPinDigitalOutput);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void closeNormalDigital(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception {
        try {
            gpioPinDigitalOutput.low();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void toggleNormalDigital(Pin pin, String name) throws Exception {
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinDigitalOutput gpioPinDigitalOutput = gpioController.provisionDigitalOutputPin(pin, name);
            gpioPinDigitalOutput.toggle();
            gpioController.unprovisionPin(gpioPinDigitalOutput);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void toggleNormalDigital(GpioPinDigitalOutput gpioPinDigitalOutput) throws Exception {
        try {
            gpioPinDigitalOutput.toggle();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void breathInDigital(Pin pin, long frequency, String name) throws Exception {
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinPwmOutput gpioPinPwmOutput = gpioController.provisionPwmOutputPin(pin, name);
            for (int i = 0; i <= 1023; i++) {
                gpioPinPwmOutput.setPwm(i);
                Thread.sleep(frequency);
            }
            gpioController.unprovisionPin(gpioPinPwmOutput);
        } catch (InterruptedException e) {
            throw e;
        }
    }

    @Override
    public synchronized void breathInDigital(GpioPinPwmOutput gpioPinPwmOutput, long frequency) throws Exception {
        try {
            for (int i = 0; i <= 1023; i++) {
                gpioPinPwmOutput.setPwm(i);
                Thread.sleep(frequency);
            }
        } catch (InterruptedException e) {
            throw e;
        }
    }

    @Override
    public synchronized void breathOutcloseDigital(Pin pin, long frequency, String name) throws Exception {
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinPwmOutput gpioPinPwmOutput = gpioController.provisionPwmOutputPin(pin, name);
            for (int i = 1023; i >= 0; i--) {
                gpioPinPwmOutput.setPwm(i);
                Thread.sleep(frequency);
            }
            gpioController.unprovisionPin(gpioPinPwmOutput);
        } catch (InterruptedException e) {
            throw e;
        }
    }

    @Override
    public synchronized void breathOutcloseDigital(GpioPinPwmOutput gpioPinPwmOutput, long frequency) throws Exception {
        try {
            for (int i = 1023; i >= 0; i--) {
                gpioPinPwmOutput.setPwm(i);
                Thread.sleep(frequency);
            }
        } catch (InterruptedException e) {
            throw e;
        }
    }

    @Override
    public synchronized void togglebreathDigital(Pin pin, long frequency, String name) throws Exception {
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinPwmOutput gpioPinPwmOutput = gpioController.provisionPwmOutputPin(pin, name);
            int pwmValue = gpioPinPwmOutput.getPwm();
            if (pwmValue < 512) {
                for (int i = pwmValue; i <= 1023; i++) {
                    gpioPinPwmOutput.setPwm(i);
                    Thread.sleep(frequency);
                }
            } else {
                for (int i = pwmValue; i >= 0; i--) {
                    gpioPinPwmOutput.setPwm(i);
                    Thread.sleep(frequency);
                }
            }
            gpioController.unprovisionPin(gpioPinPwmOutput);
        } catch (InterruptedException e) {
            throw e;
        }
    }

    @Override
    public synchronized void togglebreathDigital(GpioPinPwmOutput gpioPinPwmOutput, long frequency) throws Exception {
        try {
            int pwmValue = gpioPinPwmOutput.getPwm();
            if (pwmValue < 500) {
                for (int i = pwmValue; i <= 1023; i++) {
                    gpioPinPwmOutput.setPwm(i);
                    Thread.sleep(frequency);
                }
            } else {
                for (int i = pwmValue; i >= 0; i--) {
                    gpioPinPwmOutput.setPwm(i);
                    Thread.sleep(frequency);
                }
            }
        } catch (InterruptedException e) {
            throw e;
        }
    }

    @Override
    public synchronized void pointForDigital(Pin pin, long lightness, String name) throws Exception {
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinPwmOutput gpioPinPwmOutput = gpioController.provisionPwmOutputPin(pin, name);
            gpioPinPwmOutput.setPwm((int) lightness);
            gpioController.unprovisionPin(gpioPinPwmOutput);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void pointForDigital(GpioPinPwmOutput gpioPinPwmOutput, long lightness) throws Exception {
        try {
            gpioPinPwmOutput.setPwm((int) lightness);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public synchronized void pointToPointForDigital(Pin pin, long lightnessstart, long lightnessend, long rate, String name) throws Exception {
        try {
            GpioController gpioController = gpioFactoryBean.gpioFactory();
            GpioPinPwmOutput gpioPinPwmOutput = gpioController.provisionPwmOutputPin(pin, name);
            if (lightnessstart < lightnessend) {
                for (int i = (int) lightnessstart; i <= lightnessend; i++) {
                    gpioPinPwmOutput.setPwm(i);
                    Thread.sleep(rate);
                }
            } else {
                for (int i = (int) lightnessend; i >= lightnessstart; i--) {
                    gpioPinPwmOutput.setPwm(i);
                    Thread.sleep(rate);
                }
            }
            gpioController.unprovisionPin(gpioPinPwmOutput);
        } catch (InterruptedException e) {
            throw e;
        }
    }

    @Override
    public synchronized void pointToPointForDigital(GpioPinPwmOutput gpioPinPwmOutputpoint, long lightnessstart, long lightnessend, long rate) throws Exception {
        try {
            if (lightnessstart < lightnessend) {
                for (long i = lightnessstart; i <= lightnessend; i++) {
                    gpioPinPwmOutputpoint.setPwm((int) i);
                    Thread.sleep(rate);
                }
            } else {
                for (long i = lightnessend; i >= lightnessstart; i--) {
                    gpioPinPwmOutputpoint.setPwm((int) i);
                    Thread.sleep(rate);
                }
            }
        } catch (InterruptedException e) {
            throw e;
        }
    }
}
