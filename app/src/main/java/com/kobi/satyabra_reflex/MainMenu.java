package com.kobi.satyabra_reflex;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * References sofar:
 * https://developer.android.com/guide/topics/ui/actionbar.html
 * http://developer.android.com/guide/topics/ui/dialogs.html
 * http://docs.oracle.com/javase/7/docs/api/java/util/Vector.html
 * http://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html
 * To show back button on action bar, dymmeh's answer:
 * http://stackoverflow.com/questions/14483393/how-do-i-change-the-android-actionbar-title-and-icon
 * http://www.mkyong.com/android/android-imageview-example/
 * http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
 * http://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
 * http://developer.android.com/reference/android/os/Parcelable.html
 * http://stackoverflow.com/questions/10757598/what-classloader-to-use-with-parcel-readhashmap
 * http://developer.android.com/reference/android/os/SystemClock.html
 * http://stackoverflow.com/questions/14295150/update-activity-constantly
 * http://developer.android.com/reference/android/os/Handler.html
 * Cmput 301 Labs for Gson saving.
 */
public class MainMenu extends Activity implements SinglePlayerDiag.NoticeDialogListener {

    private ActionBar actionBar;
    public final static String MESSAGE_STAT = new String("com.kobi.satyabra_reflex.MESSAGE_STAT");
    public final static String DIAG_INSTRUCT = new String("InstructionDialogFragment");
    public final static String DIAG_MP_PLAYERS = new String("MultiplayerNumbersDialogFragment");
    public final static Integer RESULT_STAT = new Integer(42);
    private StatsManager stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stats = new StatsManager();
        loadStats();

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

    @Override
    //Taken from Reto Meier's and jimmithy's answer
    //from http://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_STAT && resultCode == Activity.RESULT_OK) {
            stats = data.getParcelableExtra(MESSAGE_STAT);
        }
    }

    // Dialog -------------------------------------------

    public void showInstructionDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new SinglePlayerDiag();
        dialog.show(getFragmentManager(), DIAG_INSTRUCT);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        if(dialog.getTag() == DIAG_INSTRUCT) {
            Intent intent = new Intent(this, SinglePlayer.class);
            intent.putExtra(MESSAGE_STAT, stats);
            startActivityForResult(intent, RESULT_STAT);
        } else if (dialog.getTag() == DIAG_MP_PLAYERS) {
            Intent intent = new Intent(this, MultiPlayer.class);
            intent.putExtra(MESSAGE_STAT, stats);
            startActivityForResult(intent, RESULT_STAT);
        }
    }

    // Buttons -------------------------------------------

    public void startSingleplayer(View view) {
        showInstructionDialog();
    }

    public void startMultiplayer(View view) {
        // TEST======================
        //stats.addBuzzerCount(StatsManager.BuzzId[2]);

    }

    public void startStatistics(View view) {

        //this is not stats real content, just for test
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

    // utils -------------------------------------------

    private void loadStats() {
        try {
            FileInputStream fis = openFileInput(StatsManager.FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            stats = gson.fromJson(in, StatsManager.class);
            fis.close();
            in.close();
        } catch(FileNotFoundException e) {
            // nothing to do
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
