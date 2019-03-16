package com.changhf.common.log;

import com.changhf.common.message.MessageFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author changhuafeng
 * @version V1.0.0
 * @since 2017/09/15
 */
public class SystemErrorLog {

    private static final Logger LOG = LoggerFactory.getLogger(SystemErrorLog.class);

    public static void error(String format, Object... args) {
        LOG.error(MessageFormatter.build(format, args));
    }

    public static void error(Throwable t, String format, Object... args) {
        LOG.error(MessageFormatter.build(format, args), t);
    }

}
