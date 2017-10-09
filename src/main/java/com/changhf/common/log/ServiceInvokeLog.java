package com.changhf.common.log;

import com.changhf.common.message.MessageFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceInvokeLog {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceInvokeLog.class);

    public static void info(String format, Object... args) {
        LOG.info(MessageFormatter.build(format, args));
    }

    public static void error(String format, Object... args) {
        LOG.error(MessageFormatter.build(format, args));
    }

    public static void error(Throwable t, String format, Object... args) {
        LOG.error(MessageFormatter.build(format, args), t);
    }

}
