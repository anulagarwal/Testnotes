package theswiftbaker.com.testnotes;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    EditText EditText1;

    SeekBar txtSize, topOffset, leftOffset;
    TextView leftOffText, topOffText, txtSizeText;
    public boolean hasChanged;
    private FirebaseAnalytics mFirebaseAnalytics;
    CardView cv;
    CheckBox cb;
    RadioGroup rg;
    RadioButton rb1,rb2;
    private BroadcastReceiver chargerReceiver;
    ProgressDialog progress;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button timePickerBtn;
    EditText txtDate, txtTime;
    InterstitialAd mInterstitialAd;
     AdView mAdView;
     Calendar cal;
    SharedPreferences prefs ;
BroadcastReceiver br;

EditText[] et;
Switch switch1;
boolean isOptionsDisplayed;

    @Override
    @TargetApi(21)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         prefs = getSharedPreferences("textSettings", Context.MODE_PRIVATE);


br = null;
br = new AlarmReceiver();
((AlarmReceiver) br).setMainActivityHandler(this);
IntentFilter iff = new IntentFilter("android.provider.AlarmClock");
        registerReceiver(br,iff);
        cal = Calendar.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  txtSize = (SeekBar) findViewById(R.id.txtSize);
      //  topOffset = (SeekBar) findViewById(R.id.topOffset);
     //   leftOffset = (SeekBar) findViewById(R.id.leftOffset);
     //   txtSizeText = (TextView) findViewById(R.id.txtSizeText);
     //   topOffText = (TextView) findViewById(R.id.topOffText);
     //   leftOffText = (TextView) findViewById(R.id.leftOffText);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        switch1 = (Switch) findViewById(R.id.switch1);

// Obtain the FirebaseAnalytics instance.

        hasChanged = false;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(MainActivity.this);
        final Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Main");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "MainScreen Loaded");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        MobileAds.initialize(this, "ca-app-pub-1149253882244477~3819663696");

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
//        mAdView.setAdSize(AdSize.BANNER);
 //       mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView.loadAd(adRequest);

 //       Toast.makeText(this, "EActivity Started", Toast.LENGTH_LONG).show();
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713

       // mInterstitialAd = new InterstitialAd(this);
      //  mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544~3347511713");
      //  mInterstitialAd.loadAd(new AdRequest.Builder().build());



        chargerReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO: Awesome things
                SharedPreferences sp = getSharedPreferences("textSettings", Context.MODE_PRIVATE);
                SharedPreferences.Editor edito = getSharedPreferences("textSettings", Context.MODE_PRIVATE).edit();
                if(!sp.getBoolean("hasWallpaperSet",false) ){

                    resetApp();

                }
                else{

                    edito.putBoolean("hasWallpaperSet",false);

                    edito.commit();

                }
            }
        };

        registerReceiver(
                chargerReceiver,
                new IntentFilter(Intent.ACTION_WALLPAPER_CHANGED)
        );


        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            //TODO
        }

        EditText1.setX((float)(prefs.getInt("leftOff", 100)));


   //     txtSizeText.setText(Integer.toString(prefs.getInt("txtSize", 100)));
        EditText1.setTextSize((float)prefs.getInt("txtSize", 100));
        topOffText.setY((float)(prefs.getInt("topOff", 100)));

        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;

        int width = displayMetrics.widthPixels;

     /*   txtSize.setMax(200);
        txtSize.setProgress(prefs.getInt("txtSize", 100));

        topOffset.setMax(height);
        topOffset.setProgress(prefs.getInt("topOff", 100));
        leftOffset.setMax(width);
        leftOffset.setProgress(prefs.getInt("leftOff", 100));
        txtSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Update textview value
                txtSizeText.setText(Integer.toString(progress));
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

                bundle.putInt("ChangeTextSize", txtSize.getProgress());
                mFirebaseAnalytics.logEvent("MainSettings", bundle);
            }
        });
        leftOffset.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Update textview value
                leftOffText.setText(Integer.toString(progress));
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

                bundle.putInt("LeftDisplacement", leftOffset.getProgress());
                mFirebaseAnalytics.logEvent("MainSettings", bundle);
            }
        });

        topOffset.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Update textview value
                topOffText.setText(Integer.toString(progress));
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                bundle.putInt("TopDisplacement", topOffset.getProgress());
                mFirebaseAnalytics.logEvent("MainSettings", bundle);
            }
        });
*/

        EditText1 = (EditText) findViewById(R.id.editText1);

        final ImageButton fab = (ImageButton) findViewById(R.id.checkBtn);
        switch1.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        isOptionsDisplayed = true;

     //   mInterstitialAd.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                SharedPreferences.Editor editor = getSharedPreferences("textSettings", Context.MODE_PRIVATE).edit();
                editor.putInt("txtSize", Math.round(EditText1.getTextSize()));
                editor.putInt("leftOff",(Math.round(EditText1.getX())));
                editor.putInt("topOff", (Math.round(EditText1.getY())));
                editor.putBoolean("hasWallpaperSet", true);
                bundle.putString("SetReminder", "Reminder set");
                mFirebaseAnalytics.logEvent("MainSettings", bundle);
             /*   if(cb.isChecked()) {
                    Intent intent=new Intent(MainActivity.this,AlarmReceiver.class);
                    PendingIntent mAlarmSender=PendingIntent.getBroadcast(MainActivity.this,23454546, intent,0);
                    AlarmManager alm=(AlarmManager)getSystemService(ALARM_SERVICE);

                    alm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),mAlarmSender);
                    editor.putString("textToWallpaper",  EditText1.getText().toString());

                }
                else{
                    Save("NoteText1.txt",String.valueOf(EditText1.getText().toString()));


                }*/
                Save("NoteText1.txt",String.valueOf(EditText1.getText().toString()));
                editor.commit();

            }
        });





        EditText1.setText(Open("NoteText1.txt"));



        cv = (CardView)findViewById(R.id.cardView);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText1.requestFocus();
                int pos = EditText1.getText().toString().trim().length();
                EditText1.setSelection(pos);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(EditText1, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    bundle.putString("ResetButton", "Reset Button");
                    mFirebaseAnalytics.logEvent("MainSettings", bundle);
                    clearText();

                }
            }
        });

        cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(isOptionsDisplayed){

                    switch1.setVisibility(View.INVISIBLE);
                    fab.setVisibility(View.INVISIBLE);
                    isOptionsDisplayed = false;
                }
                else{
                    switch1.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    isOptionsDisplayed = true;
                }

                return false;
            }
        });


EditText1.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(!isOptionsDisplayed) {
            EditText1.setX(x);
            EditText1.setY(y);
        }
        return false;

    }

});
    }

    public void clearText() {
        EditText1.setText("");


        //    Save("NoteText1.txt");
        resetToWallpaper();

    }

    final static float STEP = 200;
    TextView mytv;
    float mRatio = 1.0f;
    int mBaseDist;
    float mBaseRatio;
    float fontsize = 13;
    float x,y;
    public boolean onTouchEvent(MotionEvent event) {

        if(!isOptionsDisplayed) {
            if (event.getPointerCount() == 2) {

                int action = event.getAction();
                int pureaction = action & MotionEvent.ACTION_MASK;
                if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                    mBaseDist = getDistance(event);
                    mBaseRatio = mRatio;
                } else {
                    float delta = (getDistance(event) - mBaseDist) / STEP;
                    float multi = (float) Math.pow(2, delta);
                    mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
                    EditText1.setTextSize(mRatio + 13);
                }
            } else {
                x = event.getX();
                y = event.getY();

            }
        }
        return true;
    }

    int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }

    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
public void setWallpaper(Context context){

Save("NoteText1.txt",context.getSharedPreferences("textSettings",MODE_PRIVATE).getString("textToWallpaper",""));

}
    private void resetToWallpaper() {

        String root = getFilesDir().toString();
        //Environment.getRootDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        String originalImage = "/OriginalImage.png";
        File originalWallpaper = new File(myDir, originalImage);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        Bitmap bitmap = BitmapFactory.decodeFile(root + originalImage, options);
        if(bitmap!=null)
        try {
            WallpaperManager.getInstance(MainActivity.this).setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception5: " + e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(chargerReceiver);
    }
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
    private void saveImage(Bitmap finalBitmap, String image_name) {

        String root = getFilesDir().toString();
        //Environment.getRootDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();

        String originalImage = "OriginalImage.png";

        File originalWallpaper = new File(myDir, originalImage);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        Bitmap origPaper = ((BitmapDrawable) wallpaperManager.getDrawable()).getBitmap();
        try {
            WallpaperManager.getInstance(MainActivity.this).setBitmap(finalBitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception6: " + e.toString(), Toast.LENGTH_LONG).show();

        }
        if (originalWallpaper.exists()) {


        } else {


            try {
                originalWallpaper.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileOutputStream out = new FileOutputStream(originalWallpaper);
                out.write(getBytesFromBitmap(origPaper));
                //origPaper.compress(Bitmap.CompressFormat.PNG, 90, out);

                out.flush();
                out.close();

            } catch (Exception e) {
                Toast.makeText(this, "Exception1: " + e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
//updateTemporaryBG();

    }



    public void resetApp() {

        String root = getFilesDir().toString();
        //Environment.getRootDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        // String fname = "Image-" + image_name+ ".png";
        String originalImage = "/OriginalImage.png";
        //   File file = new File(myDir, fname);
        File originalWallpaper = new File(myDir, originalImage);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
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
                Toast.makeText(this, "Exception7: " + e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }


    }

    public String Open(String fileName) {
        String content = "";
        if (FileExists(fileName)) {
            try {
                InputStream in = openFileInput(fileName);


                if (in != null) {
                    InputStreamReader tmp = new InputStreamReader(in);
                    BufferedReader reader = new BufferedReader(tmp);
                    String str;
                    StringBuilder buf = new StringBuilder();
                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n");
                    }
                    in.close();
                    content = buf.toString();
                }
            } catch (java.io.FileNotFoundException e) {
            } catch (Throwable t) {
                Toast.makeText(this, "Exception2: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return content;
    }

    public void Convert() {
        SharedPreferences prefsa = getSharedPreferences("textSettings", Context.MODE_PRIVATE);
        if(prefsa.getBoolean("FirstTimeOpen",true)){

            SharedPreferences.Editor edit = getSharedPreferences("textSettings", Context.MODE_PRIVATE).edit();
            edit.putBoolean("FirstTimeOpen",false);
            edit.commit();
            Toast.makeText(this,"ffiiii",Toast.LENGTH_LONG).show();
            updateTemporaryBG();
        }
        String text = EditText1.getText().toString();
        int width = EditText1.getMeasuredWidth();
text = text.trim();
        //background color red
        int color = Color.BLACK;

        Bitmap img;
        if(text== "" || text == null || text.length()==0) {

            resetToWallpaper();

        }
        else{
            img = drawTextOverWallpaper(text, width, color);

            //  iv.setImageBitmap(img);
            saveImage(img, "Wallpape");
updateTemporaryBG();

        }
        //background transparent

        // int colorT=Color.TRANSPARENT;
        // Bitmap img1=drawText(text,width,colorT);
        //img2.setImageBitmap(img1);
    }
    public void updateTemporaryBG(){
        String root = getFilesDir().toString();

        File myDir = new File(root);
        myDir.mkdirs();

        String originalImage = "/tempImage.png";

        File originalWallpaper2 = new File(myDir, originalImage);
        WallpaperManager wallpaperManager2 = WallpaperManager.getInstance(this);

        Bitmap origPaper2 =((BitmapDrawable) wallpaperManager2.getDrawable()).getBitmap();
        if(originalWallpaper2.exists()){


            originalWallpaper2.delete();

        }

        try {
            originalWallpaper2.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream out = new FileOutputStream(originalWallpaper2);
           // origPaper2.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.write(getBytesFromBitmap(origPaper2));

            out.flush();
            out.close();
           // Toast.makeText(this, "Updatee Set!", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "Exception1: " + e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    public void createEditText(){


    }

    public Bitmap retrieveTempBG(){
        String root = getFilesDir().toString();

        File myDir = new File(root);
        myDir.mkdirs();

        String originalImage = "/tempImage.png";


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        Bitmap origPaper1 = BitmapFactory.decodeFile(root+ originalImage, options);
        return  origPaper1;
    }


    public Bitmap drawTextOverWallpaper(String text, int textWidth, int color) {

        int txtCol =Color.parseColor("#f7f1e3");
        int bgCol = Color.parseColor("#1e272e");
        int txtSize;
        String bgColor;
        int rightOff;
        int topOff;
        SharedPreferences prefs = getSharedPreferences("textSettings", Context.MODE_PRIVATE);
        String txtColor = prefs.getString("textColor", "Black");
        bgColor = prefs.getString("bgColor", "Red");//"No name defined" is the default value.
        txtSize = prefs.getInt("txtSize", 120); //0 is the default value.
        rightOff = prefs.getInt("leftOff", 0);
        topOff = prefs.getInt("topOff", 0);
        switch (txtColor) {
            case "Red":
                txtCol = Color.parseColor("#eb2f06");

                break;
            case "Blue":
                txtCol =Color.parseColor("#1e3799");
                break;
            case "Green":
                txtCol = Color.GREEN;
                break;

            case "Yellow":
                txtCol = Color.parseColor("#ffdd59");
                break;

            case "White":
                txtCol = Color.parseColor("#f7f1e3");
                break;

            case "Black":
                txtCol =  Color.parseColor("#1e272e");
                break;
        }

        switch (bgColor) {
            case "Red":

                bgCol = Color.parseColor("#eb2f06");
                break;
            case "Blue":
                bgCol =  Color.parseColor("#1e3799");
                break;
            case "Green":
                bgCol = Color.GREEN;
                break;

            case "Yellow":
                bgCol= Color.parseColor("#ffdd59");
                break;

            case "White":
                bgCol =Color.parseColor("#f7f1e3");
                break;

            case "Black":
                bgCol =  Color.parseColor("#1e272e");
                break;
        }

        Toast.makeText(getApplicationContext(),""+ textWidth,Toast.LENGTH_SHORT).show();



        // Get text dimensions
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(txtCol);
        textPaint.setTextSize(mRatio+13);
        textPaint.bgColor = bgCol;
        StaticLayout mTextLayout = new StaticLayout(text, textPaint, textWidth+32, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);


        String root = getFilesDir().toString();
        //Environment.getRootDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();

        String originalImage = "/OriginalImage.png";

        File originalWallpaper = new File(myDir, originalImage);
        //WallpaperManager wallpaperManager1 = WallpaperManager.getInstance(MainActivity.this);
        WallpaperManager wallpaperManager1;

        //Bitmap origPaper1 =((BitmapDrawable) wallpaperManager1.getDrawable()).getBitmap();
        Bitmap origPaper1;

        if (!originalWallpaper.exists()) {

            wallpaperManager1 = WallpaperManager.getInstance(MainActivity.this);

            origPaper1 = ((BitmapDrawable) wallpaperManager1.getDrawable()).getBitmap();


        } else {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            origPaper1 = BitmapFactory.decodeFile(root + originalImage, options);


        }




        //Create bitmap and canvas to draw to
        Bitmap a = Bitmap.createBitmap(origPaper1);
        Bitmap b = a.copy(Bitmap.Config.ARGB_8888, true);

        Canvas c = new Canvas(b);
        //Draw text
        c.save();
        //c.translate(rightOff, ((height/2)-400)+topOff);
        c.translate(x, y);


        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        p.setStyle(Paint.Style.FILL);
        p.setColor(bgCol);


        c.drawRect(0, 0, mTextLayout.getWidth(), mTextLayout.getHeight(), p);
        mTextLayout.draw(c);

        c.restore();
        hasChanged = true;


//        Toast.makeText(this, "Reminder Set!", Toast.LENGTH_LONG).show();

        return b;
    }



    public void Save(String fileName, String text) {
        try {
            OutputStreamWriter out =

                    new OutputStreamWriter(openFileOutput(fileName, 0));
            out.write(text);
            out.close();
            Convert();
          //  Toast.makeText(this, "Reminder set", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception3: " + t.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public boolean FileExists(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(this, Settings.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
