package com.kobi.satyabra_reflex;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class SinglePlayer extends Activity {

    private final static Integer DURATION_RESULT_DISPLAY = new Integer(2500);
    private ActionBar actionBar;
    private StatsManager stats;
    private StopWatch t;
    private Runnable restart, start;
    private Handler handler;
    private Boolean canPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        stats = intent.getParcelableExtra(MainMenu.MESSAGE_STAT);

        handler = new Handler();
        canPress = Boolean.FALSE;
        t = new StopWatch();

        setContentView(R.layout.activity_single_player);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = getActionBar();
        actionBar.setTitle(R.string.app_title);
        actionBar.setSubtitle(R.string.app_subtitle_singleplayer);
        actionBar.setHomeButtonEnabled(Boolean.TRUE);
        actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);

        restart = new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_single_player);
                startReflex();
            }
        };

        start = new Runnable() {
            @Override
            public void run() {
                ImageView imgGo = (ImageView) findViewById(R.id.imageGo);
                imgGo.setImageResource(R.drawable.go);
                canPress = Boolean.TRUE;
                t.start();
            }
        };

        startReflex();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_player, menu);
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
            return true;
        } else if (id == android.R.id.home) {
            saveStats();
            handler.removeCallbacks(restart);
            handler.removeCallbacks(start);
            Intent resultIntent = new Intent();
            resultIntent.putExtra(MainMenu.MESSAGE_STAT, stats);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(restart);
        handler.removeCallbacks(start);
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_single_player);
        ImageView imgGo = (ImageView) findViewById(R.id.imageGo);
        imgGo.setImageResource(R.drawable.wait);
        canPress = Boolean.FALSE;
        startReflex();
    }

    public void buttonBigRed(View view) {
        Long timeTaken = t.stop();
        //Log.w("---_AAAA_ timer", " timeTaken in ms " + timeTaken.toString());
        if(canPress) {
            stats.addReactionTime(timeTaken.intValue());
            saveStats();
        }
        showResults();
    }

    private void startReflex() {
        Random rnd = new Random();
        Integer randomTime = 10 + rnd.nextInt(1990);
        handler.postDelayed(start, randomTime);
    }

    private void showResults() {
        // remove callback and reset the images.
        handler.removeCallbacks(start);
        ImageView imgGo = (ImageView) findViewById(R.id.imageGo);
        imgGo.setImageResource(R.drawable.wait);

        // Actually show results.
        setContentView(R.layout.activity_single_player_results);
        TextView resultTxt = (TextView) findViewById(R.id.textSpResult);
        if(canPress)
            resultTxt.setText("Your reaction time was: " + stats.getReactionTime(0).toString() + "ms.");
        else
            resultTxt.setText("Sorry you pressed too early!");

        // Taken from ρяσѕρєя K's answer http://stackoverflow.com/questions/14295150/update-activity-constantly
        handler.postDelayed(restart, DURATION_RESULT_DISPLAY);

        // Reset the reaction state.
        canPress = Boolean.FALSE;
    }

    private void saveStats() {
        try {
            FileOutputStream fos = openFileOutput(StatsManager.FILENAME, MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(stats, writer);
            writer.flush();
            fos.close();
        } catch(FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

}
