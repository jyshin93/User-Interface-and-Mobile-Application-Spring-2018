package com.example.jinshin.watertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jinshin on 05/03/2018.
 */

public class Notification extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener{

    Button save, cancel;

    Switch email_switch, hourly_switch, target_switch;
    TextView email;
    Toast toast;
    Intent home;

    Spinner from_spinner, to_spinner, interval_spinner, target_time_spinner, target_amount_spinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);

        save = (Button) findViewById(R.id.save_button);
        cancel = (Button) findViewById(R.id.cancel_button);
        email_switch = (Switch) findViewById(R.id.email_switch);
        hourly_switch = (Switch) findViewById(R.id.hourly_switch);
        target_switch = (Switch) findViewById(R.id.target_switch);

        //set up spinner
        from_spinner = (Spinner) findViewById(R.id.from_spinner);
        to_spinner = (Spinner) findViewById(R.id.to_spinner);
        interval_spinner = (Spinner) findViewById(R.id.interval_spinner);
        target_time_spinner = (Spinner) findViewById(R.id.target_time_spinner);
        target_amount_spinner = (Spinner) findViewById(R.id.target_amount_spinner);

        //set up adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Hours, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> interval_adapter = ArrayAdapter.createFromResource(this,
                R.array.Interval, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> amount_adapter = ArrayAdapter.createFromResource(this,
                R.array.Amount, android.R.layout.simple_spinner_item);
        from_spinner.setAdapter(adapter);
        to_spinner.setAdapter(adapter);
        interval_spinner.setAdapter(interval_adapter);
        target_time_spinner.setAdapter(adapter);
        target_amount_spinner.setAdapter(amount_adapter);

        //setup Toast
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        email_switch.setOnCheckedChangeListener(this);
        hourly_switch.setOnCheckedChangeListener(this);
        target_switch.setOnCheckedChangeListener(this);

        //listener to spinner
        from_spinner.setOnItemSelectedListener(this);
        to_spinner.setOnItemSelectedListener(this);
        interval_spinner.setOnItemSelectedListener(this);
        target_time_spinner.setOnItemSelectedListener(this);
        target_amount_spinner.setOnItemSelectedListener(this);


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
            case R.id.target_switch:
                if (b) {
                    toast.makeText(Notification.this, "Target Notification On", toast.LENGTH_SHORT).show();
                } else {
                    toast.makeText(Notification.this, "Target Notification Off", toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
