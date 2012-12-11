package es.jdbc.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class UriDownloaderTask extends AsyncTask<Void, Void, Bitmap>
{ 	
	private final String url;
	private ImageView view;
	
	/**
	 * Constructor
	 * 
	 * @param srcImg
	 * @param imageView
	 */
	public UriDownloaderTask(String srcImg, ImageView imageView)
	{
		this.url = srcImg;
		this.view = imageView;
	}
			
	
	/**
	 * 
	 * @param srcImg
	 * @param imageView
	 */
	public Bitmap loadImage(String srcImg, ImageView imageView)
	{			
		if (srcImg!=null)
		{
			try 
			{
				Log.i(this.getClass().getName(), srcImg);
				
				URLConnection conn = new URL(srcImg).openConnection();
				InputStream inputStream = conn.getInputStream();
				
				Bitmap bmp = BitmapFactory.decodeStream(inputStream);
				//BitmapDrawable drawable;
				//imageView.setImageDrawable(drawable);
				//imageView.setImageBitmap(bmp);
				
				return bmp;
			} 
			catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected Bitmap doInBackground(Void... params) {

		return loadImage(url, view);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	protected void onPostExecute(Bitmap result) {
		
		view.setImageBitmap(result);
	}

}
