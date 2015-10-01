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
import android.widget.Toast;

public class Statistics extends FileManager {

    private ActionBar actionBar;
    private StatsCalculator calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calc = new StatsCalculator();

        Intent intent = getIntent();
        stats = intent.getParcelableExtra(MainMenu.MESSAGE_STAT);

        setContentView(R.layout.activity_statistics);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = getActionBar();
        actionBar.setTitle(R.string.app_title);
        actionBar.setSubtitle(R.string.app_subtitle_stats);
        actionBar.setHomeButtonEnabled(Boolean.TRUE);
        actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);

        TextView text = (TextView) findViewById(R.id.textStatistics);
        text.setText(writeStatistics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finishStats();
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
            finishStats();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buttonEmail(View view) {
        // Taken from http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "Game Buzzer Coach statistics");
        i.putExtra(Intent.EXTRA_TEXT   , writeStatistics());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Statistics.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonClearStats(View view) {
        stats.clearStatistics();
        saveStats();
        TextView text = (TextView) findViewById(R.id.textStatistics);
        text.setText(writeStatistics());
    }

    private void finishStats() {
        saveStats();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainMenu.MESSAGE_STAT, stats);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private String writeStatistics() {
        String text = new String();

        // Minimum
        text += getString(R.string.stat_min) + "\n  ";
        text += getString(R.string.stat_alltimes) + " ";
        text += calc.getFastestReaction(stats.getReactionTimesList()).toString() + "ms\n  ";
        text += getString(R.string.stat_10times) + " ";
        text += calc.getFastestReaction10(stats.getReactionTimesList()).toString() + "ms\n  ";
        text += getString(R.string.stat_100times) + " ";
        text += calc.getFastestReaction100(stats.getReactionTimesList()).toString() + "ms\n\n";

        // Maximum
        text += getString(R.string.stat_max) + "\n  ";
        text += getString(R.string.stat_alltimes) + " ";
        text += calc.getSlowestReaction(stats.getReactionTimesList()).toString() + "ms\n  ";
        text += getString(R.string.stat_10times) + " ";
        text += calc.getSlowestReaction10(stats.getReactionTimesList()).toString() + "ms\n  ";
        text += getString(R.string.stat_100times) + " ";
        text += calc.getSlowestReaction100(stats.getReactionTimesList()).toString() + "ms\n\n";

        // Average
        text += getString(R.string.stat_avg) + "\n  ";
        text += getString(R.string.stat_alltimes) + " ";
        text += calc.getAverageReaction(stats.getReactionTimesList()).toString() + "ms\n  ";
        text += getString(R.string.stat_10times) + " ";
        text += calc.getAverageReaction10(stats.getReactionTimesList()).toString() + "ms\n  ";
        text += getString(R.string.stat_100times) + " ";
        text += calc.getAverageReaction100(stats.getReactionTimesList()).toString() + "ms\n\n";

        // Median
        text += getString(R.string.stat_median) + "\n  ";
        text += getString(R.string.stat_alltimes) + " ";
        text += calc.getMedianReaction(stats.getReactionTimesList()).toString() + "ms\n  ";
        text += getString(R.string.stat_10times) + " ";
        text += calc.getMedianReaction10(stats.getReactionTimesList()).toString() + "ms\n  ";
        text += getString(R.string.stat_100times) + " ";
        text += calc.getMedianReaction100(stats.getReactionTimesList()).toString() + "ms\n\n";

        // Multiplayer Buzzer
        text += getString(R.string.stat_buzzer_counts) + "\n  ";
        text += getString(R.string.stat_2pmode) + "\n      ";
        text += getString(R.string.stat_1p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p21).toString() + "\n      ";
        text += getString(R.string.stat_2p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p22).toString() + "\n  ";
        text += getString(R.string.stat_3pmode) + "\n      ";
        text += getString(R.string.stat_1p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p31).toString() + "\n      ";
        text += getString(R.string.stat_2p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p32).toString() + "\n      ";
        text += getString(R.string.stat_3p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p33).toString() + "\n  ";
        text += getString(R.string.stat_4pmode) + "\n      ";
        text += getString(R.string.stat_1p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p41).toString() + "\n      ";
        text += getString(R.string.stat_2p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p42).toString() + "\n      ";
        text += getString(R.string.stat_3p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p43).toString() + "\n      ";
        text += getString(R.string.stat_4p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p44).toString() + "\n";
        return text;
    }

}
