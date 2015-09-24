package com.kobi.satyabra_reflex;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * References sofar:
 * https://developer.android.com/guide/topics/ui/actionbar.html
 * http://developer.android.com/guide/topics/ui/dialogs.html
 * http://docs.oracle.com/javase/7/docs/api/java/util/Vector.html
 * http://docs.oracle.com/javase/7/docs/api/java/util/Map.html
 * To show back button on action bar, dymmeh's answer:
 * http://stackoverflow.com/questions/14483393/how-do-i-change-the-android-actionbar-title-and-icon
 * http://www.mkyong.com/android/android-imageview-example/
 * retrieve the statistic manager object using Bill Pugh's Initialization-on-demand holder idiom.
 * taken from https://en.wikipedia.org/wiki/Singleton_pattern#Initialization-on-demand_holder_idiom
 */
public class MainMenu extends Activity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadOldStats();

        //hiding status bar https://developer.android.com/training/system-ui/status.html
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // https://developer.android.com/guide/topics/ui/actionbar.html
        actionBar = getActionBar();
        actionBar.setTitle(R.string.app_title);
        actionBar.setSubtitle(R.string.app_subtitle_menu);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void testLayoutChange(View view) {
        setContentView(R.layout.activity_main_menu2);
    }

    public void testLayoutReturn(View view) {
        setContentView(R.layout.activity_main_menu);
    }

    public void startSingleplayer(View view) {
        Intent intent = new Intent(this, SinglePlayer.class);
        startActivity(intent);
    }

    public void startMultiplayer(View view) {
        Intent intent = new Intent(this, MultiPlayer.class);
        startActivity(intent);
    }
    private void loadOldStats() {
        Map<Integer, Integer> oldMap = new HashMap<Integer, Integer>();
        Vector<Integer> oldVector = new Vector<Integer>();
        try {
            FileInputStream fis = openFileInput(StatsManager.FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // DOESNT WORK cuz private constructor! should store the vector and map in gson in one file somehjow?
            StatsManager oldStats = gson.fromJson(in, StatsManager.class);
            oldMap.putAll(oldStats.getBuzzerCountData());
            oldVector.addAll(oldStats.getReactionTimeData());
        } catch(FileNotFoundException e) {
            // nothing to do, just start out with clean new data!
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        StatsManager.getStats().dataClearAndLoad(oldVector, oldMap);
    }
}
