package loan.ppcat.godsky.controller.model;

import java.io.Serializable;

public class HeartBeat implements Serializable {
    private static final long serialVersionUID = -1132311060872550789L;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
