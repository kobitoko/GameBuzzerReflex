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
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

/**
 * MainMenu class purpose is to provide buttons of the main menu so the user can navigate to
 * the different activities of the app, as well as creating the necessary dialog pop-ups
 * before launching the activity.
 * It also passes on a copy of its StatsManager object to the launching activity.
 */
public class MainMenu extends FileManager implements SinglePlayerDiag.NoticeDialogListener,
        MultiplayerDiag.MpDialogListener {

    private ActionBar actionBar;
    public final static String MESSAGE_STAT = new String("com.kobi.satyabra_reflex.MESSAGE_STAT");
    public final static String DIAG_INSTRUCT = new String("InstructionDialogFragment");
    public final static String DIAG_MP_PLAYERS = new String("MultiplayerNumbersDialogFragment");
    public final static Integer RESULT_STAT = new Integer(42);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stats = new StatsManager();
        loadStats();

        // Hiding status bar, taken from Android Documentation: https://developer.android.com/training/system-ui/status.html
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Change the text of the action bar
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
    // Gets the StatsManager object from the child-activity which then replaces its own local StatsManager object.
    // Taken from Stack Overflow: Reto Meier's and jimmithy's answer at
    // http://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_STAT && resultCode == Activity.RESULT_OK) {
            stats = data.getParcelableExtra(MESSAGE_STAT);
        }
    }

    //------------------------ Button Methods ------------------------------

    public void startSingleplayer(View view) {
        showInstructionDialog();
    }

    public void startMultiplayer(View view) {
        showPlayerAmountAskDialog();
    }

    // Passes on the local StatsManager object to the Statistics activity.
    public void startStatistics(View view) {
        Intent intent = new Intent(this, Statistics.class);
        intent.putExtra(MESSAGE_STAT, stats);
        startActivityForResult(intent, RESULT_STAT);
    }

    //------------------------ Dialog Methods ------------------------------

    // Create an instruction dialog using the SinglePlayerDiag class
    public void showInstructionDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new SinglePlayerDiag();
        dialog.show(getFragmentManager(), DIAG_INSTRUCT);
    }

    // Creates a multiplayer dialog using MuliplayerDiag class
    public void showPlayerAmountAskDialog() {
        DialogFragment dialog = new MultiplayerDiag();
        dialog.show(getFragmentManager(), DIAG_MP_PLAYERS);
    }

    // Receives the result from the dialog and passes on the local StatsManager object to the launching activity.
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button and
        // pass on the local StatsManager object to the launching Activity
        if (dialog.getTag() == DIAG_INSTRUCT) {
            Intent intent = new Intent(this, SinglePlayer.class);
            intent.putExtra(MESSAGE_STAT, stats);
            startActivityForResult(intent, RESULT_STAT);
        } else if (dialog.getTag() == DIAG_MP_PLAYERS) {
            Intent intent = new Intent(this, MultiPlayer.class);
            intent.putExtra(MESSAGE_STAT, stats);
            startActivityForResult(intent, RESULT_STAT);
        }
    }

}
