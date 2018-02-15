package Utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalenderUtil {  
	public static List<Date> getDatesBetween( Date startDate, Date endDate) {
			    List<Date> datesInRange = new ArrayList<>();
			    Calendar calendar = new GregorianCalendar();
			    calendar.setTime(startDate);
			     
			    Calendar endCalendar = new GregorianCalendar();
			    endCalendar.setTime(endDate);
			 
			    while (calendar.before(endCalendar)) {
			        Date result = calendar.getTime();
			        datesInRange.add(result);
			        calendar.add(Calendar.DATE, 1);
			    }
			    return datesInRange;
			}
	
	public static Date getdate(int theYear,int theMonth,int theDay)
	{
	Calendar myCal = Calendar.getInstance();
	myCal.set(Calendar.YEAR, theYear);
	myCal.set(Calendar.MONTH, theMonth);
	myCal.set(Calendar.DAY_OF_MONTH, theDay);
	Date theDate = myCal.getTime();
	return theDate;
	}
}
