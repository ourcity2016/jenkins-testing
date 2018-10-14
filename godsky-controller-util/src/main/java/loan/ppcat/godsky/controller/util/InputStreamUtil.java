package loan.ppcat.godsky.controller.util;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamUtil {
    public static void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }
}
