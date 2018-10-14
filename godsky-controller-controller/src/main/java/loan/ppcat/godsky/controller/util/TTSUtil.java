package loan.ppcat.godsky.controller.util;

import java.io.InputStream;
import java.util.List;

public class TTSUtil {
    private static String akid = "LTAITL3anXJX2Oud";
    private static String akSecret = "mpSE30u1FX54hPw34gwEvpCc7I8pXG";

    public static void playText(String text) {
        synchronized (TTSUtil.class) {
            try {
                TTSForSystem ttsForSystem = new TTSForSystem(akid, akSecret, text);
                ttsForSystem.startTTS();
                ttsForSystem.shutDown();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void playStream(InputStream inputStream) {
        synchronized (TTSUtil.class) {
            try {
                SoundRead.playText(inputStream);
            } catch (Exception e) {
            }
        }
    }

    public static void playListText(String header, List<String> texts, String end) {
        synchronized (TTSUtil.class) {
            try {
                StringBuilder builder = new StringBuilder();
                int size = texts.size();
                int i = 1;
                for (String text : texts) {
                    builder.append(text);
                    if (i < size) {
                        builder.append("或者");
                    }
                    i++;
                }
                TTSForSystem ttsForSystem = new TTSForSystem(akid, akSecret, header + builder.toString() + "," + end);
                ttsForSystem.startTTS();
                ttsForSystem.shutDown();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
