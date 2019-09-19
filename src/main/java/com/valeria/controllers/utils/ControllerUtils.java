package com.valeria.controllers.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControllerUtils {
		
	public static Date getDateFromParam(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		
		if (s.isEmpty()) {
			return date;
		} else {
			try {
				return sdf.parse(s);
			} catch (ParseException e) {
				return date;
			}
		}		
	}
}
