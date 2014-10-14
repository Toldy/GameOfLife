package com.game.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameBoard
	extends SurfaceView
	implements SurfaceHolder.Callback{

	public GameBoard(Context context) {
		super(context);
		getHolder().addCallback(this); // This class callbacks will be called
		setFocusable(true); // allow this class to receuve events
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return super.onTouchEvent(e);
	}
	
	@Override
	protected void onDraw(Canvas c) {
	}
}
