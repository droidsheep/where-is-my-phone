package com.parse.starter;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class AlarmReceiverActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.
		
				ParseObject position = new ParseObject("PositionObject");
				position.put("lat", location.getLatitude());
				position.put("long", location.getLongitude());
				position.put("User", ParseUser.getCurrentUser());
				position.setACL(ParseUser.getCurrentUser().getACL());

		// Create an offset from the current time in which the alarm will go
		// off.
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 5);

		// Create a new PendingIntent and add it to the AlarmManager
		Intent intent = new Intent(this, AlarmReceiverActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
	}

}