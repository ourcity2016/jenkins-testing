package loan.ppcat.godsky.controller.model;

import java.io.Serializable;

public class ServerLightResponse implements Serializable {
    private String type;
    private Light light;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }
}
