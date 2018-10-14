package loan.ppcat.godsky.controller.controller;

import loan.ppcat.godsky.controller.model.SystemInfo;
import loan.ppcat.godsky.controller.netty.NettyGodSkyClient;
import loan.ppcat.godsky.controller.util.SystemUtil;
import loan.ppcat.godsky.controller.util.TTSUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.URL;
import java.util.List;

@Controller
@RequestMapping("/system")
public class SystemController {
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public SystemInfo getSystemInfo() throws IOException, InterruptedException {
        int kb = 1024;
        SystemInfo systemInfo = new SystemInfo();
        File root = new File("/");
        long totalSpace = root.getTotalSpace() / (kb * kb);
        long freeSpace = root.getFreeSpace() / (kb * kb);
        systemInfo.setTotalPhysicalMemorySize(totalSpace);
        systemInfo.setFreePhysicalMemorySize(freeSpace);
        systemInfo.setUsedPhysicalMemorySize(totalSpace - freeSpace);
        long totalMemory = com.pi4j.system.SystemInfo.getMemoryTotal() / (kb * kb);
        long freeMemory = com.pi4j.system.SystemInfo.getMemoryFree()/ (kb * kb);
        long maxMemory = com.pi4j.system.SystemInfo.getMemoryTotal() / (kb * kb);
        long userMem = com.pi4j.system.SystemInfo.getMemoryUsed() / (kb * kb);
        systemInfo.setAvailableProcessors(Runtime.getRuntime().availableProcessors());
        systemInfo.setTotalMemory(totalMemory);
        systemInfo.setFreeMemory(freeMemory);
        systemInfo.setMaxMemory(maxMemory);
        systemInfo.setUsedMemory(userMem);
        systemInfo.setCpuRatio(com.pi4j.system.SystemInfo.getClockFrequencyArm());
        systemInfo.setCpuTmp(com.pi4j.system.SystemInfo.getCpuTemperature());
        StringBuilder ipaddr = new StringBuilder();
        List<String> adds = SystemUtil.getNetworkAddress();
        for (String addrs : adds) {
            String[] strs = addrs.split(",");
            if (strs.length > 1) {
                if (StringUtils.isNotEmpty(strs[1])) {
                    ipaddr.append(strs[0].substring(0, 17) + " : " + strs[1] + "</br>");
                } else {
                    ipaddr.append(strs[0].substring(0, 17) + ": unknown </br>");
                }
            }
        }
        systemInfo.setIpAddrAndMac(ipaddr.toString());
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String jdkVersion = System.getProperty("java.version");
        systemInfo.setOsName(com.pi4j.system.SystemInfo.getOsName());
        systemInfo.setOsVersion(com.pi4j.system.SystemInfo.getOsVersion());
        systemInfo.setJdkVersion(com.pi4j.system.SystemInfo.getJavaVersion());
        return systemInfo;
    }

    @RequestMapping(value = "/recovery", method = RequestMethod.GET)
    @ResponseBody
    public void recovery() throws IOException {
        TTSUtil.playText("Monkey System 正在恢复出厂设置 请稍后... ");
        String srcPath = SystemController.class.getResource("/device-recovery.xml").getFile();
        String desPath = SystemController.class.getResource("/device.xml").getFile();
        File file = new File(srcPath);
        File srcFile = new File(desPath);
        if (file.exists()) {
            file.delete();
        }
        FileUtils.copyFile(srcFile, file);

    }

    @RequestMapping(value = "/server", method = RequestMethod.GET)
    @ResponseBody
    public String readProps() throws IOException {
        URL url = SystemController.class.getResource("/server.properties");
        File file = new File(url.getPath());
        String str = FileUtils.readFileToString(file, "UTF-8");
        return str;
    }

    @RequestMapping(value = "/server", method = RequestMethod.POST)
    @ResponseBody
    public void setProps(String properties) throws IOException {
        URL url = SystemController.class.getResource("/server.properties");
        File file = new File(url.getPath());
        FileUtils.write(file, properties, "UTF-8");
    }

    @RequestMapping(value = "/server/status", method = RequestMethod.GET)
    @ResponseBody
    public boolean status() throws IOException {
        return NettyGodSkyClient.isCurrentStatus();
    }

    @RequestMapping(value = "/server/ip", method = RequestMethod.GET)
    @ResponseBody
    public String getIp() throws IOException {
        return NettyGodSkyClient.getHost();
    }

    @RequestMapping(value = "/server/port", method = RequestMethod.GET)
    @ResponseBody
    public int getPort() throws IOException {
        return NettyGodSkyClient.getPort();
    }

    @RequestMapping(value = "/restart", method = RequestMethod.GET)
    @ResponseBody
    public String restart() throws Exception {
        TTSUtil.playText("Monkey System 正在执行重启 请稍后... ");
        String result = "";
        try {
            Runtime runtime = Runtime.getRuntime();
            Process ps = runtime.exec(new String[]{"bash", "/opt/apache-tomcat-7.0.85/bin/restart.sh"});
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
