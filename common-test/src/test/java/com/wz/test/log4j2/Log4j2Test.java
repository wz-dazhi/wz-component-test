package com.wz.test.log4j2;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @projectName: wz-component-test
 * @package: com.wz.test.log4j2
 * @className: Log4j2Test
 * @description:
 * @author: zhi
 * @date: 2021/12/21
 * @version: 1.0
 */
public class Log4j2Test {
    private static final Logger log = LoggerFactory.getLogger(Log4j2Test.class);

    @Test
    public void log() {
        log.info("----------info--------------");
        log.debug("----------debug--------------");
        log.error("----------error--------------");
    }
}
