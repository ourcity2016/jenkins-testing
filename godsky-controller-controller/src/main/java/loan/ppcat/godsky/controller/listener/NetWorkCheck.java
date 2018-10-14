package loan.ppcat.godsky.controller.listener;

import loan.ppcat.godsky.controller.util.InputStreamUtil;
import loan.ppcat.godsky.controller.util.TTSUtil;

import java.io.InputStream;
import java.net.InetAddress;

public class NetWorkCheck implements Runnable {
    private boolean isChangeStatus = false;
    private boolean currentChangeStatus = false;

    @Override
    public void run() {
        while (true) {
            try {
                int timeout = 2000;
                InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
                for (InetAddress address : addresses) {
                    if (address.isReachable(timeout)) {
                        changeStatus(false);
                        break;
                    } else {
                        changeStatus(true);
                        break;
                    }
                }
            } catch (Exception e) {
                changeStatus(true);
            }
            if (isChangeStatus) {
                if (currentChangeStatus) {
                    TTSUtil.playText("Monkey System 网络连接成功 已接入互联网... ");
                } else {
                    InputStream inputStream = null;
                    try {
                        inputStream = NetWorkCheck.class.getResourceAsStream("/offline.wav");
                        TTSUtil.playStream(inputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        InputStreamUtil.closeInputStream(inputStream);
                    }

                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }
    }

//    public static void main(String[] args) {
//        new Thread(new NetWorkCheck()).start();
//    }

    private void changeStatus(boolean status) {
        if (currentChangeStatus == (status == false ? false : true)) {
            isChangeStatus = true;
        } else {
            isChangeStatus = false;
        }
        currentChangeStatus = (status == true ? false : true);
    }
}
