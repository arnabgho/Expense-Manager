package com.arnab.meandmyfriendsexpense;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity{
	MediaPlayer ourSong;	
	@Override
	protected void onCreate(Bundle arnabGho) {
		// TODO Auto-generated method stub
		super.onCreate(arnabGho);
		setContentView(R.layout.splash);
		ourSong = MediaPlayer.create(Splash.this, R.raw.test1);
		
		
		SharedPreferences getPrefs=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean music= getPrefs.getBoolean("checkbox",true);
		if (music==true)
			ourSong.start();
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2000);
					
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				finally{
					Intent OpenStartingPoint=new Intent(Splash.this,Menu_new1.class);
					startActivity(OpenStartingPoint);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourSong.release();
		finish();
	}

	
}
