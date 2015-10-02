package com.kobi.satyabra_reflex;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * MultiPlayer class's purpose is to provide the Button functionalities and show the
 * different screens of the MultiPlayer game state.
 * It saves the results to file too after a player's buzzer button was pressed.
 * It also passes on a copy of its StatsManager object to the parent activity upon finishing.
 */
public class MultiPlayer extends FileManager {

    private ActionBar actionBar;
    private Integer playerNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Receive the StatsManager object from the parent activity and replace the local one with it.
        Intent intent = getIntent();
        stats = intent.getParcelableExtra(MainMenu.MESSAGE_STAT);

        // initialize the playerNum with the amount of players gotten from MpHelper.
        playerNums = MpHelper.getInstance().getPlayerNumbers();

        setViewButtons();

        // Hide the status bar of the android's OS.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Change texts in the action bar and enable the back button to the parent activity.
        actionBar = getActionBar();
        actionBar.setTitle(R.string.app_title);
        actionBar.setSubtitle(R.string.app_subtitle_multiplayer);
        actionBar.setHomeButtonEnabled(Boolean.TRUE);
        actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multi_player, menu);
        return true;
    }

    // Exit this activity when the hardware back button was pressed.
    @Override
    public void onBackPressed() {
        finishMultiPlayer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            // Exit this activity when the action bar back button was pressed.
            finishMultiPlayer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //------------------------ Button Methods ------------------------------
    // buttonPx where x is the player, these function increase a button press counter in the
    // StatsManager object and change the view to the result page.
    // resetMp resets the MpHelper-singleton's values and changes the view back to the buttons view.

    public void buttonP1(View view) {
        switch(playerNums) {
            case 2:
                stats.addBuzzerCount(StatsManager.BuzzId.p21);
                break;
            case 3:
                stats.addBuzzerCount(StatsManager.BuzzId.p31);
                break;
            case 4:
                stats.addBuzzerCount(StatsManager.BuzzId.p41);
                break;
        }
        if(MpHelper.getInstance().playerCanPress()) {
            MpHelper.getInstance().setPlayerFirst(1);
            showResults();
        }
    }

    public void buttonP2(View view) {
        switch(playerNums) {
            case 2:
                stats.addBuzzerCount(StatsManager.BuzzId.p22);
                break;
            case 3:
                stats.addBuzzerCount(StatsManager.BuzzId.p32);
                break;
            case 4:
                stats.addBuzzerCount(StatsManager.BuzzId.p42);
                break;
        }
        if(MpHelper.getInstance().playerCanPress()) {
            MpHelper.getInstance().setPlayerFirst(2);
            showResults();
        }
    }

    public void buttonP3(View view) {
        switch(playerNums) {
            case 3:
                stats.addBuzzerCount(StatsManager.BuzzId.p33);
                break;
            case 4:
                stats.addBuzzerCount(StatsManager.BuzzId.p43);
                break;
        }
        if(MpHelper.getInstance().playerCanPress()) {
            MpHelper.getInstance().setPlayerFirst(3);
            showResults();
        }
    }

    public void buttonP4(View view) {
        switch(playerNums) {
            case 4:
                stats.addBuzzerCount(StatsManager.BuzzId.p44);
        }
        if(MpHelper.getInstance().playerCanPress()) {
            MpHelper.getInstance().setPlayerFirst(4);
            showResults();
        }
    }

    public void resetMp(View view) {
        MpHelper.getInstance().playerCanPressReset();
        setViewButtons();
    }

    //------------------------ Helper Methods ------------------------------

    // Changes the view to the correct view depending on the amount of players.
    private void setViewButtons() {
        switch (playerNums) {
            case 2:
                setContentView(R.layout.activity_multi_player);
                break;
            case 3:
                setContentView(R.layout.activity_multi_player3);
                break;
            case 4:
                setContentView(R.layout.activity_multi_player4);
                break;
            default:
                // should not happen
                break;
        }
    }

    // Saves the StatsManager object into file and changes the view to show results of which player pressed first.
    private void showResults() {
        saveStats();
        setContentView(R.layout.activity_multi_player_results);
        TextView resultTxt = (TextView) findViewById(R.id.textMpResult);
        switch(MpHelper.getInstance().getPlayerFirst()) {
            case 1:
                resultTxt.setText(R.string.mp_p1);
                break;
            case 2:
                resultTxt.setText(R.string.mp_p2);
                break;
            case 3:
                resultTxt.setText(R.string.mp_p3);
                break;
            case 4:
                resultTxt.setText(R.string.mp_p4);
                break;
            default:
                // should never happen
                resultTxt.setText("Something went very wrong... Try again.");
                break;
        }
    }

    // Saves the StatsManager object into file, resets the MpHelper for future use, and passes the
    // StatsManager object back to the parent activity before exiting.
    private void finishMultiPlayer() {
        saveStats();
        MpHelper.getInstance().playerCanPressReset();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainMenu.MESSAGE_STAT, stats);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}
