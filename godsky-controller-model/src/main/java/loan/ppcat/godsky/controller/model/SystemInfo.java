package loan.ppcat.godsky.controller.model;

import java.io.Serializable;

public class SystemInfo implements Serializable {
    private long totalMemory;
    private long freeMemory;
    private long maxMemory;
    private long availableProcessors;
    private long usedMemory;
    private int totalThread;
    private double cpuRatio;
    private String osName;
    private String osVersion;
    private String jdkVersion;
    private String ipAddrAndMac;
    private long freePhysicalMemorySize;
    private long totalPhysicalMemorySize;
    private long usedPhysicalMemorySize;
    private float cpuTmp;

    public float getCpuTmp() {
        return cpuTmp;
    }

    public void setCpuTmp(float cpuTmp) {
        this.cpuTmp = cpuTmp;
    }

    public String getIpAddrAndMac() {
        return ipAddrAndMac;
    }

    public void setIpAddrAndMac(String ipAddrAndMac) {
        this.ipAddrAndMac = ipAddrAndMac;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public long getAvailableProcessors() {
        return availableProcessors;
    }

    public void setAvailableProcessors(long availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public long getFreePhysicalMemorySize() {
        return freePhysicalMemorySize;
    }

    public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
        this.freePhysicalMemorySize = freePhysicalMemorySize;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public int getTotalThread() {
        return totalThread;
    }

    public void setTotalThread(int totalThread) {
        this.totalThread = totalThread;
    }

    public double getCpuRatio() {
        return cpuRatio;
    }

    public void setCpuRatio(double cpuRatio) {
        this.cpuRatio = cpuRatio;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public long getTotalPhysicalMemorySize() {
        return totalPhysicalMemorySize;
    }

    public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
        this.totalPhysicalMemorySize = totalPhysicalMemorySize;
    }

    public long getUsedPhysicalMemorySize() {
        return usedPhysicalMemorySize;
    }

    public void setUsedPhysicalMemorySize(long usedPhysicalMemorySize) {
        this.usedPhysicalMemorySize = usedPhysicalMemorySize;
    }

    public String getJdkVersion() {
        return jdkVersion;
    }

    public void setJdkVersion(String jdkVersion) {
        this.jdkVersion = jdkVersion;
    }

}
