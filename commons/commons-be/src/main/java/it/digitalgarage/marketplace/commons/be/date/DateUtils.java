package it.digitalgarage.marketplace.commons.be.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
    public static ZonedDateTime zonedDateTimeOf(LocalDateTime localDateTime, String zoneId) {
        ZoneId id = ZoneId.of(zoneId);
        return localDateTime.atZone(id);
    }

    public static LocalDateTime formatLocalDateTime(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, formatter);
    }

    public static String formatDate(java.util.Date d, String format ) {
        SimpleDateFormat s = new SimpleDateFormat(format);
        return (s.format(d));
    }
    
    public static final String format(Date date){
		return date!=null ? new SimpleDateFormat("dd/MM/yyyy").format(date) : null;
	}
    
    public static final Date parse(String s) throws ParseException{
		return s!=null ? new SimpleDateFormat("dd/MM/yyyy").parse(s) : null;
	}
	
	
	public static final int compareDate(Date min,Date max){
		Calendar min1 = getCalendar(min);
		reset(min1);
		Calendar max1 = getCalendar(max);
		reset(max1);
		return min1.compareTo(max1);
	}
	
	public static final int distanzaInGiorni(Date d1,Date d2){
		Calendar c1 = getCalendar(d1);
		reset(c1);
		Calendar c2 = getCalendar(d2);
		reset(c2);
		
		long giorni = (c1.getTime().getTime() - c2.getTime().getTime())    / (24 * 3600 * 1000);
		
		return (int)giorni;
	}
	
	public static final Date add(Date date,int giorni){
		Calendar c = getCalendar(date);
		reset(c);
		c.add(Calendar.DATE, giorni);
		return new Date(c.getTimeInMillis());
	}
	
	public static final Date addMonth(Date date,int mesi){
		Calendar c = getCalendar(date);
		reset(c);
		c.add(Calendar.MONTH, mesi);
		return new Date(c.getTimeInMillis());
	}
	
	public static final Date addYear(Date date,int anno){
		Calendar c = getCalendar(date);
		reset(c);
		c.add(Calendar.YEAR, anno);
		return new Date(c.getTimeInMillis());
	}


	private static Calendar getCalendar(Date min) {
		Calendar min1 = Calendar.getInstance();
		min1.setTime(min);
		return min1;
	}
	
	public static Date getDate(){
		Calendar c = Calendar.getInstance();
		reset(c);
		return c.getTime();
	}


	private static void reset(Calendar min1) {
		min1.set(Calendar.HOUR, 0);
		min1.set(Calendar.MINUTE, 0);
		min1.set(Calendar.SECOND, 0);
		min1.set(Calendar.MILLISECOND, 0);
	}
	
	
	public static final int compareDateWithTime(Date min,Date max){
		Calendar min1 = getCalendar(min);
		Calendar max1 = getCalendar(max);
		return min1.compareTo(max1);
	}

}
