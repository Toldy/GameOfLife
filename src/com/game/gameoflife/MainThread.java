package com.game.gameoflife;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	private static final String TAG = MainThread.class.getSimpleName();
	private boolean running;
	private SurfaceHolder surfaceHolder;
	private GameBoard gameBoard;

	public MainThread(SurfaceHolder surfaceHolder, GameBoard gameBoard) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gameBoard = gameBoard;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while (running) {
			//SystemClock.sleep(100);
			canvas = null;
			// try locking the canvas for exclusive pixel editing on the surface
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					this.gameBoard.update();
					// update game state
					// draws the canvas on the panel
					this.gameBoard.render(canvas);
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

}
