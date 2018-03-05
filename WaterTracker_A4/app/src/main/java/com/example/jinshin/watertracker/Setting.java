package com.example.jinshin.watertracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by jinshin on 05/03/2018.
 */

public class Setting extends AppCompatActivity {

    android.support.v7.widget.Toolbar action_bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        action_bar = (android.support.v7.widget.Toolbar) findViewById(R.id.setting_bar);
        setSupportActionBar(action_bar);



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
