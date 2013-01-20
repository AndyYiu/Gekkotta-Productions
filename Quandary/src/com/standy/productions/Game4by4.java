package com.standy.productions;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class Game4by4 extends Activity implements OnTouchListener {

	OurView v;
	Bitmap grid;

	Bitmap[][] tiles;
	Bitmap temp;
	Bitmap playAgain, backToMenu;
	int[][] tilesNums;

	boolean movable, levelCompleted;
	boolean ok = false;
	boolean boardLoaded = false;
	int tempi, tempz, blanki, blankz, tilei, tilez, tempnum;
	int bToMenuX = 190, bToMenuY = 51;
	int pAgainX = 50, pAgainY = 165;
	float cx, cy, tilex, tiley, blankx, blanky;

	ArrayList<Integer> stored;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		v = new OurView(this);
		v.setOnTouchListener(this);

		grid = BitmapFactory
				.decodeResource(getResources(), R.drawable.gamegrid);

		tiles = new Bitmap[4][4];
		tilesNums = new int[tiles.length][tiles[0].length];
		stored = new ArrayList<Integer>();
		backToMenu = BitmapFactory.decodeResource(getResources(),
				R.drawable.bmenu);
		playAgain = BitmapFactory.decodeResource(getResources(),
				R.drawable.bplayagain);
		setContentView(v);
		setProgressBarVisibility(true);

	}

	public void genBoardRand() {

		stored.clear();

		int num = 16;

		for (int i = 0; i < tiles.length; i++) {
			for (int z = 0; z < tiles[i].length; z++) {
				boolean valid = false;

				while (valid == false) {
					// (int) (Math.random() * (max - min + 1) ) + min;
					num = (int) (Math.random() * (16 - 1 + 1)) + 1;// 16 = Blank

					if (!stored.contains(num)) {
						valid = true;
					}
				}
				if (valid == true) {
					stored.add(num);
					tilesNums[i][z] = num;
					switch (num) {
					case 16:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.blank);
						blanki = i;
						blankz = z;
						break;
					case 1:

						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.one);
						break;
					case 2:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.two);

						break;
					case 3:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.three);

						break;
					case 4:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.four);

						break;
					case 5:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.five);

						break;
					case 6:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.six);

						break;
					case 7:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.seven);

						break;
					case 8:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.eight);

						break;
					case 9:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.nine);

						break;
					case 10:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.ten);

						break;
					case 11:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.eleven);

						break;
					case 12:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.twelve);

						break;
					case 13:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.thirteen);

						break;
					case 14:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.fourteen);

						break;
					case 15:
						tiles[i][z] = BitmapFactory.decodeResource(
								getResources(), R.drawable.fifteen);
						break;

					}

				}
			}
		}
	}

	public boolean validBoard(int[][] array) {
		// Algorithm taken from
		// http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
		// Implemented into Java by us
		boolean valid = false;
		int numInv = 0;
		int blankRow = 0;

		int[] a = new int[array.length * array[0].length];

		int counter = 0;
		// Turns 2D array into 1D
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				a[counter] = array[i][j];
				counter += 1;
			}
		}

		// Counts number of inversions
		for (int i = 0; i < a.length; i++) {

			for (int z = (i); z < a.length; z++) {
				if (a[i] > a[z]) {
					if (a[i] != 16) {
						numInv += 1;
					}
				}
			}
			// System.out.println(numInv);
		}
		// Checks which row blank is in
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] == 16) {
					blankRow = i + 1;
				}
			}
		}
		if (array[0].length % 2 == 0) {// Even grid width
			// System.out.println("BlankRow: " + blankRow);
			// If even row from TOP, num inv = even
			if (blankRow % 2 == 0) {// Even row
				// System.out.println("Even Row");

				if (numInv % 2 == 0) {
					valid = true;
				} else {
					valid = false;
				}

				// If odd row from TOP, num inv = odd
			} else if (blankRow % 2 != 0) {// Odd row

				// System.out.println("Odd Row");

				if (numInv % 2 != 0) {
					valid = true;
				} else {
					valid = false;
				}
			}
		} else if (array[0].length % 2 != 0) {// Odd grid width,solvable
												// situation has odd num
												// inversions
			if (numInv % 2 != 0) {
				valid = true;
			}

		}

		return valid;
	}

	public boolean levelComplete(int[][] array) {
		boolean done = false;
		int[] a = new int[array.length * array[0].length];
		int counter = 0;
		int check = 0;

		for (int i = 0; i < array.length; i++) {
			for (int z = 0; z < array[i].length; z++) {
				a[counter] = array[i][z];
				counter += 1;
			}
		}

		for (int i = 0; i < a.length; i++) {
			if (a[i] == (i + 1)) {
				check += 1;
			}

		}

		if (check == 16) {
			done = true;
		}

		return done;

	}
	
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Closing Activity")
	        .setMessage("Are you sure you want to close this activity?")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            finish();    
	        }

	    })
	    .setNegativeButton("No", null)
	    .show();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		v.pause();
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		v.resume();
	}

	public class OurView extends SurfaceView implements Runnable {

		Thread t = null;
		SurfaceHolder holder;

		public OurView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			holder = getHolder();

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (ok == true) {
				// perform drawings/actions
				if (!holder.getSurface().isValid()) {
					continue;
				}
				// Skips the rest of the code below it so that it
				// wont run if surface isnt valid

				// Lock->Draw-> Unlock
				Canvas c = holder.lockCanvas();
				int x = 45;
				int y = 315;

				c.drawBitmap(grid, 0, 0, null);

				if (boardLoaded == false) {
					genBoardRand();

					boardLoaded = validBoard(tilesNums);

				} else if (boardLoaded == true) {

					for (int i = 0; i < tiles.length; i++) {
						for (int z = 0; z < tiles[i].length; z++) {
							c.drawBitmap(tiles[i][z], x, y, null);
							x += 97;
						}
						x = 45;
						y += 107;
					}

					if (levelComplete(tilesNums)) {
						c.drawBitmap(playAgain, pAgainX, pAgainY, null);
						c.drawBitmap(backToMenu, bToMenuX, bToMenuY, null);
					}
				}

				holder.unlockCanvasAndPost(c);

			}
		}

		public void pause() {
			ok = false;
			while (true) {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}

		public void resume() {
			ok = true;
			t = new Thread(this);
			t.start();

		}

	}

	public boolean onTouch(View v, MotionEvent me) {
		// TODO Auto-generated method stub

		try {
			Thread.sleep(33);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Add if pressed then put switch inside IF statement for it
		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			cx = me.getX();
			cy = me.getY();
			getTilePosition(cx, cy);

			if (levelCompleted) {
				if (cx > pAgainX && cx < ( pAgainX + playAgain.getWidth()) && cy > pAgainY
						&& cy < (pAgainY + playAgain.getHeight())) {

					boardLoaded = false;
					levelCompleted = false;

				} else if (cx > bToMenuX && cx < (bToMenuX + backToMenu.getWidth())
						&& cy > bToMenuY && cy < (bToMenuY + backToMenu.getHeight())) {
					Intent menu = new Intent("com.standy.productions.MENU");
					startActivity(menu);

					this.finish();

				}
			} else {
				if (checkSurround()) {
					temp = tiles[tilei][tilez];
					tiles[tilei][tilez] = tiles[blanki][blankz];
					tiles[blanki][blankz] = temp;

					tempnum = tilesNums[tilei][tilez];
					tempi = tilei;
					tempz = tilez;
					tilesNums[tilei][tilez] = tilesNums[blanki][blankz];
					tilei = blanki;
					tilez = blankz;
					tilesNums[blanki][blankz] = tempnum;
					blanki = tempi;
					blankz = tempz;
				}
				movable = false;
			}
			levelCompleted = levelComplete(tilesNums);
		}

		return false;
	}

	public boolean checkSurround() {
		if (tilei + 1 == blanki && tilez == blankz) {
			movable = true;
			blankx = tilex;
			blanky = tiley + 107;
		} else if (tilei - 1 == blanki && tilez == blankz) {
			movable = true;
			blankx = tilex;
			blanky = tiley - 107;
		} else if (tilei == blanki && tilez + 1 == blankz) {
			movable = true;
			blankx = tilex + 97;
			blanky = tiley;
		} else if (tilei == blanki && tilez - 1 == blankz) {
			movable = true;
			blankx = tilex - 97;
			blanky = tiley;
		} else {
			movable = false;
		}
		return movable;
	}

	public void getTilePosition(float x, float y) {

		tilex = x - ((x - 45) % 97);
		tiley = y - ((y - 315) % 107);

		if (x > 45 && x < 142 && y > 315 && y < 422) {
			tilei = 0;
			tilez = 0;
		}
		if (x > 142 && x < 239 && y > 315 && y < 422) {
			tilei = 0;
			tilez = 1;
		}
		if (x > 239 && x < 336 && y > 315 && y < 422) {
			tilei = 0;
			tilez = 2;
		}
		if (x > 336 && x < 433 && y > 315 && y < 422) {
			tilei = 0;
			tilez = 3;
		}
		if (x > 45 && x < 142 && y > 422 && y < 529) {
			tilei = 1;
			tilez = 0;
		}
		if (x > 142 && x < 239 && y > 422 && y < 529) {
			tilei = 1;
			tilez = 1;
		}
		if (x > 239 && x < 336 && y > 422 && y < 529) {
			tilei = 1;
			tilez = 2;
		}
		if (x > 336 && x < 433 && y > 422 && y < 529) {
			tilei = 1;
			tilez = 3;
		}
		if (x > 45 && x < 142 && y > 529 && y < 636) {
			tilei = 2;
			tilez = 0;
		}
		if (x > 142 && x < 239 && y > 529 && y < 636) {
			tilei = 2;
			tilez = 1;
		}
		if (x > 239 && x < 336 && y > 529 && y < 636) {
			tilei = 2;
			tilez = 2;
		}
		if (x > 336 && x < 433 && y > 529 && y < 636) {
			tilei = 2;
			tilez = 3;
		}
		if (x > 45 && x < 142 && y > 636 && y < 743) {
			tilei = 3;
			tilez = 0;
		}
		if (x > 142 && x < 239 && y > 636 && y < 743) {
			tilei = 3;
			tilez = 1;
		}
		if (x > 239 && x < 336 && y > 636 && y < 743) {
			tilei = 3;
			tilez = 2;
		}
		if (x > 336 && x < 433 && y > 636 && y < 743) {
			tilei = 3;
			tilez = 3;
		}
	}

}
