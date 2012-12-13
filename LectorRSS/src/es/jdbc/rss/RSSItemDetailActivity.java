package es.jdbc.rss;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 
 * @author jdbc
 *
 */
public class RSSItemDetailActivity extends Activity{
	
	private ToggleButton playPauseButton;
	private ImageView imageView;
	private TextView titleView;
	private TextView pubDateView;
	private WebView webView;
	private TextView descView;
		
	protected MediaPlayer mplayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.item_detail);
		
		String description = getIntent().getStringExtra(RSSParser.ITEM_DESCRIPTION);
		//loadImageView(description);
		
		//Item title
		String title = getIntent().getStringExtra(RSSParser.ITEM_TITLE);
		titleView = (TextView) findViewById(R.id.item_title);
		titleView.setText(title);	
		
		//Item pubdate
		String pubDate = getIntent().getStringExtra(RSSParser.ITEM_PUBDATE);
		
		pubDateView = (TextView) findViewById(R.id.item_pubdate);
		pubDateView.setText(pubDate);
		
		
		/*descView = (TextView) findViewById(R.id.description);
		descView.setText(description);*/
		
		webView = (WebView) findViewById(R.id.html);
		
		//Webview settings
		WebSettings webSettings = webView.getSettings();
		webSettings.setDefaultTextEncodingName("UTF-8");
		
		/*TextSize textsize = webSettings.getTextSize();
		String prueba = textsize.toString();
		
		webSettings.setTextSize(WebSettings.TextSize.NORMAL);*/
		
		//htmlView.loadData(description, "text/html", "UTF-8");
		webView.loadDataWithBaseURL(null, description, "text/html", "utf-8", null);
		
		//Button play/pause
		final String audioUrl = getIntent().getStringExtra(RSSParser.ITEM_ENCLOSURE_URL);
		
		/*if (audioUrl!=null && !audioUrl.equals(""))
		{
			playPauseButton = (ToggleButton) findViewById(R.id.playPauseButton);
			playPauseButton.setVisibility(ToggleButton.VISIBLE);
			playPauseButton.setOnClickListener(new OnClickListener(){
			
				public void onClick(View v) {
					if (playPauseButton.isChecked())
					{					
						playAndPause(audioUrl);
					}
				}
			});	
		}*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	
	private void playAndPause(String audioUrl)
	{
		//Create an instance of MediaPlayer
		if (mplayer==null){
			mplayer = new MediaPlayer();
			mplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){

				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mplayer.start();
				}
			
			});
		}
		
		try {
			//Check if MediaPlayer object is playing or not
			if (mplayer.isPlaying()) 
			{
				mplayer.pause();
			}
			else
			{
				mplayer.reset();
				mplayer.setDataSource(audioUrl);
				mplayer.prepare();
				mplayer.start();
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void stop()
	{
		if (mplayer!=null)
		{
			mplayer.stop();
		}
	}
	
	/**
	 * 
	 * @param description
	 */
	/*private void loadImageView(String description)
	{
		String srcImg = new RSSParser().getFirstImageUrl(description);
		
		if (srcImg!=null)
		{
			imageView = (ImageView) findViewById(R.id.item_image);
			UriDownloaderTask downloader = new UriDownloaderTask(srcImg, imageView); 
		
			downloader.execute();
		}
		//imageService.loadImage(srcImg, imageView);
	}*/
}
