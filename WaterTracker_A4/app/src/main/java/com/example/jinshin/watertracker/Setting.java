package com.example.jinshin.watertracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.widget.RadioButton;

/**
 * Created by jinshin on 05/03/2018.
 */

public class Setting extends AppCompatActivity {

    int totalInt = 63;
    Button okbutton;
    EditText totalEdit;
    String total;
    MainActivity mainActivity;

    android.support.v7.widget.Toolbar action_bar;
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
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        action_bar = (android.support.v7.widget.Toolbar) findViewById(R.id.setting_bar);
        setSupportActionBar(action_bar);

        okbutton = (Button)findViewById(R.id.button);
        totalEdit   = (EditText)findViewById(R.id.editText);

        mainActivity = new MainActivity();

        okbutton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        total = totalEdit.getText().toString();
                        totalInt = Integer.parseInt(total);
                        mainActivity.setTotal(totalInt);
                        Log.v("EditText", totalEdit.getText().toString());
                    }
                }
        );

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    //ounces
                    break;
            case R.id.radioButton2:
                if (checked)
                    //ml
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

    public int getTotal() {
        return totalInt;
    }
}
