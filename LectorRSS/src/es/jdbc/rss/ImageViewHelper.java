package es.jdbc.rss;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class ImageViewHelper {

	private static UriDownloaderTask task;
	private static Resources mResources;
    private static DisplayMetrics mMetrics;
	
	private static void prepareResources(final Context context) 
	{	
        if (mMetrics != null) {
            return;
        }
        
        mMetrics = new DisplayMetrics();
        
        final Activity act = (Activity)context;
        act.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        final AssetManager mgr = context.getAssets();
        mResources = new Resources(mgr, mMetrics, context.getResources().getConfiguration());
    }
	
	private static void executeTask(final String srcImg, ImageView imageView) {
        		
		task.execute();
    }
}
