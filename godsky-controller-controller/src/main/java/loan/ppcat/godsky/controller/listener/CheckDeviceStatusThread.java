package loan.ppcat.godsky.controller.listener;

import com.pi4j.io.gpio.*;
import loan.ppcat.godsky.controller.model.Controller;
import loan.ppcat.godsky.controller.model.Light;
import loan.ppcat.godsky.controller.service.api.IDeviceService;
import loan.ppcat.godsky.controller.util.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class CheckDeviceStatusThread extends RaspiPin implements Runnable {
    private static Map<Integer, Controller> controllerMap = new HashMap<>();
    private IDeviceService deviceService;
    private Controller controller;
    private Logger logger = Logger.getLogger(CheckDeviceStatusThread.class);
    private Map<String, GpioPin> serviceList;
    public static int isChangedStatus = 0;

    public static int isIsChangedStatus() {
        return isChangedStatus;
    }

    public static Map<Integer, Controller> getControllerMap() {
        return controllerMap;
    }

    public CheckDeviceStatusThread(IDeviceService _deviceService, Map<String, GpioPin> _serviceList, Controller _controller) {
        deviceService = _deviceService;
        controller = _controller;
        serviceList = _serviceList;
    }

    @Override
    public void run() {
        logger.warn("Check Device starting...");
        controllerMap.put(1, controller);
        setIpAndMac(controller);
        while (true) {
            getAndSetLights(controller);
            SystemUtil.sleep(10);
        }
    }

    public void getAndSetLights(Controller controller) {
        List<Light> lights = controller.getLightList();
        Iterator<Light> it = lights.iterator();
        boolean isChangedFlag = false;
        while (it.hasNext()) {
            Light light = it.next();
//            if (setStatus(light)) {
//                isChangedFlag = true;
//            }
        }
        if (isChangedFlag) {
            if (isChangedStatus == 1) {
                isChangedStatus = 0;
            } else {
                isChangedStatus = 1;
            }
        }
    }


    public void setIpAndMac(Controller controller) {
        List<String> adds = SystemUtil.getNetworkAddress();
        for (String addrs : adds) {
            String[] strs = addrs.split(",");
            if (strs.length > 1) {
                if (StringUtils.isNotEmpty(strs[1])) {
                    controller.setMac(strs[0].substring(0, 17));
                    controller.setIp(strs[1]);
                    break;
                } else {
                    // controller.setMac(strs[0].substring(0, 17));
                    // controller.setIp("-999");
                }
            }
        }
    }


    public boolean setStatus(Light light) {
        boolean isChanged = false;
        try {
            if (light.isPwm()) {
                int status = deviceService.checkPwmDigitalStatus((GpioPinPwmOutput) serviceList.get(light.getDeviceName()));
                if (status != light.getDeviceStatus()) {
                    isChanged = true;
                }
                light.setDeviceStatus(status);
            } else {
                PinState pinState = deviceService.checkDigitalStatus((GpioPinDigitalOutput) serviceList.get(light.getDeviceName()));
                if (pinState.getValue() != light.getDeviceStatus()) {
                    isChanged = true;
                }
                light.setDeviceStatus(pinState.getValue());
            }
        } catch (Exception e) {
            logger.error("System check device status error e = {}", e);
        }
        return isChanged;
    }

}
