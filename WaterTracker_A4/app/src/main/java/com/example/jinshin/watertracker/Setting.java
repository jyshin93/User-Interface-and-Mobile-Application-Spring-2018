package com.example.jinshin.watertracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jinshin on 05/03/2018.
 */

public class Setting extends AppCompatActivity {

    EditText totalEdit, timeEdit;
    String total;

    android.support.v7.widget.Toolbar action_bar;
    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    RadioButton ounces, milli;
    RadioGroup rg;

    Toast toast;

    String base_measurement;
    float goal;
    int end_time;


    /*
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        action_bar = (android.support.v7.widget.Toolbar) findViewById(R.id.setting_bar);
        setSupportActionBar(action_bar);
    }
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        action_bar = (android.support.v7.widget.Toolbar) findViewById(R.id.setting_bar);
        setSupportActionBar(action_bar);

        totalEdit = (EditText) findViewById(R.id.editText);
        timeEdit = (EditText) findViewById(R.id.resetText);


        //set up sharedPreferences
        settings = getSharedPreferences(PREFS_NAME, 0);
        goal = settings.getFloat("Goal", 0);
        base_measurement = settings.getString("Measure", "oz");
        end_time = settings.getInt("End_Time", 24);
        //set up editText with values
        totalEdit.setText(String.valueOf(goal));
        timeEdit.setText(String.valueOf(end_time));


        rg = (RadioGroup) findViewById(R.id.radioButtonGroup);
        ounces = (RadioButton) findViewById(R.id.ounces);
        milli = (RadioButton) findViewById(R.id.ml);

        if (base_measurement.equals("oz")) {
            ounces.setChecked(true);
        } else {
            milli.setChecked(true);
        }


        //set extedit action listener
        totalEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    goal = Float.parseFloat(totalEdit.getText().toString());
                    InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    editor = settings.edit();
                    editor.putFloat("Goal", goal);
                    editor.commit();
                    return true;
                }
                return false;
            }
        });

        //set extedit action listener
        timeEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    end_time = Integer.parseInt(timeEdit.getText().toString());
                    Log.e("Value", end_time + "");
                    if (end_time > 24.0) {
                        toast.makeText(Setting.this, "Select between 1 to 24", toast.LENGTH_SHORT).show();
                        timeEdit.setText("");
                        return true;
                    } else if (end_time <= 0) {
                        toast.makeText(Setting.this, "Select between 1 to 24", toast.LENGTH_SHORT).show();
                        timeEdit.setText("");
                        return true;
                    } else {
                        InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                        editor = settings.edit();
                        editor.putInt("End_Time", end_time);
                        editor.commit();
                        return true;
                    }
                }
                return false;
            }
        });

        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        int checked = rg.getCheckedRadioButtonId();

        switch (checked) {
            case R.id.ounces:
                base_measurement = "oz";
                editor = settings.edit();
                editor.putString("Measure", base_measurement);
                editor.commit();
                toast.makeText(Setting.this, "ounces checked", toast.LENGTH_SHORT).show();
                break;
            case R.id.ml:
                base_measurement = "ml";
                editor = settings.edit();
                editor.putString("Measure", base_measurement);
                editor.commit();
                toast.makeText(Setting.this, "ml checked", toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        int id = item.getItemId();
        if (id == R.id.close) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
