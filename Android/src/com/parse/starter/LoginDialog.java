package com.parse.starter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginDialog extends DialogFragment implements OnClickListener{

	private Context context;
	private Dialog theDialog;

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
		View v = inflater.inflate(R.layout.layout_dialog, null);
		((Button) (v.findViewById(R.id.btnLogin))).setOnClickListener(this);
		builder.setView(v);
		theDialog = builder.create();
		return theDialog;
	}

	
	@Override
	public void onClick(View v) {
		EditText txtUsr = (EditText) theDialog.findViewById(R.id.username);
		EditText txtPwd = (EditText) theDialog.findViewById(R.id.password);
		String usr = "";
		String pwd = "";
		if (txtUsr.getText() != null) {
			usr = txtUsr.getText().toString();
		}
		if (txtPwd.getText() != null) {
			pwd = txtPwd.getText().toString();
		}

		ParseUser.logInInBackground(usr, pwd, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					ParseACL.setDefaultACL(user.getACL(), true);
					theDialog.dismiss();
				} else {
					Toast.makeText(context, "Log-in failed", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
