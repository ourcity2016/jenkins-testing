package loan.ppcat.godsky.controller.util;

import javax.sound.sampled.*;
import java.io.InputStream;

public class SoundRead {
    private static Mixer.Info finalMixerInfo = null;

    static {
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        for (Mixer.Info aMixerInfo : mixerInfo) {
            if (aMixerInfo.getName().contains("Device") && aMixerInfo.getName().contains("plughw")) {
                finalMixerInfo = aMixerInfo;
                break;
            }
        }
    }

    public static void playText(InputStream in) {
        Clip clip = null;
        AudioInputStream stream = null;
        try {
            if (in != null) {
                stream = AudioSystem.getAudioInputStream(in);
                AudioFormat format = stream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);
                Mixer mixer = AudioSystem.getMixer(finalMixerInfo);
                clip = (Clip) mixer.getLine(info);
                clip.open(stream);
                clip.loop(0);
                do {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException iex) {
                    }
                } while (clip.isRunning());
            }
        } catch (Exception e) {
        } finally {
            try {
                if (clip != null) {
                    clip.close();
                }
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception x) {
            }
        }
    }

}