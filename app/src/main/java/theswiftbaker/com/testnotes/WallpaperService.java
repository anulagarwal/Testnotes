package theswiftbaker.com.testnotes;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class WallpaperService extends Service {
    private WallpaperReceiver wr = null;

    public WallpaperService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();

        // Add network connectivity change action.
        intentFilter.addAction(Intent.ACTION_WALLPAPER_CHANGED);



        // Set broadcast receiver priority.
        intentFilter.setPriority(100);

        // Create a network change broadcast receiver.
        wr = new WallpaperReceiver();
        // Register the broadcast receiver with the intent filter object.
        registerReceiver(wr, intentFilter);
       // Toast.makeText(getApplicationContext(),"SErvice Started", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Unregister screenOnOffReceiver when destroy.
        if(wr!=null)
        {
           // Toast.makeText(getApplicationContext(),"SErvice dest", Toast.LENGTH_SHORT).show();

            unregisterReceiver(wr);
        }
    }
}
