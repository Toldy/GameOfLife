package com.game.gameoflife;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainGame extends Activity {

	private static final String TAG = MainGame.class.getSimpleName();

	private GameBoard g;
	
	private OnClickListener onClickButtonMinus = new OnClickListener() {
		public void onClick(View v) {
			TextView t = (TextView) findViewById(R.id.textView_size);
			int size = Integer.parseInt(t.getText().toString());
			--size;
			if (size <= 0)
				size = 1;
			t.setText(String.valueOf(size));
			g.initBoard(size);
		}
	};

	private OnClickListener onClickButtonPlus = new OnClickListener() {
		public void onClick(View v) {
			TextView t = (TextView) findViewById(R.id.textView_size);
			int size = Integer.parseInt(t.getText().toString());
			++size;
			t.setText(String.valueOf(size));
			g.initBoard(size);
		}
	};

	private OnClickListener onClickButtonStart = new OnClickListener() {
		public void onClick(View v) {
			g.setRunning(true);
		}
	};
	
	private OnClickListener onClickButtonStop = new OnClickListener() {
		public void onClick(View v) {
			g.setRunning(false);
		}
	};
	private OnClickListener onClickButtonRand = new OnClickListener() {
		public void onClick(View v) {
			g.random();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// making it full screen
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main_game);
		

		g = (GameBoard) findViewById(R.id.gameBoard1);
		
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
