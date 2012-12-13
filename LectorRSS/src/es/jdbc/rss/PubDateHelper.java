package es.jdbc.rss;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;

public class PubDateHelper {
		
	private static DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
		
	public static String getAgoMessage(Context context, String pubDate) throws ParseException 
	{
		String result = null;
		Date itemDate = dateFormat.parse(pubDate);
		
		//Compare date with today
		Date today = Calendar.getInstance().getTime();
		
		long diff = today.getTime() - itemDate.getTime();
		long diffSeconds = diff / 1000;
		if (diffSeconds > 0)
		{
			long diffMinutes = diffSeconds / 60;
			if (diffMinutes > 0)
			{
				long diffHours = diffMinutes / 60;
				if (diffHours > 0)
					result = createAgoStr(context, diffHours, R.string.hour);
				else
					result = createAgoStr(context, diffMinutes, R.string.minute);
			}
			else
				result = createAgoStr(context, diffSeconds, R.string.second);
		}       
        
		return result;
	}
	
	public static String createAgoStr(Context context, long diff, int period)
	{				
		if (diff > 0)
		{
			StringBuffer result = new StringBuffer();
			result.append(context.getString(R.string.ago));
			result.append(" ");
			result.append(Long.toString(diff));
			result.append(" ");
			result.append(context.getString(period));
			
			if (diff>1)
				result.append("s");
			
			return result.toString();
		}
		else
			return null;
			
	}
}

