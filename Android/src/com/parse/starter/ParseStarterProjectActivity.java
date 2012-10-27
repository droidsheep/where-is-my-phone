package com.parse.starter;

import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;

public class ParseStarterProjectActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			
		} else {
			
		}
		
	}
	
}