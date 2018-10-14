package loan.ppcat.godsky.controller.listener;

import com.pi4j.io.gpio.*;
import loan.ppcat.godsky.controller.dao.goio.GpioFactoryBean;
import loan.ppcat.godsky.controller.model.Controller;
import loan.ppcat.godsky.controller.model.Light;
import loan.ppcat.godsky.controller.model.Switch;
import loan.ppcat.godsky.controller.service.api.IDeviceService;
import loan.ppcat.godsky.controller.service.implement.SystemInitService;
import loan.ppcat.godsky.controller.util.SystemUtil;
import loan.ppcat.godsky.controller.util.TTSForSystem;
import loan.ppcat.godsky.controller.util.TTSUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SystemStartupListener extends RaspiPin {
    private Logger logger = Logger.getLogger(SystemStartupListener.class);
    @Inject
    private SystemInitService systemInitService;
    @Inject
    private IDeviceService deviceService;
    private JSONObject xmlJSONObj = null;
    @Inject
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void initSysteConfig() {
        //TTSUtil.playText("Monkey System 启动中 请稍后... ");
        InputStream in = null;
        InputStreamReader inputStreamReader = null;
        Controller controller = null;
        try {
            in = SystemStartupListener.class.getResourceAsStream("/device.xml");
            inputStreamReader = new InputStreamReader(in, "UTF-8");
            String xml = IOUtils.toString(inputStreamReader);
            xmlJSONObj = XML.toJSONObject(xml);
            JSONObject systemObject = (JSONObject) xmlJSONObj.get("system");
            controller = initSystemInfo(systemObject);
            JSONObject deviceObject = (JSONObject) systemObject.get("devices");
            JSONArray jsonArray = deviceObject.getJSONArray("device");
            Iterator it = jsonArray.iterator();
            List<Light> lightList = new ArrayList<>();
            while (it.hasNext()) {
                JSONObject jsonObject = (JSONObject) it.next();
                Light light = initLightInfo(jsonObject);
                JSONObject aSwitch = (JSONObject) jsonObject.get("switch");
                Switch switcher = initSwitchInfo(aSwitch);
                light.setaSwitch(switcher);
                switcher.setLight(light);
                lightList.add(light);
                light.setController(controller);
                controller.setLightList(lightList);
                //bindRealtions(light, switcher);
            }
        } catch (IOException e) {
            logger.error("IOException = {}", e);
        } catch (JSONException e) {
            logger.error("JSONException error e = {}", e);
        } catch (Exception e) {
            logger.error("Exception error e = {}", e);
        } finally {
            SystemUtil.closeInputStream(in);
            SystemUtil.closeInputStreamReader(inputStreamReader);

        }
        //TTSUtil.playListText("Monkey System 启动成功 , 当前IP地址为 ", SystemUtil.getIps(), "请使用浏览器进行访问");
        systemInitService.setJsonObject(xmlJSONObj);
        systemInitService.setController(controller);
        threadPoolTaskExecutor.submit(new CheckDeviceStatusThread(deviceService, systemInitService.getServiceList(), controller));
        threadPoolTaskExecutor.submit(new NetWorkCheck());
    }

    public void bindRealtions(Light light, Switch switcher) {
        try {
            int footer = Integer.parseInt(light.getDeviceFooter());
            String name = light.getDeviceName();
            boolean pwm = light.isPwm();
            String type = light.getDeviceType();
            int switchFooter = Integer.parseInt(switcher.getSwitchFooter());
            String switchName = switcher.getSwitchName();
            String switchType = switcher.getSwitchType();
            if (light.isPwm()) {
                GpioPinPwmOutput gpioPinPwmOutput = (GpioPinPwmOutput) systemInitService.initSystemService(createDigitalAndPwmPin(footer, name), name, pwm, type);
                GpioPinDigitalInput gpioPinDigitalInput = (GpioPinDigitalInput) systemInitService.initSystemService(createDigitalAndPwmPin(switchFooter, switchName), switchName, false, switchType);
                deviceService.bindPWMDevice(gpioPinDigitalInput, gpioPinPwmOutput, 1);
            } else {
                GpioPinDigitalOutput gpioPinDigitalOutput = (GpioPinDigitalOutput) systemInitService.initSystemService(createDigitalPin(footer, name), name, pwm, type);
                GpioPinDigitalInput gpioPinDigitalInput = (GpioPinDigitalInput) systemInitService.initSystemService(createDigitalAndPwmPin(switchFooter, switchName), switchName, false, switchType);
                deviceService.bindDevice(gpioPinDigitalInput, gpioPinDigitalOutput);
            }

        } catch (Exception e) {
            logger.error("try to bind relation error e = {}", e);
        }
    }

    public Controller initSystemInfo(JSONObject systemObject) {
        Controller controller = new Controller();
        controller.setId(systemObject.getInt("id"));
        controller.setIp("");
        controller.setName(systemObject.getString("name"));
        controller.setType(systemObject.getString("type"));
        controller.setRegisterId(systemObject.getString("registerId"));
        controller.setVersion(systemObject.getString("version"));
        return controller;
    }

    public Light initLightInfo(JSONObject jsonObject) {
        Light light = new Light();
        boolean pwm = jsonObject.getBoolean("pwm");
        light.setPwm(pwm);
        int id = jsonObject.getInt("id");
        light.setId(id);
        int setup = jsonObject.getInt("setup");
        light.setSetup(setup);
        String footer = jsonObject.get("footer").toString();
        light.setDeviceFooter(footer);
        float powerSize = jsonObject.getInt("powerSize");
        light.setPowerSize(powerSize);
        String name = jsonObject.getString("name");
        light.setDeviceName(name);
        String mode = jsonObject.getString("mode");
        light.setMode(mode);
        String registerId = jsonObject.getString("registerId");
        light.setRegisterId(registerId);
        String type = jsonObject.getString("type");
        light.setDeviceType(type);
        boolean breath = jsonObject.getBoolean("breath");
        light.setBreath(breath);
        String describeInfo = jsonObject.getString("describeInfo");
        light.setDescribeInfo(describeInfo);
        String nickname = jsonObject.getString("nickname");
        light.setNickname(nickname);
        return light;
    }

    public Switch initSwitchInfo(JSONObject aSwitch) {
        Switch switcher = new Switch();
        int switchId = aSwitch.getInt("id");
        switcher.setId(switchId);
        String switchFooter = aSwitch.get("footer").toString();
        switcher.setSwitchFooter(switchFooter);
        String switchRegisterId = aSwitch.getString("registerId");
        switcher.setRegisterId(switchRegisterId);
        String switchType = aSwitch.getString("type");
        switcher.setSwitchType(switchType);
        String switchName = aSwitch.getString("name").toString();
        switcher.setSwitchName(switchName);
        String switchMode = aSwitch.getString("mode").toString();
        switcher.setSwitchMode(switchMode);
        int switchSetup = aSwitch.getInt("setup");
        switcher.setSwitchSetup(switchSetup);
        return switcher;
    }
}
