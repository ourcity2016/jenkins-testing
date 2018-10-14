package loan.ppcat.godsky.controller.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ReadUtil {
    public static byte[] readInputStreamToBytes(File file) throws IOException {
        return FileUtils.readFileToByteArray(file);
    }
}
