package com.kobi.satyabra_reflex;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MultiPlayer extends FileManager {

    private ActionBar actionBar;
    private Integer playerNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        stats = intent.getParcelableExtra(MainMenu.MESSAGE_STAT);

        playerNums = MpHelper.getInstance().getPlayerNumbers();
        setViewButtons();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
            MpHelper.getInstance().playerCanPressReset();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(MainMenu.MESSAGE_STAT, stats);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Button functions ---------------------------

    public void buttonP1(View view) {
        switch(playerNums) {
            case 2:
                stats.addBuzzerCount(StatsManager.BuzzId[0]);
                break;
            case 3:
                stats.addBuzzerCount(StatsManager.BuzzId[2]);
                break;
            case 4:
                stats.addBuzzerCount(StatsManager.BuzzId[5]);
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
                stats.addBuzzerCount(StatsManager.BuzzId[1]);
                break;
            case 3:
                stats.addBuzzerCount(StatsManager.BuzzId[3]);
                break;
            case 4:
                stats.addBuzzerCount(StatsManager.BuzzId[6]);
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
                stats.addBuzzerCount(StatsManager.BuzzId[4]);
                break;
            case 4:
                stats.addBuzzerCount(StatsManager.BuzzId[7]);
                break;
        }
        if(MpHelper.getInstance().playerCanPress()) {
            MpHelper.getInstance().setPlayerFirst(3);
            showResults();
        }
    }

    public void buttonP4(View view) {
        switch(playerNums) {
            case 2:
                stats.addBuzzerCount(StatsManager.BuzzId[8]);
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

    //-------
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

}
