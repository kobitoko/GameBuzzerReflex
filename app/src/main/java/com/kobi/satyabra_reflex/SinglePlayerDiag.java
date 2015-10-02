package com.kobi.satyabra_reflex;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


/**
 * SinglePlayerDiag class displays the instruction for singleplayer mode.
 * Class template taken from Androids http://developer.android.com/guide/topics/ui/dialogs.html
 */
public class SinglePlayerDiag extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // When the user enters this mode they prompted with information that says they how to supposed to react quickly.
        builder.setTitle(R.string.diag_title_instructions);
        builder.setMessage(R.string.diag_instructions);

        builder.setPositiveButton(R.string.diag_okai, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the positive button event back to the host activity
                mListener.onDialogPositiveClick(SinglePlayerDiag.this);
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }
}
