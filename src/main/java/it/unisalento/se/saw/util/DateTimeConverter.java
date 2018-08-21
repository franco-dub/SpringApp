package it.unisalento.se.saw.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeConverter {

	
	public LocalTime convertToLocalTime(Date dateToConvert) {
		Instant instant = Instant.ofEpochMilli(dateToConvert.getTime());
		LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
		return res;
	}
	
	public LocalDate convertToLocalDate(Date dateToConvert) {
		Instant instant = Instant.ofEpochMilli(dateToConvert.getTime());
		LocalDate res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		return res;
	}
}
