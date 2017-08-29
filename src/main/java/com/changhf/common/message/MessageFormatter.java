package com.changhf.common.message;


import com.changhf.common.log.SystemErrorLog;
import com.changhf.utils.CollectionUtil;

import java.util.List;

/**
 * MessageFormatter
 *
 * @author <a href="mailto:longlin.ll@alibaba-inc.com">根宝</a>
 * @version V1.0.0
 * @since 2016-04-23
 */
public class MessageFormatter {

    public static String build(String message, Object... args) {
        if (args == null || args.length == 0) {
            return message;
        }
        try {
            return String.format(message, args);
        } catch (Exception e) {
            List<Object> argsList = CollectionUtil.asList(args);
            String errMsg = String.format("format message failed, params={messge:%s, args:[%s]}", message,
                    CollectionUtil.join(argsList, ","));
            SystemErrorLog.error(e, errMsg);
            return message + ", args:[" + CollectionUtil.join(argsList, ",") + "]";
        }
    }
}