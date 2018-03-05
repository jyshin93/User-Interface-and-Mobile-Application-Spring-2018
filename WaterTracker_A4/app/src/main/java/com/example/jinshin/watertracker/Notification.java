package com.example.jinshin.watertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by jinshin on 05/03/2018.
 */

public class Notification extends AppCompatActivity implements View.OnClickListener{

    Button save, cancel;

    Intent home;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);

        save = (Button) findViewById(R.id.save_button);
        cancel = (Button) findViewById(R.id.cancel_button);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);


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
}
