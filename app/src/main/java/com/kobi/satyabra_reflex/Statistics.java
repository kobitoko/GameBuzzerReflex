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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    private String writeStatistics() {
        String text = new String();

        // Minimum
        text += getString(R.string.stat_min) + "\n  ";
        text += getString(R.string.stat_alltimes) + " ";
        text += stats.getFastestReaction().toString() + "ms\n  ";
        text += getString(R.string.stat_10times) + " ";
        text += stats.getFastestReaction10().toString() + "ms\n  ";
        text += getString(R.string.stat_100times) + " ";
        text += stats.getFastestReaction100().toString() + "ms\n\n";

        // Maximum
        text += getString(R.string.stat_max) + "\n  ";
        text += getString(R.string.stat_alltimes) + " ";
        text += stats.getSlowestReaction().toString() + "ms\n  ";
        text += getString(R.string.stat_10times) + " ";
        text += stats.getSlowestReaction10().toString() + "ms\n  ";
        text += getString(R.string.stat_100times) + " ";
        text += stats.getSlowestReaction100().toString() + "ms\n\n";

        // Average
        text += getString(R.string.stat_avg) + "\n  ";
        text += getString(R.string.stat_alltimes) + " ";
        text += stats.getAverageReaction().toString() + "ms\n  ";
        text += getString(R.string.stat_10times) + " ";
        text += stats.getAverageReaction10().toString() + "ms\n  ";
        text += getString(R.string.stat_100times) + " ";
        text += stats.getAverageReaction100().toString() + "ms\n\n";

        // Median
        text += getString(R.string.stat_median) + "\n  ";
        text += getString(R.string.stat_alltimes) + " ";
        text += stats.getMedianReaction().toString() + "ms\n  ";
        text += getString(R.string.stat_10times) + " ";
        text += stats.getMedianReaction10().toString() + "ms\n  ";
        text += getString(R.string.stat_100times) + " ";
        text += stats.getMedianReaction100().toString() + "ms\n\n";

        // Multiplayer Buzzer
        text += getString(R.string.stat_buzzer_counts) + "\n  ";
        text += getString(R.string.stat_2pmode) + "\n    ";
        text += getString(R.string.stat_1p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p21).toString() + "\n    ";
        text += getString(R.string.stat_2p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p22).toString() + "\n  ";
        text += getString(R.string.stat_3pmode) + "\n    ";
        text += getString(R.string.stat_1p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p31).toString() + "\n    ";
        text += getString(R.string.stat_2p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p32).toString() + "\n    ";
        text += getString(R.string.stat_3p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p33).toString() + "\n  ";
        text += getString(R.string.stat_4pmode) + "\n    ";
        text += getString(R.string.stat_1p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p41).toString() + "\n    ";
        text += getString(R.string.stat_2p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p42).toString() + "\n    ";
        text += getString(R.string.stat_3p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p43).toString() + "\n    ";
        text += getString(R.string.stat_4p) + " ";
        text += stats.getBuzzerCount(StatsManager.BuzzId.p44).toString() + "\n  ";
        return text;
    }

}
