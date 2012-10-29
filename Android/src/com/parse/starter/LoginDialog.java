package com.parse.starter;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

public class LoginDialog extends DialogFragment {

	private Context context;

	public LoginDialog(Context context) {
		this.context = context;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(inflater.inflate(R.layout.layout_dialog, null))
		// Add action buttons
				.setPositiveButton("Login", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, int id) {

						EditText txtUser = (EditText) getView().findViewById(R.id.username);
						EditText txtPass = (EditText) getView().findViewById(R.id.password);

						ParseUser.logInInBackground(txtUser.getText().toString(), txtPass.getText().toString(), new LogInCallback() {
							public void done(ParseUser user, ParseException e) {
								if (user != null) {
									ParseACL.setDefaultACL(user.getACL(), true);
									dialog.dismiss();
								} else {
									Toast.makeText(context, "Log-in failed", Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
				});
		return builder.create();
	}
}
