package loan.ppcat.godsky.controller.model;

import java.io.Serializable;

public class Device implements Serializable {
    private int id;
    private String type;
    private String name;
    private String mode;
    private String footer;
    private String setup;
    private boolean pwm;
    private boolean breath;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
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
}
