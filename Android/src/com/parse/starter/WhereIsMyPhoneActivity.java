package com.parse.starter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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
	protected void onStart() {
		super.onStart();
		ParseUser.logInInBackground("theuser", "thepwd", new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			    	ParseACL.setDefaultACL(user.getACL(), true);
			    	Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show();
			    } else {
			    	Toast.makeText(context, "Log-in failed", Toast.LENGTH_SHORT).show();
			    }
			  }
			});
    	ParseInstallation pi = ParseInstallation.getCurrentInstallation();
    	pi.saveEventually();
	}

	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			store(location);			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {}
	};
	
	public void store(Location location) {
		Toast.makeText(context, "Got location:" , Toast.LENGTH_SHORT).show();
		double lat, lon;
		if (location!= null) {
			lat = location.getLatitude();
			lon = location.getLongitude();
			Toast.makeText(context, String.format("Got location: %f:%f", lat, lon) , Toast.LENGTH_SHORT).show();
			ParseObject position = new ParseObject("PositionObject");
			position.put("lat", lat);
			position.put("long", lon);
			position.put("User", ParseUser.getCurrentUser());
			position.saveInBackground();
		}
	}
	
	public void onStartClick(View v) {
		Log.i("PARSE", "CLICKED");
		Log.i("PARSE", ParseUser.getCurrentUser().getUsername());
		Log.i("PARSE", ParseUser.getCurrentUser().isNew()?"true":"false");
		
		running = true;
		
		ParseQuery query = new ParseQuery("PositionObject");
		query.getInBackground("dTFY6QqlUJ", new GetCallback() {
		  public void done(ParseObject object, ParseException e) {
		    if (e == null) {
		    	Log.i("PARSE", object.get("lat") + " OK");
		    } else {
		    	Log.i("PARSE", e.getLocalizedMessage() + " ERR");
		    }
		  }
		});
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 5, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 5, 0, locationListener);
	}

	public void onStopClick(View v) {
		running = false;
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.removeUpdates(locationListener);
	}

	public void onLogoutClick(View v) {
		ParseUser.logOut();
		
		DialogFragment newFragment = new LoginDialog(context);
	    newFragment.show(getFragmentManager(), "login");
		
	}

}