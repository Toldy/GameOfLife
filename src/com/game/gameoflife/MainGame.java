package com.game.gameoflife;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainGame extends Activity {

	private static final String TAG = MainGame.class.getSimpleName();

	private GameBoard g;
	TextView textViewSize;
	TextView textViewIsRunning;

	private OnClickListener onClickButtonMinus = new OnClickListener() {
		public void onClick(View v) {
			synchronized (MainThread.class) {
				int size = Integer.parseInt(textViewSize.getText().toString());
				--size;
				if (size <= 0)
					size = 1;
				textViewSize.setText(String.valueOf(size));
				g.initBoard(size);
			}
		}
	};

	private OnClickListener onClickButtonPlus = new OnClickListener() {
		public void onClick(View v) {
			synchronized (MainThread.class) {
				int size = Integer.parseInt(textViewSize.getText().toString());
				++size;
				textViewSize.setText(String.valueOf(size));
				g.initBoard(size);
			}
		}
	};

	private OnClickListener onClickButtonStart = new OnClickListener() {
		public void onClick(View v) {

			synchronized (MainThread.class) {
				setRunning(true);
			}
		}
	};

	private OnClickListener onClickButtonStop = new OnClickListener() {
		public void onClick(View v) {

			synchronized (MainThread.class) {
				setRunning(false);
			}
		}
	};
	private OnClickListener onClickButtonRand = new OnClickListener() {
		public void onClick(View v) {

			synchronized (MainThread.class) {
				g.random();
				
			}
		}
	};

	public void setRunning(boolean isRunning) {
		if (isRunning) {
			textViewIsRunning.setText("running...");
			textViewIsRunning.setBackgroundColor(Color.GREEN);
		} else {
			textViewIsRunning.setText("stopped...");
			textViewIsRunning.setBackgroundColor(Color.RED);
		}
		g.setRunning(isRunning);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// making it full screen
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main_game);

		g = (GameBoard) findViewById(R.id.gameBoard1);
		textViewSize = (TextView) findViewById(R.id.textView_size);
		textViewIsRunning = (TextView) findViewById(R.id.textView_isRunning);
		Button bMinus = (Button) findViewById(R.id.button_minus);
		Button bPlus = (Button) findViewById(R.id.button_plus);
		Button bStart = (Button) findViewById(R.id.button_start);
		Button bStop = (Button) findViewById(R.id.button_stop);
		Button bRand = (Button) findViewById(R.id.button_rand);
		bMinus.setOnClickListener(onClickButtonMinus);
		bPlus.setOnClickListener(onClickButtonPlus);
		bStart.setOnClickListener(onClickButtonStart);
		bStop.setOnClickListener(onClickButtonStop);
		bRand.setOnClickListener(onClickButtonRand);
	}

	@Override
	protected void onResume() {

		Log.d(TAG, "onResume...");
		super.onResume();
	}
	
	@Override
	protected void onPause() {

		g.getMainThread().setRunning(false);
		Log.d(TAG, "onPause...");
		super.onPause();
	}
	
//	@Override
//	public void onBackPressed() {
		//g.hardExit();
	    // your code.
//	}
	
	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
}
