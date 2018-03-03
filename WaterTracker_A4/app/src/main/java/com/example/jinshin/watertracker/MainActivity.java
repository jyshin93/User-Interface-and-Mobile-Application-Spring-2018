package com.example.jinshin.watertracker;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.itangqi.waveloadingview.WaveLoadingView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Animation alpha;
    public static final String PREFS_NAME = "MyPrefsFile";
    float amount;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    LinearLayout mug, cup, bottle;
    TextView plus, minus, screen;
    EditText input_text;
    Toast toast;

    ImageView cup_image_layout_1, cup_image_layout_2;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    Date date;

    String open_date;

    WaveLoadingView wave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences(PREFS_NAME, 0);

        //receive amount based on current date
        open_date = getDate();
        amount = settings.getFloat(open_date, 0);

        //setup animation
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        //setup Toast
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);


        screen = (TextView) findViewById(R.id.screen);
        screen.setText("You drank " + amount + "/64 oz of water today!");

        cup = (LinearLayout) findViewById(R.id.cup_layout);
        cup.setOnClickListener(this);
        mug = (LinearLayout) findViewById(R.id.mug_layout);
        mug.setOnClickListener(this);
        bottle = (LinearLayout) findViewById(R.id.bottle_layout);
        bottle.setOnClickListener(this);

        plus = (TextView) findViewById(R.id.plus);
        plus.setOnClickListener(this);
        minus = (TextView) findViewById(R.id.minus);
        minus.setOnClickListener(this);

        //call image layout
        cup_image_layout_2 = (ImageView) findViewById(R.id.cup_image_layout_2);
        wave = (WaveLoadingView) findViewById(R.id.cup_image_layout_1);
        fillup();

        input_text = (EditText) findViewById(R.id.input_text);

    }

    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        if (open_date.equals(getDate())) {
            editor = settings.edit();
            editor.putFloat(open_date, amount);

            // Commit the edits!
            editor.commit();
        }
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cup_layout:
                v.startAnimation(alpha);
                input_text.setText("8");
//                screen.setText("You drank " + Html.fromHtml(amount + "<font color='#0000FF'></font> ")  + "/64 oz of water today!");
                break;
            case R.id.mug_layout:
                v.startAnimation(alpha);
                input_text.setText("12");
//                screen.setText("You drank " + amount + "/64 oz of water today!");
                break;
            case R.id.bottle_layout:
                v.startAnimation(alpha);
                input_text.setText("17");
//                screen.setText("You drank " + amount + "/64 oz of water today!");
                break;

            case R.id.plus:
                String plus_input = input_text.getText().toString();

                if (isNumber(plus_input)) {
                    float temp = Float.parseFloat(plus_input);
                    amount += temp;
                    if (amount > 64) {
                        toast.makeText(MainActivity.this, "You have completed goal today!!!", toast.LENGTH_SHORT).show();
                        screen.setText("You drank " + amount + "/64 oz of water today!");
                        fillup();
                    } else {
                        screen.setText("You drank " + amount + "/64 oz of water today!");
                        toast.makeText(MainActivity.this, "Added " + temp + " now", toast.LENGTH_SHORT).show();
                        input_text.setText("");
                        //change bottle image
                        fillup();
                    }
                } else {
                    toast.makeText(MainActivity.this, "Please try again", toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.minus:
                String minus_input = input_text.getText().toString();

                if (isNumber(minus_input)) {
                    float temp = Float.parseFloat(minus_input);

                    if (amount < temp) {
                        toast.makeText(MainActivity.this, "Cannot deduct more than current value", toast.LENGTH_SHORT).show();
                        input_text.setText("");
                    }else {
                        amount -= temp;
                        screen.setText("You drank " + amount + "/64 oz of water today!");
                        toast.makeText(MainActivity.this, "Deducted " + temp + " now", toast.LENGTH_SHORT).show();
                        input_text.setText("");
                        fillup();
                    }

                } else {
                    toast.makeText(MainActivity.this, "Please try again", toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    public boolean isNumber(String input) {
        boolean isValid = false;
        try {
            Float.parseFloat(input);
            isValid = true;
        } catch (NumberFormatException e) {

        }
        return isValid;
    }

    public String getDate() {
        date = Calendar.getInstance().getTime();
        return df.format(date);
    }

    public void fillup() {
        float ratio = amount / 64;
        int progress_value = (int) (ratio * 100);
        if (ratio <= 1 ) {
            wave.setProgressValue(progress_value);
            wave.setBottomTitle(String.format("%d%%", progress_value));
            wave.setCenterTitle("");
            wave.setTopTitle("");
        } else {
            wave.setProgressValue(100);
            wave.setBottomTitle(String.format("%d%%", 100));
            wave.setCenterTitle("");
            wave.setTopTitle("");
        }

    }
}
