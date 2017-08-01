package com.changhf.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {
	public Date convert(String source) {
		Date result = null;
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			result = fm.parse(source);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
		return result;
	}
}
