package com.changhf.common.message;


import com.changhf.common.log.SystemErrorLog;
import com.changhf.utils.CollectionUtil;

import java.util.List;

/**
 * @author changhuafeng
 * @version V1.0.0
 * @since 2017/09/15
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
