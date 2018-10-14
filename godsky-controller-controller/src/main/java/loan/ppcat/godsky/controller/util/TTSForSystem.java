package loan.ppcat.godsky.controller.util;

import com.alibaba.idst.nls.NlsClient;
import com.alibaba.idst.nls.NlsFuture;
import com.alibaba.idst.nls.event.NlsEvent;
import com.alibaba.idst.nls.event.NlsListener;
import com.alibaba.idst.nls.protocol.NlsRequest;
import com.alibaba.idst.nls.protocol.NlsResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TTSForSystem implements NlsListener {
    private NlsClient client = new NlsClient();
    private String akId;
    private String akSecret;
    private String tts_text = "系统启动成功,当前IP地址为192.168.0.100,请使用浏览器访问";
    private InputStream inputStream = null;

    public TTSForSystem(String akId, String akSecret, String tts_text) {
        //System.out.println("init Nls client...");
        this.akId = akId;
        this.akSecret = akSecret;
        this.tts_text = tts_text;
        // 初始化NlsClient
        client.init();
    }

    public void shutDown() {
        //System.out.println("close NLS client");
        // 关闭客户端并释放资源
        client.close();
        if (inputStream != null) {
            //AudioPlayer.player.start(inputStream);
            try {
                SoundRead.playText(inputStream);
            } catch (Exception e) {
            }
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
        //System.out.println("demo done");
    }


    public void startTTS() {
//        File file = new File("/opt/audio/startup.wav");
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        NlsRequest req = new NlsRequest();
        String appkey = "nls-service";
        req.setAppKey(appkey); // 设置语音文件格式
        req.setTtsRequest(tts_text); //传入测试文本，返回语音结果
        req.setTtsEncodeType("wav");//返回语音数据格式，支持pcm,wav.alaw
        req.setTtsVolume(30);       //音量大小默认50，阈值0-100
        req.setTtsSpeechRate(0);    //语速，阈值-500~500
        req.setTtsBackgroundMusic(1, 0);//背景音乐编号,偏移量
        req.authorize(akId, akSecret); // 请替换为用户申请到的Access Key ID和Access Key Secret
        try {
            ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
            NlsFuture future = client.createNlsFuture(req, this); // 实例化请求,传入请求和监听器
            int total_len = 0;
            byte[] data;
            while ((data = future.read()) != null) {
                fileOutputStream.write(data, 0, data.length);
                total_len += data.length;
            }
            inputStream = new ByteArrayInputStream(fileOutputStream.toByteArray());
            fileOutputStream.close();
            future.await(10000); // 设置服务端结果返回的超时时间

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(NlsEvent e) {
        NlsResponse response = e.getResponse();
        String result = "";
        int statusCode = response.getStatus_code();
        if (response.getTts_ret() != null) {
            result += "\nget tts result: statusCode=[" + statusCode + "], " + response.getTts_ret();
        }

        if (result != null) {
            //System.out.println(result);
        } else {
           // System.out.println(response.jsonResults.toString());
        }
    }

    @Override
    public void onOperationFailed(NlsEvent e) {
        //识别失败的回调
       // System.out.print("on operation failed: ");
       // System.out.println(e.getErrorMessage());
    }

    @Override
    public void onChannelClosed(NlsEvent e) {
        //socket 连接关闭的回调
        //System.out.println("on websocket closed.");
    }
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        String akId = "LTAITL3anXJX2Oud";
//        String akSecret = "mpSE30u1FX54hPw34gwEvpCc7I8pXG";
//        TTSDemo ttsDemo = new TTSDemo(akId, akSecret);
//        ttsDemo.startTTS();
//        ttsDemo.shutDown();
//    }
}