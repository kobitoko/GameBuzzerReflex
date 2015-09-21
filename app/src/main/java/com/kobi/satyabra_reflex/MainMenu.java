package com.kobi.satyabra_reflex;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

/**
 * https://developer.android.com/guide/topics/ui/actionbar.html
 * http://developer.android.com/guide/topics/data/data-storage.html#pref
 * http://developer.android.com/guide/topics/ui/dialogs.html
 * http://docs.oracle.com/javase/7/docs/api/java/util/Vector.html
 * http://docs.oracle.com/javase/7/docs/api/java/util/Map.html
 * To show back button on action bar, dymmeh's answer:
 * http://stackoverflow.com/questions/14483393/how-do-i-change-the-android-actionbar-title-and-icon
 */
public class MainMenu extends Activity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    public void startSingleplayer(View view) {
        actionBar.setHomeButtonEnabled(Boolean.TRUE);
        actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);
        setContentView(R.layout.activity_main_menu2);
    }

    public void startMultiplayer(View view) {
        actionBar.setHomeButtonEnabled(Boolean.FALSE);
        actionBar.setDisplayHomeAsUpEnabled(Boolean.FALSE);

        setContentView(R.layout.activity_main_menu);
    }

}
