package com.wz.test.oshi;

import cn.hutool.system.OsInfo;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.wz.test.LogBaseTest;
import org.junit.jupiter.api.Test;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

/**
 * @projectName: wz-component-test
 * @package: com.wz.test
 * @className: OSTest
 * @description:
 * @author: zhi
 * @date: 2021/12/21
 * @version: 1.0
 */
public class OSTest extends LogBaseTest {

    @Test
    public void osInfo() {
        OsInfo osInfo = new OsInfo();
        log.info("OS info: {}", osInfo);
    }

    @Test
    public void cpuInfo() {
        final CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        log.info("CPU info: {}", cpuInfo);
    }

    @Test
    public void systemInfo() {
        SystemInfo info = new SystemInfo();
        final HardwareAbstractionLayer hardware = info.getHardware();
        log.info("硬件: {}", hardware);
        final OperatingSystem os = info.getOperatingSystem();
        log.info("操作系统: {}", os);
        log.info("Family: {}", os.getFamily());
        log.info("处理数量: {}", os.getProcessCount());
        log.info("线程数量: {}", os.getThreadCount());
        log.info("Bitness: {}", os.getBitness());
        log.info("文件系统: {}", os.getFileSystem());
        log.info("InternetProtocolStats: {}", os.getInternetProtocolStats());
        log.info("制造商: {}", os.getManufacturer());
        log.info("网络参数: {}", os.getNetworkParams());
        log.info("processes: {}", os.getProcesses());
        log.info("processId: {}", os.getProcessId());
        log.info("services: {}", os.getServices());
        log.info("sessions: {}", os.getSessions());
        log.info("系统启动时间: {}", os.getSystemBootTime());
        log.info("系统更新时间: {}", os.getSystemUptime());
        log.info("版本信息: {}", os.getVersionInfo());
        log.info("是否高温: {}", os.isElevated());
    }
}
