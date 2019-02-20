package it.unisalento.se.saw.builder;

import com.sun.istack.Nullable;

import java.util.Date;

public class CalendarExamType extends CalendarDate{

	@Override
	public CalendarDate calendarDate(Date startTime, Date endTime, Date date,
	                                 @Nullable CalendarType type, @Nullable Date startDate, @Nullable Date endDate){
		System.out.println("Prima di tutto");
		System.out.println(startTime + "from Calendar lesson type");
		return new CalendarDate(startTime, endTime, date, type(), startDate, endDate);
	}

	@Override
	public CalendarType type(){
		return new ExamType();
	}
}
