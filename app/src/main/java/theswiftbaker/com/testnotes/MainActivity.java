package theswiftbaker.com.testnotes;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;

public class MainActivity extends AppCompatActivity  {
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
Button savebtn;
     Calendar cal;
    SharedPreferences prefs ;
BroadcastReceiver br;
WallpaperReceiver wr;
EditText[] et;
Switch switch1;
Switch timer;
Spinner timerMinute;
boolean isOptionsDisplayed;
boolean isCardDisplayed;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private RewardedAd rewardedAd;
     Bundle bundle;
Timer timerT;
    public void showAlertDialogButtonClicked() {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Settings");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.settingslayout, null);
        builder.setView(customLayout);

        txtSize = customLayout.findViewById(R.id.txtSize);
        topOffset = customLayout.findViewById(R.id.topOffset);
        leftOffset = customLayout.findViewById(R.id.leftOffset);
        txtSizeText = customLayout.findViewById(R.id.txtSizeText2);
        topOffText = customLayout.findViewById(R.id.topOffText);
        leftOffText = customLayout.findViewById(R.id.leftOffText);
        timer = customLayout.findViewById(R.id.timer1);
        timerMinute = customLayout.findViewById(R.id.spinner);

// Obtain the FirebaseAnalytics instance.

        hasChanged = false;
        timerMinute.setEnabled(false);

        timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                timerMinute.setEnabled(isChecked);
            }
        });

        txtSizeText.setText(Integer.toString(prefs.getInt("txtSize", 100)));
        // EditText1.setTextSize((float)prefs.getInt("txtSize", 100));
        //  EditText1.setY((float)(prefs.getInt("topOff", 100)));
        leftOffText.setText(Integer.toString(prefs.getInt("leftOff", 100)));
        topOffText.setText(Integer.toString(prefs.getInt("topOff", 100)));

        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;

        int width = displayMetrics.widthPixels;

        txtSize.setMax(200);
        txtSize.setProgress(prefs.getInt("txtSize", 100));
        txtSizeText.setText(" "+prefs.getInt("txtSize", 100));

        topOffset.setMax(height);
        topOffset.setProgress(prefs.getInt("topOff", 100));
        topOffText.setText(" "+prefs.getInt("topOff", 100));

        leftOffset.setMax(width);
        leftOffset.setProgress(prefs.getInt("leftOff", 100));
        leftOffText.setText(" "+prefs.getInt("leftOff", 100));


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

        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
               //EditText editText = customLayout.findViewById(R.id.editText);
                //sendDialogDataToActivity(editText.getText().toString());
                SharedPreferences.Editor editor = getSharedPreferences("textSettings", Context.MODE_PRIVATE).edit();
                editor.putInt("txtSize", Math.round(txtSize.getProgress()));
                editor.putInt("leftOff", (Math.round(leftOffset.getProgress())));
                editor.putInt("topOff", (Math.round(topOffset.getProgress())));
                editor.putBoolean("hasWallpaperSet", true);
                bundle.putString("SetReminder", "Reminder set");
                mFirebaseAnalytics.logEvent("MainSettings", bundle);





                if (mInterstitialAd.isLoaded() && prefs.getInt("AdShown", 0) == 2) {

                    mInterstitialAd.show();

                    editor.putInt("AdShown", 0);

                    mFirebaseAnalytics.logEvent("AdShown", bundle);

                }
                editor.commit();
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // do something with the data coming from the AlertDialog
    private void sendDialogDataToActivity(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    private static boolean compare(Bitmap b1, Bitmap b2) {
        if (b1.getWidth() == b2.getWidth() && b1.getHeight() == b2.getHeight()) {
            int[] pixels1 = new int[b1.getWidth() * b1.getHeight()];
            int[] pixels2 = new int[b2.getWidth() * b2.getHeight()];
            b1.getPixels(pixels1, 0, b1.getWidth(), 0, 0, b1.getWidth(), b1.getHeight());
            b2.getPixels(pixels2, 0, b2.getWidth(), 0, 0, b2.getWidth(), b2.getHeight());
            return Arrays.equals(pixels1, pixels2);
        } else {
            return false;
        }
    }

    public void clearText() {
        EditText1.setText("");


        //    Save("NoteText1.txt");
        resetToWallpaper();

    }



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         prefs = getSharedPreferences("textSettings", Context.MODE_PRIVATE);
isCardDisplayed = true;
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        String admobID;
        savebtn = findViewById(R.id.savebtn);
        MobileAds.initialize(this, "ca-app-pub-1149253882244477~3819663696");
savebtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    showAlertDialogButtonClicked();}
});


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1149253882244477/8215996284");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        cal = Calendar.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(MainActivity.this);
       bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Main");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "MainScreen Loaded");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        switch1 = findViewById(R.id.timer1);




        final ImageButton fab = findViewById(R.id.checkBtn);

        switch1.setChecked(true);

        EditText1 = findViewById(R.id.editText1);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

     //   mInterstitialAd.show();
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // timerT.schedule();
                if(1==2){
                    Toast.makeText(getApplicationContext(), "Reminding after : "+timerMinute.getSelectedItem().toString() + "Minute", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            SharedPreferences.Editor editorw = getSharedPreferences("textSettings", Context.MODE_PRIVATE).edit();
                            editorw.putInt("AdShown", prefs.getInt("AdShown", 0) + 1);
                            editorw.commit();
                            //   updateTemporaryBG();
                            //   Bitmap bitmap2 = BitmapFactory.decodeFile(root + originalImage, options);
                            if (retrieveTempBG() == null) {


                            } else if (compare(retrieveTempBG(), ((BitmapDrawable) WallpaperManager.getInstance(MainActivity.this).getDrawable()).getBitmap())) {


                            } else {

                                resetApp();

                            }





                            Save("NoteText1.txt", String.valueOf(EditText1.getText().toString()));
                            Toast.makeText(getApplicationContext(),"Reminder Set In Wallpaper!", Toast.LENGTH_LONG).show();
                        }
                    }, Long.parseLong(timerMinute.getSelectedItem().toString()) * 60000);
                }
                else{
                    SharedPreferences.Editor editorw = getSharedPreferences("textSettings", Context.MODE_PRIVATE).edit();
                    editorw.putInt("AdShown", prefs.getInt("AdShown", 0) + 1);

                    editorw.commit();
                    //   updateTemporaryBG();
                    //   Bitmap bitmap2 = BitmapFactory.decodeFile(root + originalImage, options);
                    if (retrieveTempBG() == null) {


                    } else if (compare(retrieveTempBG(), ((BitmapDrawable) WallpaperManager.getInstance(MainActivity.this).getDrawable()).getBitmap())) {



                    } else {

                        resetApp();

                    }





                    Save("NoteText1.txt", String.valueOf(EditText1.getText().toString()));
                    Toast.makeText(getApplicationContext(),"Reminder Set In Wallpaper!", Toast.LENGTH_LONG).show();

                }

                if (mInterstitialAd.isLoaded() && prefs.getInt("AdShown", 0) == 2) {
                   /* Activity activityContext = MainActivity.this;
                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                        }

                        @Override
                        public void onRewardedAdClosed() {
                            // Ad closed.

                            rewardedAd = createAndLoadRewardedAd();
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem reward) {
                            // User earned reward.
                        }

                        @Override
                        public void onRewardedAdFailedToShow(int errorCode) {
                            // Ad failed to display
                        }
                    };
                    rewardedAd.show(activityContext, adCallback);
                    mInterstitialAd.show();
                    SharedPreferences.Editor editor = getSharedPreferences("textSettings", Context.MODE_PRIVATE).edit();
                    editor.putInt("AdShown", 0);

                    mFirebaseAnalytics.logEvent("AdShown", bundle);
                    editor.commit();*/
                }


                }

        });


        EditText1.setText(Open("NoteText1.txt"));


        cv = findViewById(R.id.cardView);
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
                    isCardDisplayed = false;
                    Toast.makeText(getApplicationContext(),"Reminder Removed!", Toast.LENGTH_LONG).show();

                }
                else{
                    isCardDisplayed = true;
                }
            }
        });


    }

    public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(this,
                "ca-app-pub-1149253882244477/6755276853");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void resetToWallpaper() {

        String root = getFilesDir().toString();
        //Environment.getRootDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        String originalImage = "/OriginalImage.png";
        File originalWallpaper = new File(myDir, originalImage);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        Bitmap   bitmap  = BitmapFactory.decodeFile(root+originalImage,options);

        // Bitmap bitmap = BitmapFactory.decodeFile(root + originalImage, options);

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


updateTemporaryBG();

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

    public void setWallpaper(Context context) {

        Save("NoteText1.txt", context.getSharedPreferences("textSettings", MODE_PRIVATE).getString("textToWallpaper", ""));

    }

    public void Convert() {
        SharedPreferences prefsa = getSharedPreferences("textSettings", Context.MODE_PRIVATE);
        if(prefsa.getBoolean("FirstTimeOpen",true)){

            SharedPreferences.Editor edit = getSharedPreferences("textSettings", Context.MODE_PRIVATE).edit();
            edit.putBoolean("FirstTimeOpen",false);
            edit.commit();

            updateTemporaryBG();
        }
        String text = EditText1.getText().toString();
        int width = EditText1.getMeasuredWidth();
text = text.trim();
        //background color red
        int color = Color.BLACK;

        Bitmap img;
        if (text == "" || text == null || text.length() == 0) {

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
            origPaper2.compress(Bitmap.CompressFormat.PNG, 100, out);
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public Bitmap retrieveTempBG(){
        String root = getFilesDir().toString();

        File myDir = new File(root);
        myDir.mkdirs();

        String originalImage = "/tempImage.png";


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;

        Bitmap   origPaper1  = BitmapFactory.decodeFile(root+originalImage,options);


return origPaper1;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
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





        // Get text dimensions
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(txtCol);
        textPaint.setTextSize(txtSize);
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
               origPaper1  = BitmapFactory.decodeFile(root+originalImage,options);


        }




        //Create bitmap and canvas to draw to
        Bitmap a = Bitmap.createBitmap(origPaper1);
        Bitmap b = a.copy(Bitmap.Config.ARGB_8888, true);

        Canvas c = new Canvas(b);
        //Draw text
        c.save();
        //c.translate(rightOff, ((height/2)-400)+topOff);
        c.translate(leftOffset.getProgress(), topOffset.getProgress());


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
