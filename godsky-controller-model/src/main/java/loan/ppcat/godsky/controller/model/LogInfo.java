package loan.ppcat.godsky.controller.model;

import java.io.Serializable;

public class LogInfo implements Serializable {
    private String name;
    private String data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
