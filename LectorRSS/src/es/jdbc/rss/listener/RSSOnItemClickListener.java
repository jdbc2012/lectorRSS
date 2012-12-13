package es.jdbc.rss.listener;

import java.util.HashMap;
import java.util.List;

import es.jdbc.rss.RSSItemDetailActivity;
import es.jdbc.rss.RSSParser;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class RSSOnItemClickListener implements OnItemClickListener{
	
	//Source activity
	protected Activity activity;
		
	/**
	 * Constructor
	 */
	public RSSOnItemClickListener(Activity context, List<HashMap<String, String>> rssList)
	{
		this.activity = context;
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Log.i(getClass().getName(), "Position: " + position);	
		Log.i(getClass().getName(), "Id: " + id);
		
		//Intent in = new Intent(this.activity.getApplicationContext(), RSSItemActivity.class);
		
		@SuppressWarnings("unchecked")
		HashMap<String,String> item = (HashMap<String,String>) parent.getAdapter().getItem(position);
		String audioUrl = item.get(RSSParser.ITEM_ENCLOSURE_URL);
		
		Intent newIntent = new Intent(view.getContext(), RSSItemDetailActivity.class);
		newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		newIntent.putExtra(RSSParser.ITEM_ENCLOSURE_URL, audioUrl);
		newIntent.putExtra(RSSParser.ITEM_TITLE, item.get(RSSParser.ITEM_TITLE));
		newIntent.putExtra(RSSParser.ITEM_PUBDATE, item.get(RSSParser.ITEM_PUBDATE));
		newIntent.putExtra(RSSParser.ITEM_DESCRIPTION, item.get(RSSParser.ITEM_DESCRIPTION));
		this.activity.getApplicationContext().startActivity(newIntent);
		
		
        // passing parameters
        //in.putExtra(TAG_ID, sqlite_id);
	}
	
}
