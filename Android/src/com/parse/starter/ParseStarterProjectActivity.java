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
import android.view.View;

import com.parse.ParseException;
import com.parse.ParseUser;

public class ParseStarterProjectActivity extends Activity {

	static Context context = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ParseStarterProjectActivity.context = this.getApplicationContext();
		setContentView(R.layout.main);
	}

	@Override
	protected void onStart() {
		super.onStart();

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			try {
				ParseUser.logIn("theuser", "thepwd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
		}

	}

	public void onStartClick(View v) {
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				Intent intent = new Intent(context, AlarmReceiverActivity.class);
				context.startActivity(intent);
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 30);
		Intent intent = new Intent(this, AlarmReceiverActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
	}

	public void onStopClick(View v) {

	}

	public void onLogoutClick(View v) {

	}

}