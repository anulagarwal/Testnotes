package theswiftbaker.com.testnotes;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
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
        String action = intent.getAction();
        if(Intent.ACTION_WALLPAPER_CHANGED.equals(action))
        {
           // Toast.makeText(context, "Wallpaper Changed12", Toast.LENGTH_SHORT).show();


            String root = context.getFilesDir().toString();
            //Environment.getRootDirectory().toString();
            File myDir = new File(root);
            myDir.mkdirs();
            // String fname = "Image-" + image_name+ ".png";
            String originalImage = "/OriginalImage.png";
            //   File file = new File(myDir, fname);

            File originalWallpaper = new File(myDir, originalImage);

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);

            // Drawable wallpaperDrawable = wallpaperManager.getDrawable();

            Bitmap origPaper = ((BitmapDrawable) wallpaperManager.getDrawable()).getBitmap();

            if (originalWallpaper.exists()) {

                originalWallpaper.delete();

                try {

                    originalWallpaper.createNewFile();

                } catch (IOException e) {

                    e.printStackTrace();

                }

                try {
                    FileOutputStream out = new FileOutputStream(originalWallpaper);
                    //  origPaper.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.write(getBytesFromBitmap(origPaper));

                    out.flush();
                    out.close();

                } catch (Exception e) {
                    Toast.makeText(context, "Exception7: " + e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }


        }else if(Intent.ACTION_WALLPAPER_CHANGED.equals(action))
        {
        }

    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        return stream.toByteArray();
    }
}