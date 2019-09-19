package com.valeria.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static Date getDate(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
		if (s.isEmpty()) {
			return new Date();
		} else {
			try {
				return sdf.parse(s);
			} catch (ParseException e) {
				return new Date();
			}
		}		
	}
	
	public static Date getDateTime(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
		if (s.isEmpty()) {
			return new Date();
		} else {
			try {
				return sdf.parse(s);
			} catch (ParseException e) {
				return new Date();
			}
		}		
	}

}
