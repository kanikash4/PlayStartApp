package utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DATE_START_HOUR_FORMAT = "yyyy-MM-dd'T'00:00:00Z";
    public static final String DATE_END_HOUR_FORMAT = "yyyy-MM-dd'T'23:59:59Z";
    public static final int NO_OF_DAYS_TO_ADD = 5;


    public static Date nowDate(){
    	return new DateTime(DateTimeZone.forID(Constant.ASIA_KL)).
    			toDate();
    }
    public static String nowDateAsString(){
    	return new DateTime( DateTimeZone.forID(Constant.ASIA_KL)).
    			toString(DateTimeFormat.forPattern(DATE_FORMAT));
    }	
    
    public static String todayStartAsString(){
    	return new DateTime( DateTimeZone.forID(Constant.ASIA_KL)).
    			toString(DateTimeFormat.forPattern(DATE_START_HOUR_FORMAT));
    }	
    
    public static String todayEndAsString(){
    	return new DateTime( DateTimeZone.forID(Constant.ASIA_KL)).
    			toString(DateTimeFormat.forPattern(DATE_END_HOUR_FORMAT));
    }

    public static DateTime convertToDate(String date){
        return DateTime.parse(date,DateTimeFormat.forPattern(DATE_FORMAT));
    }
    
    //TODO:Fix Timezone issue in this
	public static String getExpiryDate() {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DATE, NO_OF_DAYS_TO_ADD); // number of days to
		return df.format(c.getTime());
	}

	//TODO:Fix Timezone issue in this
	public static boolean isDateExpired(String date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    	Date now = new java.util.Date();
	    	Date expiry = sdf.parse(date);;
	    	Calendar calNow = Calendar.getInstance();
	    	Calendar calExpiry = Calendar.getInstance();
	    	calNow.setTime(now);
	    	calExpiry.setTime(expiry);
	    	
	    	if(calNow.after(calExpiry)){
	    		return Boolean.TRUE;
	    	}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return Boolean.FALSE;
    }
	
	public static boolean isDateBetweenTheseDates(String date, String fromDate, String toDate){
		try {
			DateTime from = DateHelper.convertToDate(fromDate);
			DateTime to = DateHelper.convertToDate(toDate);
			DateTime userProvided = DateHelper.convertToDate(date);
			if(userProvided.isAfter(from) && userProvided.isBefore(to)){
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return Boolean.FALSE;
    }

}
