package loan.ppcat.godsky.controller.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SystemUtil {
    public static List<String> getNetworkAddress() {
        List<String> result = new ArrayList<String>();
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                StringBuffer stringBuffer = new StringBuffer();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    byte[] bytes = ni.getHardwareAddress();
                    if (bytes != null) {
                        for (int i = 0; i < bytes.length; i++) {
                            if (i != 0) {
                                stringBuffer.append("-");
                            }
                            int tmp = bytes[i] & 0xff;
                            String str = Integer.toHexString(tmp);
                            if (str.length() == 1) {
                                stringBuffer.append("0" + str);
                            } else {
                                stringBuffer.append(str);
                            }
                        }
                        stringBuffer.append("");
                    }
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(':') == -1) {
                        String mac = stringBuffer.toString().toUpperCase();
                        if (StringUtils.isEmpty(ip.getHostAddress())) {
                            result.add(mac + "," + "unknown");
                        } else {
                            result.add(mac + "," + ip.getHostAddress());
                        }
                    }
                }
            }

        } catch (Exception e) {
        }
        return result;
    }

    public static List<String> getIps() {
        List<String> adds = SystemUtil.getNetworkAddress();
        List<String> ips = new ArrayList<>();
        for (String addrs : adds) {
            String[] strs = addrs.split(",");
            if (strs.length > 1) {
                if (StringUtils.isNotEmpty(strs[1])) {
                    ips.add(strs[1]);
                } else {
                    //ipaddr.append(strs[0].substring(0, 17) + ": unknown </br>");
                }
            }
        }
        return ips;
    }

    public static void sleep(long millseconds) {
        try {
            Thread.sleep(millseconds);
        } catch (Exception e) {
        }
    }

    public static void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }

    }

    public static void closeInputStreamReader(InputStreamReader inputStreamReader) {
        if (inputStreamReader != null) {
            try {
                inputStreamReader.close();
            } catch (IOException e) {
            }
        }
    }
}
