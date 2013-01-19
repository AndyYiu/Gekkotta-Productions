package com.standy.productions;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity {

	MediaPlayer ourSong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ourSong = MediaPlayer.create(Splash.this, R.raw.splashintro);

		ourSong.start();

		setContentView(R.layout.splash);

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(4600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {

					Intent openStartingPoint = new Intent("com.standy.productions.MENU");
					startActivity(openStartingPoint);
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
