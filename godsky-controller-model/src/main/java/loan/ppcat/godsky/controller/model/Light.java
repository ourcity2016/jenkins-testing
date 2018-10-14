package loan.ppcat.godsky.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Light implements Serializable {
    private static final long serialVersionUID = -5132311060872550788L;
    private int id;
    private String registerId;
    private String deviceFooter;
    private int deviceStatus;
    private String deviceType;
    private String deviceName;
    private String nickname;
    private String mode;
    private String describeInfo;
    private boolean pwm;
    private boolean breath;
    private int setup;
    private float powerSize;
    @JsonIgnore
    private Controller controller;
    private Switch aSwitch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getDeviceFooter() {
        return deviceFooter;
    }

    public void setDeviceFooter(String deviceFooter) {
        this.deviceFooter = deviceFooter;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
    }

    public boolean isPwm() {
        return pwm;
    }

    public void setPwm(boolean pwm) {
        this.pwm = pwm;
    }

    public boolean isBreath() {
        return breath;
    }

    public void setBreath(boolean breath) {
        this.breath = breath;
    }

    public int getSetup() {
        return setup;
    }

    public void setSetup(int setup) {
        this.setup = setup;
    }

    public float getPowerSize() {
        return powerSize;
    }

    public void setPowerSize(float powerSize) {
        this.powerSize = powerSize;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Switch getaSwitch() {
        return aSwitch;
    }

    public void setaSwitch(Switch aSwitch) {
        this.aSwitch = aSwitch;
    }
}
