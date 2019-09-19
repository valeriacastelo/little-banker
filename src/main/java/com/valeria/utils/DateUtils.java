package com.valeria.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

public class DateUtils {
	
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	public static Date getDate(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		try {
			return sdf.parse(s);
		} catch (ParseException e) {
			throw new DateTimeException("Date format error. Required format [" + DATE_FORMAT + "]");
		}
	}
	
	public static Date getDateTime(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);		
		try {
			return sdf.parse(s);
		} catch (ParseException e) {
			throw new DateTimeException("Date format error. Required format [" + DATE_TIME_FORMAT + "]");
		}
	}
}
