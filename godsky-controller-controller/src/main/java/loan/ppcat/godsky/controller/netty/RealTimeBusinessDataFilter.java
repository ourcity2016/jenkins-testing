package loan.ppcat.godsky.controller.netty;

import loan.ppcat.godsky.controller.model.Light;
import loan.ppcat.godsky.controller.model.ServerLightResponse;
import loan.ppcat.godsky.controller.service.api.IDeviceService;
import loan.ppcat.godsky.controller.service.implement.SystemInitService;

public class RealTimeBusinessDataFilter {
    private static IDeviceService deviceService;
    private static SystemInitService systemInitService;

    private RealTimeBusinessDataFilter() {

    }

    public RealTimeBusinessDataFilter(IDeviceService _deviceService, SystemInitService _systemInitService) {
        deviceService = _deviceService;
        systemInitService = _systemInitService;
    }

    public static void doDataFilter(Object object) {
        if (object instanceof ServerLightResponse) {
            ServerLightResponse serverLightResponse = (ServerLightResponse) object;
            handleWithController(serverLightResponse);
        } else {

        }

    }

    public static void handleWithController(ServerLightResponse serverLightResponse) {
        String type = serverLightResponse.getType();
        Light light = serverLightResponse.getLight();
        try {
            if (type.equals("toggle")) {
                if (light.isPwm()) {
                    deviceService.togglebreathDigital(systemInitService.getGpioPinPwmOutputByName(light.getDeviceName()), 1);
                } else {
                    deviceService.toggleNormalDigital(systemInitService.getGpioPinDigitalOutputByName(light.getDeviceName()));
                }
            }
        } catch (Exception ex) {

        }
    }
}
