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
}
