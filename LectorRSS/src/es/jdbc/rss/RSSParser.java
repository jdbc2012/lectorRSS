package es.jdbc.rss;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

public class RSSParser {

	private static DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
	
	//CHANNEL TAGS
	public static final String CHANNEL_TITLE = "title";
	
	//ITEM TAGS
	public static final String ITEM_TITLE = "title";
	public static final String ITEM_LINK = "link";
	public static final String ITEM_DESCRIPTION = "description";
	public static final String ITEM_THUMBNAIL = "thumbnail";
	public static final String ITEM_CATEGORY = "category";
	public static final String ITEM_PUBDATE = "pubDate";
	public static final String ITEM_ENCLOSURE = "enclosure";
	public static final String ITEM_ENCLOSURE_URL = "enclosure_url";
	
	protected Context context;

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public RSSParser(Context context){
		
		this.context = context;
	}
	
	/**
     * RSS Parser and reader
     * 
     * @param url
     * @return Number of RSS items
     * 
     * @throws XmlPullParserException
     * @throws IOException
	 * @throws ParseException 
     */
	public RSSChannel getChannel(URL url) throws XmlPullParserException, IOException, ParseException
	{	
		RSSChannel result = new RSSChannel();
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
		XmlPullParser xpp = factory.newPullParser();
			
		xpp.setInput(this.getInputStream(url), "UTF_8");
		
		boolean insideChannel = false;
		boolean insideItem = false;
		
		List<HashMap<String,String>> rssFeedList = new ArrayList<HashMap<String, String>>();
		
		// Returns the type of current event: START_TAG, END_TAG, etc..
		int eventType = xpp.getEventType();
		
		HashMap<String, String> itemData = null;
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG) 
			{	
				if (insideChannel)
				{
					if (xpp.getName().equalsIgnoreCase(CHANNEL_TITLE)){
						result.setTitle(xpp.nextText());
						insideChannel = false;
					}
				}
				else if (insideItem)
				{
					if (itemData==null)
						itemData = new HashMap<String, String>();
					
					parseItemElement(xpp, itemData);
				}
				
				else if (xpp.getName().equalsIgnoreCase("item")) {
					 insideItem = true;					 
		 	    }
				else if (xpp.getName().equalsIgnoreCase("channel")) {
					insideChannel = true;					 
		 	    }
			}
			else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
		 	            insideItem=false;
		 	            
		 	            //Append a new item to list
		 	            rssFeedList.add(itemData);
		 	            itemData = null;
		 	            
		 	}   
		 	 
		 	eventType = xpp.next(); //move to next element
		} //while
		
		result.setItemList(rssFeedList);
				
		return result;
	}
	
	protected void parseItemElement(XmlPullParser xpp, HashMap<String, String> itemData) throws XmlPullParserException, IOException, ParseException
	{
		if (xpp.getName().equalsIgnoreCase(ITEM_TITLE)) {
			
			// adding each child node to HashMap key => value
		 	itemData.put(ITEM_TITLE, xpp.nextText());
	     }
		 else 
			if (xpp.getName().equalsIgnoreCase(ITEM_LINK)) {
				itemData.put(ITEM_LINK, xpp.nextText());
			}
		 else 
			if (xpp.getName().equalsIgnoreCase(ITEM_PUBDATE)){
				String pubDate = xpp.nextText();
				String pubAgo = PubDateHelper.getAgoMessage(context, pubDate);
				itemData.put(ITEM_PUBDATE, pubAgo);
			}
		 else 
			if (xpp.getName().equalsIgnoreCase(ITEM_DESCRIPTION)){
				itemData.put(ITEM_DESCRIPTION, xpp.nextText());
			}
		else
		{
			if (xpp.getName().equalsIgnoreCase(ITEM_ENCLOSURE)){
				String enclosureUrl = xpp.getAttributeValue(null, "url");
				if (!enclosureUrl.equals(""))
					itemData.put(ITEM_ENCLOSURE_URL, enclosureUrl);
			}
		}
	}
	
	/**
	 * Retrieve inputStream from URL object
	 * 
	 * @param url 
	 * @return InputStream object
	 */
	protected InputStream getInputStream(URL url) 
	{
		try 
		{
			URLConnection conn = url.openConnection();
					
			return conn.getInputStream();
		} 
		catch (Exception e) {
			Log.w(this.getClass().getName(), e);
			return null;
	    }
	}
	
	/**
	 * Get first image of description HTML
	 * 
	 * @param html
	 * @return 
	 */
	public String getFirstImageUrl(String html) {
        String result = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(html));
            result = getFirstImageUrl(xpp);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return result;
    }

	
    private String getFirstImageUrl(XmlPullParser xpp)
        throws XmlPullParserException, IOException {
        String result = null;
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG
                && "img".equals(xpp.getName())) {
                result = xpp.getAttributeValue(null, "src");
                break;
            }
            eventType = xpp.next();
        }
        return result;
    }
        
}
