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

import java.util.Random;

/**
 * SinglePlayer class's purpose is to provide the Button functionalities and show the
 * different screens of the SinglePlayer game state.
 * It saves the results to file after the user presses the button if it was legal to press it.
 * It also passes on a copy of its StatsManager object to the parent activity upon finishing.
 */
public class SinglePlayer extends FileManager {

    private final static Integer DURATION_RESULT_DISPLAY = new Integer(2500);
    private ActionBar actionBar;
    private StopWatch t;
    private Runnable restart, start;
    private Handler handler;
    private Boolean canPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Receive the StatsManager object from the parent activity and replace the local one with it.
        Intent intent = getIntent();
        stats = intent.getParcelableExtra(MainMenu.MESSAGE_STAT);

        // initialize local class variables.
        handler = new Handler();
        canPress = Boolean.FALSE;
        t = new StopWatch();

        setContentView(R.layout.activity_single_player);

        // Hide the status bar of the android's OS.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Change texts in the action bar and enable the back button to the parent activity.
        actionBar = getActionBar();
        actionBar.setTitle(R.string.app_title);
        actionBar.setSubtitle(R.string.app_subtitle_singleplayer);
        actionBar.setHomeButtonEnabled(Boolean.TRUE);
        actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);

        // a handler callback to exit results and restart the game.
        restart = new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_single_player);
                startReflex();
            }
        };

        // a handler callback to allow button press.
        start = new Runnable() {
            @Override
            public void run() {
                ImageView imgGo = (ImageView) findViewById(R.id.imageGo);
                imgGo.setImageResource(R.drawable.go);
                // Make button pressing legal
                canPress = Boolean.TRUE;
                // Start the timer
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

    // Exit this activity when the hardware back button was pressed.
    @Override
    public void onBackPressed() {
        finishSinglePlayer();
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
            // Exit this activity when the action bar back button was pressed.
            finishSinglePlayer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Disable the handler callbacks when the app was paused
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(restart);
        handler.removeCallbacks(start);
    }

    // Just restarts the game when resuming the app.
    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_single_player);
        ImageView imgGo = (ImageView) findViewById(R.id.imageGo);
        imgGo.setImageResource(R.drawable.wait);
        canPress = Boolean.FALSE;
        startReflex();
    }

    // Stops timer and records the time taken, and save it to file, and show results
    public void buttonBigRed(View view) {
        Long timeTaken = t.stop();
        if(canPress) {
            stats.addReactionTime(timeTaken.intValue());
            saveStats();
        }
        showResults();
    }

    // Call the allow legal button press function with a delay of randomly 10ms - 2000ms.
    private void startReflex() {
        Random rnd = new Random();
        Integer randomTime = 10 + rnd.nextInt(1990);
        handler.postDelayed(start, randomTime);
    }

    // Resets the game state and change view to the results. Delay calls the restart function.
    private void showResults() {
        // remove callback and reset the images.
        handler.removeCallbacks(start);
        ImageView imgGo = (ImageView) findViewById(R.id.imageGo);
        imgGo.setImageResource(R.drawable.wait);

        // Switch view and actually show results.
        setContentView(R.layout.activity_single_player_results);
        TextView resultTxt = (TextView) findViewById(R.id.textSpResult);
        TextView valueTxt = (TextView) findViewById(R.id.textSpResultValue);
        // If it is legal to press button, show results, else just complain.
        if(canPress) {
            resultTxt.setText(R.string.sp_results);
            valueTxt.setText(stats.getReactionTime(0).toString() + "ms");
        } else {
            resultTxt.setText(R.string.sp_too_early);
            valueTxt.setText("");
        }

        // Taken from ρяσѕρєя K's answer http://stackoverflow.com/questions/14295150/update-activity-constantly
        handler.postDelayed(restart, DURATION_RESULT_DISPLAY);

        // Reset the reaction state.
        canPress = Boolean.FALSE;
    }

    private void finishSinglePlayer() {
        saveStats();
        handler.removeCallbacks(restart);
        handler.removeCallbacks(start);
        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainMenu.MESSAGE_STAT, stats);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}
