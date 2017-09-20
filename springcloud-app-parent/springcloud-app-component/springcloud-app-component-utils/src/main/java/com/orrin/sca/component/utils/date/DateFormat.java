package com.orrin.sca.component.utils.date;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author orrin.zhang on 2017/8/3.
 */
public class DateFormat {
	public static String defaultFormat(Instant instant){
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		String format = localDateTime.format(DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD_HH_MM_SS.getValue()));
		return format;
	}

	public static String defaultFormat(){
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
		String format = localDateTime.format(DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD_HH_MM_SS.getValue()));
		return format;
	}

	public static String defaultPatternFormat(DatePattern datePattern){
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
		String format = localDateTime.format(DateTimeFormatter.ofPattern(datePattern.getValue()));
		return format;
	}

	public static String patternFormat(Date date, DatePattern datePattern){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern.getValue());
		return simpleDateFormat.format(date);
	}
}
