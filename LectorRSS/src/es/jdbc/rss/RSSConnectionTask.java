package es.jdbc.rss;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import es.jdbc.rss.R;
import es.jdbc.rss.listener.RSSOnItemClickListener;

/**
 * 
 * @author jdbc
 *
 */
public class RSSConnectionTask extends AsyncTask<URL, Integer, Long>{
	
	public static final String TAG_ID = "id";
	public static final String TAG_TITLE = "title";
	public static final String TAG_LINK = "link";
	
	//ITEM TAGS
	/*public static final String ITEM_TITLE = "title";
	public static final String ITEM_LINK = "link";
	public static final String ITEM_CATEGORY = "category";
	public static final String ITEM_PUBDATE = "pubDate";
	public static final String ITEM_ENCLOSURE = "enclosure";
	
	public static final String ITEM_ENCLOSURE_URL = "enclosure_url";*/
	
	protected Activity context;
	
	protected ProgressDialog dialog;
	
	protected String channelTitle;
	protected List<HashMap<String, String>> rssFeedList;
	
	//Audio files player
	protected MediaPlayer audioPlayer;
	
	public RSSConnectionTask(RSSReaderActivity activity)
	{
		this.context = (Activity) activity;
		
		//this.dialog = activity.getpDialog(); 
	}
		
	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		//LinearLayout spinnerLayout = (LinearLayout) context.getLayoutInflater().inflate(R.id.header_view_layout, null);
		//spinnerLayout.setVisibility(View.VISIBLE);
		
		//listView.addHeaderView(spinnerLayout);
		//ProgressBar spinner = (ProgressBar) context.findViewById(R.id.spinner);
		//spinner.setVisibility(View.VISIBLE);
		
		this.dialog = new ProgressDialog(this.context);
		this.dialog.setMessage(context.getString(R.string.refresh));
		this.dialog.setIndeterminate(false);
		this.dialog.setCancelable(false);
		this.dialog.show();
	}
	
	/**
	 * Executes task in background
	 * 
	 */
	@Override
	protected Long doInBackground(URL... urls) 
	{
		//int count = urls.length;
        long totalSize = 0;
        
        try {
        	RSSChannel channel = new RSSParser(this.context).getChannel(urls[0]);
        	channelTitle = channel.getTitle();
        	rssFeedList = channel.getItemList();
			totalSize = rssFeedList.size();
			//publishProgress(100);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
        return totalSize;
	}
	
	// This is called each time you call publishProgress()
    /*protected void onProgressUpdate(Integer... progress) {
    	Log.d(this.getClass().getName(), "Result: " + progress[0]);
    }*/
	
	 // This is called when doInBackground() is finished
    protected void onPostExecute(Long result) {
    	Log.d(this.getClass().getName(), "Result: " + result);
    	
    	this.dialog.dismiss();
    	
    	if (rssFeedList!=null && !rssFeedList.isEmpty())
    	{	
    		ListAdapter adapter = new SimpleAdapter(this.context,
    												rssFeedList, R.layout.item_row,
                    								new String[] {RSSParser.ITEM_TITLE, RSSParser.ITEM_PUBDATE },
                    								new int[] { R.id.row_title, R.id.row_pubdate });
    		
    		TextView textView = (TextView) this.context.findViewById(R.id.main_title); 
    		textView.setText(channelTitle.toUpperCase());
    		
    		ListView listView = (ListView) this.context.findViewById(R.id.main_list);
    		listView.setAdapter(adapter);
    		
    		listView.setOnItemClickListener(new RSSOnItemClickListener(this.context, rssFeedList));
    	}
    	else
    	{
    		//Empty list
    		Log.w(this.getClass().getName(), "RSS Feed list is empty");
    	}
    	
    	
    }
			
}
