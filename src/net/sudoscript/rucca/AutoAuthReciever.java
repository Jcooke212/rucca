package net.sudoscript.rucca;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Time;
import android.util.Log;

public class AutoAuthReciever extends BroadcastReceiver{
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("RUCCA-Epoch", "I am running my BroadcastReciever now.");
		SharedPreferences settings = context.getSharedPreferences("MainActivity", Context.MODE_PRIVATE);
		String enabled = settings.getString("enabled", "false");
		String token = settings.getString("logoutToken", "null");
		int lastLogin = settings.getInt("loginTimestamp", Time.EPOCH_JULIAN_DAY);
		        
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		
		//The session is about to time out or already has.
		//Let's go ahead and refresh the session and logout token.
		Log.d("RUCCA-Epoch", "I am checking the time after this message. ");
		Log.d("RUCCA-Epoch", "Stamp: " + String.valueOf(lastLogin));
		Log.d("RUCCA-Epoch", "CurTime: " + String.valueOf(((int) (System.currentTimeMillis() / 1000L))));
		if (lastLogin + (60*60) <= (int) (System.currentTimeMillis() / 1000L)) {
			Log.d("RUCCA-Epoch", "I decided it's been 2 hours. We should reAuth next chance we get");
			settings.edit().putString("logoutToken", "null").commit();
			}
		Log.d("RUCCA-Epoch", "I am now about to check if I am in a State that requires me to Auth. ");
		if ( activeNetInfo != null && activeNetInfo.getTypeName().equals("WIFI") && token.equals("null") && enabled.equals("true")){
			Log.d("RUCCA-Epoch", "YES! I am! Logging in...");
			settings.edit().putString("logoutToken", "busy").commit();
			WifiManager wfManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		    WifiInfo wifiinfo = wfManager.getConnectionInfo();  
		    if (Utilities.isConnectedToNetwork(wifiinfo)){
		    	new Login(context).execute();
		    }
		} else {
			Log.d("RUCCA-Epoch", "NO! I am not. I'm dying...");
		}
    }
}
