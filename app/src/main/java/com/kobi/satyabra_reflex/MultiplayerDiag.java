package com.kobi.satyabra_reflex;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * MultiplayerDiag class gets the input from the user and stores the result in to the MpHelper singleton.
 * Class template taken from Androids http://developer.android.com/guide/topics/ui/dialogs.html
 */
public class MultiplayerDiag extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.diag_mp_players);
        builder.setItems(R.array.diag_mp, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // For a list choice: the which has the index position of selected choice.
                // 3 indices, and at least 2 players and at most 4, thus index + 2 = player numbers.
                MpHelper.getInstance().setPlayerNumbers(new Integer(which + 2));
                mListener.onDialogPositiveClick(MultiplayerDiag.this);
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface MpDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    MpDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (MpDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement MpDialogListener");
        }
    }
}
