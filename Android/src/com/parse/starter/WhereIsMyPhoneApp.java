package com.parse.starter;

import android.app.Application;

import com.parse.Parse;

public class WhereIsMyPhoneApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "hCUZFiOue9RLNMOOKuC91YVx7zMCTOwVhVSpm9CG", "DWYvzxkXnbucXAuPV5NId7yPoSqRqkQlTLwNaQzm");
	}
	
}
