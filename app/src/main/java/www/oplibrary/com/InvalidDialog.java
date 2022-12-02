package www.oplibrary.com;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class InvalidDialog extends DialogFragment {
    // creates the dialog and controls the ok button
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Invalid Username or Password")
                .setTitle("Login Failed")
                .setPositiveButton("OK", (dialog, id) -> {
                });
        return builder.create();
    }
}
