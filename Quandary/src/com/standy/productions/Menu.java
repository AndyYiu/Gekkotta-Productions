package com.standy.productions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Menu extends Activity {

	Button play, instructions, credits;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		play = (Button) findViewById(R.id.IVplay);
		instructions = (Button) findViewById(R.id.IVinstructions);
		credits = (Button) findViewById(R.id.IVcredits);
		
		play.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent play = new  Intent("com.standy.productions.GAME");
				startActivity(play);
			}
		});
		
		instructions.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent play = new  Intent("com.standy.productions.INSTRUCTIONS");
				startActivity(play);
			}
		});
		
		credits.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent credit = new Intent("com.standy.productions.CREDITS");
				startActivity(credit);
			}
		});
	
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}	

}
