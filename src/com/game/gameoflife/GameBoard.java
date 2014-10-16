package com.game.gameoflife;

import com.game.model.Board;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.annotation.SuppressLint;
import android.app.Activity;

public class GameBoard extends SurfaceView implements SurfaceHolder.Callback {

	private MainThread mainThread;
	private Board board;
	private static final String TAG = GameBoard.class.getSimpleName();

	private long timeToRefresh;
	private long elapsedTime;
	private long previousRefresh;
	
	public GameBoard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public GameBoard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	public GameBoard(Context context) {
		super(context);
		init();
	}

	public void init() {
		getHolder().addCallback(this); // This class callbacks will be called

		timeToRefresh = 100;
		elapsedTime = 0;
		previousRefresh = System.currentTimeMillis();;
		board = new Board();
		mainThread = new MainThread(getHolder(), this);
		
		setFocusable(true); // allow this class to receuve events

	}
	
	public void initBoard(int nbCases) {
		board.init(nbCases, getHeight(), getWidth());
	}
	
	public void setRunning(boolean b) {
		board.setRunning(b);
	}
	
	public void random() {
		board.random();
	}
	public void clear() {
		board.clear();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initBoard(20);
		mainThread.setRunning(true);
		mainThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				mainThread.join();
				retry = false;
			} catch (InterruptedException e) {

			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			board.handleActionDown((int) event.getX(), (int) event.getY());
			if (event.getY() > getHeight() - 50) {
				hardExit();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		}
		return true;
	}
	
	public void update() {
		board.update();
	}
	
	public void render(Canvas c) {
		if (c == null)
			return;

	/*	elapsedTime = System.currentTimeMillis() - previousRefresh;
		if (elapsedTime > timeToRefresh) {
			previousRefresh = System.currentTimeMillis();
			elapsedTime = 0;*/
			c.drawColor(Color.BLACK);
			board.draw(c);
		/*}*/
	}
	
	public void hardExit() {
		mainThread.setRunning(false);
		((Activity) getContext()).finish();
	}
}
