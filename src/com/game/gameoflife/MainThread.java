package com.game.gameoflife;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	private static final String TAG = MainThread.class.getSimpleName();
	private boolean running;
	private SurfaceHolder surfaceHolder;
	private GameBoard gameBoard;

	private static final int MAX_FPS = 50;
	private static final int MAX_FRAME_SKIPS = 5;
	private static final int FRAME_PERIOD = 1000 / MAX_FPS;

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

		long beginTime;
		long timeDiff;
		int sleepTime;
		int framesSkipped;

		Log.d(TAG, "Starting game loop");
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (MainThread.class) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0;

					this.gameBoard.update();
					this.gameBoard.render(canvas);

					timeDiff = System.currentTimeMillis() - beginTime;
					sleepTime = (int) (FRAME_PERIOD - timeDiff);

					if (sleepTime > 0) {
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
						}
					}

					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						this.gameBoard.update();
						sleepTime += FRAME_PERIOD;
						framesSkipped++;
					}
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

}
