package com.example.jinshin.watertracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;

import me.itangqi.waveloadingview.WaveLoadingView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    Animation alpha;
    public static final String PREFS_NAME = "MyPrefsFile";
    float amount, goal_amount;
    int end_time;

    Setting setting;
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
    String base_measurement;

    WaveLoadingView wave;
    android.support.v7.widget.Toolbar action_bar;

    Intent intent;
    int cup_amount, mug_amount, bottle_amount;
    TextView cup_text, mug_text, bottle_text, measure_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        action_bar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(action_bar);

        //initialize drawer here
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, action_bar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        settings = getSharedPreferences(PREFS_NAME, 0);
        screen = (TextView) findViewById(R.id.screen);
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

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

        cup_text = (TextView) findViewById(R.id.cup_text);
        mug_text = (TextView) findViewById(R.id.mug_text);
        bottle_text = (TextView) findViewById(R.id.bottle_text);
        measure_view = (TextView) findViewById(R.id.measure_view);

        //call image layout
        cup_image_layout_2 = (ImageView) findViewById(R.id.cup_image_layout_2);
        wave = (WaveLoadingView) findViewById(R.id.cup_image_layout_1);

        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        input_text = (EditText) findViewById(R.id.input_text);

        //receive amount based on current date
        open_date = getDate();
//        end_time = settings.getInt("End_Time", 24);
//        try {
//            amount = get_amount(open_date, end_time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        goal_amount = settings.getFloat("Goal", 64);
//        base_measurement = settings.getString("Measure", "oz");
        this.onResume();

        //setup animation


        //setup Toast


        fillup();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        int id = item.getItemId();

        if (id == R.id.settings) {
            intent = new Intent(MainActivity.this, Setting.class);
            startActivity(intent);
            return true;
        } else if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            mDrawerLayout.closeDrawers();
        } else if (id == R.id.history) {
            intent = new Intent(MainActivity.this, History.class);
            startActivity(intent);
            mDrawerLayout.closeDrawers();
        } else if (id == R.id.notification) {
            intent = new Intent(MainActivity.this, Notification.class);
            startActivity(intent);
            mDrawerLayout.closeDrawers();
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        goal_amount = settings.getFloat("Goal", goal_amount);
        end_time = settings.getInt("End_Time", 0);
        String prev_measurement = settings.getString("Prev_Measure", "oz");
        try {
            amount = get_amount(open_date, end_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        base_measurement = settings.getString("Measure", "oz");
        if (!prev_measurement.equals(base_measurement)) {
            if (base_measurement.equals("oz")) {
                //convert amount from milli to oz
                amount = (float) ((int) (amount / 30));
                try {
                    save_amount(open_date, end_time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //convert button amounts
                initialize_amount(base_measurement);
            } else {
                //convert amount from oz to milli
                amount = (float) ((int) (amount * 30));
                try {
                    save_amount(open_date, end_time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //convert button amounts
                initialize_amount(base_measurement);
            }
        } else {
            initialize_amount(base_measurement);
        }
//        Log.e("amount", amount + "");
        screen.setText(String.format("You drank %.1f/%.1f %s of water today!", amount, goal_amount, base_measurement));
        measure_view.setText(base_measurement);
        editor = settings.edit();
        editor.putString("Prev_Measure", base_measurement);
        editor.commit();
        fillup();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            this.save_amount(open_date, end_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();

        try {
            this.save_amount(open_date, end_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cup_layout:
                v.startAnimation(alpha);
                input_text.setText(String.valueOf(cup_amount));
//                screen.setText("You drank " + Html.fromHtml(amount + "<font color='#0000FF'></font> ")  + "/64 oz of water today!");
                break;
            case R.id.mug_layout:
                v.startAnimation(alpha);
                input_text.setText(String.valueOf(mug_amount));
//                screen.setText("You drank " + amount + "/64 oz of water today!");
                break;
            case R.id.bottle_layout:
                v.startAnimation(alpha);
                input_text.setText(String.valueOf(bottle_amount));
//                screen.setText("You drank " + amount + "/64 oz of water today!");
                break;

            case R.id.plus:
                String plus_input = input_text.getText().toString();

                if (isNumber(plus_input)) {
                    float temp = Float.parseFloat(plus_input);
                    amount += temp;
                    if (amount > 64) {
                        toast.makeText(MainActivity.this, "You have completed goal today!!!", toast.LENGTH_SHORT).show();
                        screen.setText(String.format("You drank %.1f/%.1f %s of water today!", amount, goal_amount, base_measurement));
                        input_text.setText("");
                        try {
                            this.save_amount(open_date, end_time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        fillup();
                    } else {
                        screen.setText(String.format("You drank %.1f/%.1f %s of water today!", amount, goal_amount, base_measurement));
                        toast.makeText(MainActivity.this, "Added " + temp + " now", toast.LENGTH_SHORT).show();
                        input_text.setText("");
                        try {
                            this.save_amount(open_date, end_time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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
                        screen.setText(String.format("You drank %.1f/%.1f %s of water today!", amount, goal_amount, base_measurement));
                        toast.makeText(MainActivity.this, "Deducted " + temp + " now", toast.LENGTH_SHORT).show();
                        input_text.setText("");
                        try {
                            this.save_amount(open_date, end_time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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
        float ratio = amount / goal_amount;
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

    public void initialize_amount(String measure) {
        if (measure.equals("oz")) {
            cup_amount = 8;
            mug_amount = 12;
            bottle_amount = 17;
            cup_text.setText(String.format("%d %s", cup_amount, measure));
            mug_text.setText(String.format("%d %s", mug_amount, measure));
            bottle_text.setText(String.format("%d %s", bottle_amount, measure));
        } else {
            cup_amount = 8 * 30;
            mug_amount = 12 * 30;
            bottle_amount = 17 * 30;
            cup_text.setText(String.format("%d %s", cup_amount, measure));
            mug_text.setText(String.format("%d %s", mug_amount, measure));
            bottle_text.setText(String.format("%d %s", bottle_amount, measure));
        }
    }

    public float get_amount(String open_date, int end_time) throws ParseException {
        Calendar now = Calendar.getInstance();
        int cur_hour = now.get(Calendar.HOUR_OF_DAY);
        if (cur_hour < end_time) {
            return settings.getFloat(open_date, 0);
        } else {
            Date cur_date = df.parse(open_date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cur_date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String next_date = df.format(date = calendar.getTime());
            return settings.getFloat(next_date, 0);
        }
    }

    public void save_amount(String open_date, int end_time) throws ParseException {
        Calendar now = Calendar.getInstance();
        int cur_hour = now.get(Calendar.HOUR_OF_DAY);
        if (cur_hour < end_time) {
            editor = settings.edit();
            editor.putFloat(open_date, amount);
            editor.commit();
        } else {
            Date cur_date = df.parse(open_date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cur_date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String next_date = df.format(date = calendar.getTime());
            editor = settings.edit();
            editor.putFloat(next_date, amount);
            editor.commit();
        }
    }


}
