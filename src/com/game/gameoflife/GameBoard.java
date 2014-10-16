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

public class GameBoard extends SurfaceView implements SurfaceHolder.Callback {

	private MainThread mainThread;
	private Board board;
	private static final String TAG = GameBoard.class.getSimpleName();

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

	public MainThread getMainThread() {
		return mainThread;
	}

	public void init() {
		getHolder().addCallback(this); // This class callbacks will be called

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

		if (mainThread.getState() == Thread.State.TERMINATED) {
			mainThread = new MainThread(getHolder(), this);
		}
		mainThread.setRunning(true);
		mainThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		mainThread.setRunning(false);
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
		}
		return true;
	}

	public void update() {
		board.update();
	}

	public void render(Canvas c) {
		if (c == null)
			return;

		c.drawColor(Color.BLACK);
		board.draw(c);
	}
}
