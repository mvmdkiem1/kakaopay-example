package com.kakaopay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String getDateStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
		return format.format(date);
	}
	
	public static boolean getMinCompre(Date date, long compre) {
		boolean bool = false;
		
		Date now = new Date();
		long diff = now.getTime() - date.getTime();
		long min = diff / (1000 * 60);
		
		System.out.println(min);
		
		if(min <= compre) {
			bool = true;
		}
		
		return bool;
	}
}
