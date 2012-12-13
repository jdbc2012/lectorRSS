package es.jdbc.rss;

import java.net.MalformedURLException;
import java.net.URL;

import es.jdbc.rss.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class RSSReaderActivity extends Activity {

	private ProgressDialog spinner;	
	private ListView view;
	
	public ProgressDialog getpDialog() {
		return spinner;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		/*
		// Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        */
		
		URL url;
		try {
			//Read URL from data store (memory, sd, sqllite)
			//url = new URL("http://elmundo.feedsportal.com/elmundo/rss/portada.xml");
			//url = new URL("http://feeds.feedburner.com/androidcentral?format=xml");
			url = new URL("http://meneame.feedsportal.com/rss");
			
			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			String operatorname = tm.getNetworkOperatorName();
			String simoperator = tm.getSimOperatorName();
			String simserialno = tm.getSimSerialNumber();
			//String phonenumber = tm.getLine1Number();
			
			/*
			Toast message = Toast.makeText(getApplicationContext(), operatorname + " - " + simserialno, Toast.LENGTH_LONG);
			message.show();
			*/
			
			ConnectivityManager connMgr = (ConnectivityManager) 
											getSystemService(Context.CONNECTIVITY_SERVICE);
			
			//Network info
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			//Log.i(this.getClass().getName(), "Network connected: " + networkInfo.isConnected());
			
			/*NetworkInfo wifiInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if(wifiInfo!=null)
				Log.i(this.getClass().getName(), "Wifi connected: " + wifiInfo.isConnected());
			
			NetworkInfo ethernetInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
			if (ethernetInfo!=null)
				Log.i(this.getClass().getName(), "Ethernet connected: " + ethernetInfo.isConnected());*/
			
			if (networkInfo!=null && networkInfo.isConnected())
			{
				//Read background
				spinner = new ProgressDialog(this);				
				RSSConnectionTask networkTask = new RSSConnectionTask(this);
				networkTask.execute(url);
			}
			else
			{
				//No connection 
				Toast msg1 = Toast.makeText(getApplicationContext(), "Network is not available", Toast.LENGTH_LONG);
				msg1.show();
								
				this.finish();
			}
								
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.e(this.getClass().getName(), e.getMessage());
		}
		catch(Exception e)
		{
			Log.e(this.getClass().getName(), e.getMessage());
		}
		
		// Binding data
		/*ArrayAdapter adapter = new ArrayAdapter(this, 
												android.R.layout.simple_list_item_1, headlines);
		setListAdapter(adapter);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
}
