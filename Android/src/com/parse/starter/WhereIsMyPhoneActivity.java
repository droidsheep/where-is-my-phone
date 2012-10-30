package com.parse.starter;

import java.util.Date;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class WhereIsMyPhoneActivity extends Activity {

	static Context context = null;
	static boolean running = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WhereIsMyPhoneActivity.context = this.getApplicationContext();
		setContentView(R.layout.main_window);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (ParseUser.getCurrentUser() == null) {
			showLogin();
		}
	}

	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			store(location);
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	};

	public void store(Location location) {
		Toast.makeText(context, "Got location:", Toast.LENGTH_SHORT).show();
		double lat, lon;
		if (location != null) {
			lat = location.getLatitude();
			lon = location.getLongitude();
			Toast.makeText(context, String.format("Got location: %f:%f", lat, lon), Toast.LENGTH_SHORT).show();
			ParseObject position = new ParseObject("PositionObject");
			position.put("lat", lat);
			position.put("long", lon);
			position.put("User", ParseUser.getCurrentUser());
			position.saveInBackground();
		}
		updateView(location);
	}
	
	public void updateView(Location location) {
		try {
			TextView lblPos = (TextView) findViewById(R.id.lbl_last_pos);
			TextView lblLat = (TextView) findViewById(R.id.lbl_lat);
			TextView lblLon = (TextView) findViewById(R.id.lbl_lon);
			
			lblPos.setText("Last Geoposition: " + new Date(location.getTime()).toGMTString());
			lblLat.setText("Latitude: " + Double.toString(location.getLatitude()));
			lblLon.setText("Longitude: " + Double.toString(location.getLongitude()));
		} catch (Exception exc) {
		}
	}

	public void onStartClick(View v) {
		Log.i("PARSE", "CLICKED");
		Log.i("PARSE", ParseUser.getCurrentUser().getUsername());
		Log.i("PARSE", ParseUser.getCurrentUser().isNew() ? "true" : "false");
		running = true;
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 5, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 5, 0, locationListener);
	}

	public void onStopClick(View v) {
		running = false;
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.removeUpdates(locationListener);
	}

	public void onLogoutClick(View v) {
		running = false;
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.removeUpdates(locationListener);
		ParseUser.logOut();
		showLogin();
	}

	private void showLogin() {
		DialogFragment newFragment = new LoginDialog(context);
		newFragment.show(getFragmentManager(), "login");
		newFragment.setCancelable(false);		
	}
	
}