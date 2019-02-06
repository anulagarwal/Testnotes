package theswiftbaker.com.testnotes;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WallpaperReceiver extends BroadcastReceiver {

    MainActivity mainActivity;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Wallpaper Changed", Toast.LENGTH_SHORT).show();
        //   resetApp(context);
        mainActivity = new MainActivity();


    }


}