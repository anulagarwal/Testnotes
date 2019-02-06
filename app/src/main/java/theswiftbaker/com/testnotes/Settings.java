package theswiftbaker.com.testnotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.nio.charset.Charset;

public class Settings extends PreferenceActivity {
    Button save;
    Button back;

    Spinner txtColor;
    Spinner bgColor;
    SeekBar txtSize;
    SeekBar rightOffset;
    SeekBar topOffset;
    TextView sizeOffsetText;
    TextView rightOffText;
    TextView topOffText;

    Preference saveBtn;
    Preference txtSizePref,leftPrefOff,topPreOff,backSpinner;
    ListPreference txtCol, bgCol;

    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.settings);
        addPreferencesFromResource(R.xml.pref_xml);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        final Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Settings");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Settings Loaded");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Param.CONTENT_TYPE,bundle);
       // final SeekBarPreference sbp = (SeekBarPreference) findPreference("someKey");

        saveBtn = (Preference)getPreferenceManager().findPreference("SaveData");

        txtCol = (ListPreference)getPreferenceManager().findPreference("txtCol");
        bgCol = (ListPreference)getPreferenceManager().findPreference("bgColor");

        leftPrefOff = getPreferenceManager().findPreference("leftOffset");
        topPreOff = getPreferenceManager().findPreference("topOffset");
        txtSizePref = getPreferenceManager().findPreference("txtSize");


        SharedPreferences prefs = getSharedPreferences("textSettings", MODE_PRIVATE);

        txtCol.setSummary(prefs.getString("textColor","Black"));
        bgCol.setSummary(prefs.getString("bgColor","White"));


/*        rightOffset.setMax(width-400);
        topOffset.setMax(height-400);
        txtSize.setProgress(textSiz);
        rightOffset.setProgress(rightOff);
        topOffset.setProgress(topOff);

        sizeOffsetText.setText( Integer.toString(textSiz));
        rightOffText.setText( Integer.toString(rightOff));
        topOffText.setText(Integer.toString(topOff));


*/


        txtCol.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                txtCol.setSummary(newValue.toString());
                txtCol.setDefaultValue(newValue);
                bundle.putString("TextColorSelected", newValue.toString());
                mFirebaseAnalytics.logEvent("TextColorChanged",bundle);
                return false;
            }
        });
        bgCol.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                bgCol.setSummary(newValue.toString());
                bgCol.setDefaultValue(newValue);
                bundle.putString("BGColorChanged", newValue.toString());
                mFirebaseAnalytics.logEvent("BGColorChanged",bundle);
                return false;
            }
        });



    saveBtn.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {

            bundle.putString("SettingsSave", "Settings Saved");
            mFirebaseAnalytics.logEvent("SettingsSave",bundle);
            saveData();


            return false;
        }
    });


/*        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveData();

            }
        });

*/


/*
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors_list,R.layout.spinner_itemm);
        adapter.setDropDownViewResource(R.layout.spinner_itemm);

        textSpinner.setAdapter(adapter);
        textSpinner.setOnItemSelectedListener(this);
        bgSpinner.setAdapter(adapter);
        bgSpinner.setOnItemSelectedListener(this);

        txtColor.setSelection(textColor);
        bgSpinner.setSelection(bgColor);


        txtSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                //Update textview value
                sizeOffsetText.setText( Integer.toString(progress));
            }
            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
        rightOffset.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                //Update textview value
                rightOffText.setText( Integer.toString(progress));
            }
            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        topOffset.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                //Update textview value
                topOffText.setText( Integer.toString(progress));
            }
            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
        */
    }

    public void saveData(){
        //int txtCol = txtColor.getSelectedItemPosition();
        CharSequence txtCo= txtCol.getValue();
        CharSequence bgCo = bgCol.getValue();
     //   CharSequence txtSiz = txtSizePref.get();
       // CharSequence bgCol = bgColor.getSelectedItemPosition();
        //CharSequence rightOff = rightOffset.getProgress();
        //CharSequence topOff = topOffset.getProgress();
        SharedPreferences.Editor editor = getSharedPreferences("textSettings", MODE_PRIVATE).edit();
        //editor.putInt("textColor", txtCol);
        //editor.putInt("txtSize", txtSiz);

        //editor.putInt("rightOff",rightOff);
        editor.putString("bgColor",bgCol.getSummary().toString());
        editor.putString("textColor",txtCol.getSummary().toString());
//        editor.putInt("topOff",topOff);
        editor.apply();

        Intent i =  new Intent(Settings.this, MainActivity.class);
        startActivity(i);
    }


}
