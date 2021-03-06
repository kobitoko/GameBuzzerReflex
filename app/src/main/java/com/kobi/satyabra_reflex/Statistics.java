/********************************************************************************
 Copyright 2015 Ryan Satyabrata

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 *********************************************************************************/

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

/**
 * Statistics class purpose is to visually show the statistics to the user and provide a way to
 * email the statistics and clear the data.
 */
public class Statistics extends FileManager {

    private ActionBar actionBar;
    private StatsCalculator calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calc = new StatsCalculator();

        // Receive the StatsManager object from the parent activity and replace the local one with it.
        Intent intent = getIntent();
        stats = intent.getParcelableExtra(MainMenu.MESSAGE_STAT);

        setContentView(R.layout.activity_statistics);

        // Hide the status bar of the android's OS.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Change texts in the action bar and enable the back button to the parent activity.
        actionBar = getActionBar();
        actionBar.setTitle(R.string.app_title);
        actionBar.setSubtitle(R.string.app_subtitle_stats);
        actionBar.setHomeButtonEnabled(Boolean.TRUE);
        actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);

        // Write the statistics to the text-view.
        TextView text = (TextView) findViewById(R.id.textStatistics);
        text.setText(writeStatistics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    // Exit this activity when the hardware back button was pressed.
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
            // Exit this activity when the action bar back button was pressed.
            finishStats();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Launches the users email app and puts the statistics in there, ready to be send off.
    public void buttonEmail(View view) {
        // Taken from Stack Overflow: Jeremy Logan's answer at
        // http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application
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

    // clears all the data in StatsManager and saves it to file.
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

    // Writes all the statistics into a String.
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
