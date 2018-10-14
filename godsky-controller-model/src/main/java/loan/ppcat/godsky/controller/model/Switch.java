package loan.ppcat.godsky.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Switch implements Serializable {
    private static final long serialVersionUID = -1939506028986802244L;
    private int id;
    private String registerId;
    private String switchFooter;
    private String switchType;
    private String switchName;
    private String switchMode;
    private int switchSetup;
    @JsonIgnore
    private Light light;

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

    public String getSwitchFooter() {
        return switchFooter;
    }

    public void setSwitchFooter(String switchFooter) {
        this.switchFooter = switchFooter;
    }

    public String getSwitchType() {
        return switchType;
    }

    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }

    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public String getSwitchMode() {
        return switchMode;
    }

    public void setSwitchMode(String switchMode) {
        this.switchMode = switchMode;
    }

    public int getSwitchSetup() {
        return switchSetup;
    }

    public void setSwitchSetup(int switchSetup) {
        this.switchSetup = switchSetup;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }
}
