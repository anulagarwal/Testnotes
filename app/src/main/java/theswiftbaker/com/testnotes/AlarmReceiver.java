package theswiftbaker.com.testnotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    MainActivity mainActivity = null;
    void setMainActivityHandler(MainActivity main){
        this.mainActivity=main;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        mainActivity = new MainActivity();

mainActivity.setWallpaper(context);
        Toast.makeText(context,"Alaarrrm", Toast.LENGTH_LONG).show();
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
