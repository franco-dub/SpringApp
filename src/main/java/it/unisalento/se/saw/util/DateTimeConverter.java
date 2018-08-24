package it.unisalento.se.saw.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeConverter {

	
	public LocalTime convertToLocalTime(Date dateToConvert) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateToConvert.getTime()), 
				ZoneId.systemDefault()).toLocalTime();
	}
	
	public LocalDate convertToLocalDate(Date dateToConvert) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateToConvert.getTime()), 
				ZoneId.systemDefault()).toLocalDate();
	}
	
	public static Date asDate(LocalDate localDate) {
	    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	  }
	
	public static Date asDate(LocalDateTime localDateTime) {
	    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	  }
}
