package com.example.jinshin.watertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jinshin on 05/03/2018.
 */

public class Notification extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    Button save, cancel;

    Switch email_switch, hourly_switch;
    TextView email;
    Toast toast;

    Intent home;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);

        save = (Button) findViewById(R.id.save_button);
        cancel = (Button) findViewById(R.id.cancel_button);
        email_switch = (Switch) findViewById(R.id.email_switch);
        hourly_switch = (Switch) findViewById(R.id.hourly_switch);

        //setup Toast
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        email_switch.setOnCheckedChangeListener(this);
        hourly_switch.setOnCheckedChangeListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                this.finish();
                break;

            case R.id.cancel_button:
                home = new Intent(Notification.this, MainActivity.class);
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {

            case R.id.email_switch:
                if (b) {
                    toast.makeText(Notification.this, "Daily Email Summary On", toast.LENGTH_SHORT).show();
                } else {
                    toast.makeText(Notification.this, "Daily Email Summary Off", toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.hourly_switch:
                if (b) {
                    toast.makeText(Notification.this, "Hourly Notification On", toast.LENGTH_SHORT).show();
                } else {
                    toast.makeText(Notification.this, "Hourly Notification Off", toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }
}
