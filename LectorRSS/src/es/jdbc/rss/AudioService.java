package es.jdbc.rss;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.media.MediaPlayer;

/**
 * 
 * @author jdbc
 *
 */
public class AudioService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener
{
	
    private static final String ACTION_PLAY = "es.jdbc.action.PLAY";
    
    protected MediaPlayer mplayer = null;

    /**
     * Called when start command happens.
     * 
     * @param intent
     * @param flags
     * @param startId
     * 
     * @return
     */
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        if (intent.getAction().equals(ACTION_PLAY)) {
            mplayer = new MediaPlayer();
            mplayer.setOnPreparedListener(this);
            mplayer.prepareAsync(); // prepare async to not block main thread
        }
        
		return startId;
    }
    
    /**
     * Called when MediaPlayer is ready
     * 
     * @param player
     */
    public void onPrepared(MediaPlayer player) {
        player.start();
    }
    
    /**
     * Called when an error happens.
     * 
     * @param mp
     * @param what
     * @param extra
     * 
     * @return
     */
    public boolean onError(MediaPlayer mp, int what, int extra) {
		
    	mplayer.reset();
    	mplayer.release();
    	mplayer = null;
        
    	return true;
    }
    
    /**
     * Called when services is destroyed.
     */
    public void onDestroy() {
        if (mplayer != null) 
        	mplayer.release();
    }
    
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
