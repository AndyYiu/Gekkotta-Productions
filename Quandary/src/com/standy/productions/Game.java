package com.standy.productions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Game extends Activity {

	Button three,four,menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		three = (Button) findViewById(R.id.IVthreebythree);
		four = (Button) findViewById(R.id.IVfourbyfour);
		menu = (Button) findViewById(R.id.IVbmenu);
		
		three.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent play = new  Intent("com.standy.productions.GAME3");
				startActivity(play);
			}
		});
		
		four.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent play = new  Intent("com.standy.productions.GAME4");
				startActivity(play);
			}
		});
		
		menu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent credit = new Intent("com.standy.productions.MENU");
				startActivity(credit);
			}
		});
	
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}	

}
