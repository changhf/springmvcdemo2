package com.changhf.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换
 *
 * @author changhf
 * @version V1.0.0
 * @since 2017/09/07
 */
@Component
public class DateConverter {

    private SimpleDateFormat formatter;

    public DateConverter() {
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public Date parse(String str) throws ParseException {
        return formatter.parse(str);
    }

    public String format(Date date) {
        return formatter.format(date);
    }
}
