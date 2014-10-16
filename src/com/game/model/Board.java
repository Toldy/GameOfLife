package com.game.model;

import java.util.Random;

import com.game.gameoflife.MainGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Board {

	@SuppressWarnings("unused")
	private static final String TAG = MainGame.class.getSimpleName();

	private int nbCases;
	private int height;
	private int width;
	private int sizeBoard;
	private int caseSize;
	private int borderSize;
	private boolean[][] board;

	private boolean isRunning;

	private Paint paintPlainSquare = new Paint();
	private Paint paintStrokeSquare = new Paint();

	public Board() {
		paintPlainSquare.setColor(Color.GREEN);
		paintPlainSquare.setStrokeWidth(0);
		paintStrokeSquare.setStyle(Paint.Style.STROKE);
		paintStrokeSquare.setColor(Color.WHITE);
	}

	public int getNbCases() {
		return nbCases;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void init(int nbCases, int height, int width) {
		this.nbCases = nbCases;
		this.height = height;
		this.width = width;
		sizeBoard = Math.min(width, height);
		caseSize = (int) (sizeBoard / (nbCases + 2));
		borderSize = (sizeBoard - (caseSize * nbCases)) / 2;
		board = new boolean[nbCases][nbCases];
		this.setRunning(false);
		this.clear();
	}

	public void draw(Canvas canvas) {
		for (int x = 0; x < nbCases; x++) {
			for (int y = 0; y < nbCases; y++)
				canvas.drawRect(x * caseSize + borderSize, y * caseSize
						+ borderSize, (x + 1) * caseSize + borderSize, (y + 1)
						* caseSize + borderSize,
						board[y][x] == true ? paintPlainSquare
								: paintStrokeSquare);
		}
	}

	public void update() {
		if (isRunning) {
			boolean[][] boardCpy = deepCopyBooleanArray(board);
			for (int y = 0; y < nbCases; y++) {
				for (int x = 0; x < nbCases; x++) {
					int nbCells = getNbCellNearby(x, y);
					if (board[y][x]) {
						if (nbCells != 2 && nbCells != 3)
							boardCpy[y][x] = false;
					} else if (nbCells == 3)
						boardCpy[y][x] = true;
				}
			}
			board = boardCpy;
		}
	}

	public void handleActionDown(int eventX, int eventY) {
		if (eventX >= borderSize && eventX <= sizeBoard - borderSize)
			if (eventY >= borderSize && eventY <= sizeBoard - borderSize) {
				board[(int) (eventY - borderSize) / caseSize][(int) (eventX - borderSize)
						/ caseSize] = !board[(int) (eventY - borderSize)
						/ caseSize][(int) (eventX - borderSize) / caseSize];
			}

	}

	public void clear() {
		if (!isRunning) {
			for (int y = 0; y < board.length; y++)
				for (int x = 0; x < board[y].length; x++)
					board[y][x] = false;
		}
	}

	public void random() {
		if (!isRunning) {
			Random r = new Random();
			for (int y = 0; y < board.length; y++)
				for (int x = 0; x < board[y].length; x++)
					board[y][x] = r.nextInt(4) == 1 ? true : false;
		}
	}

	private int getNbCellNearby(int x, int y) {
		int nbCells = 0;
		for (int yi = y - 1; yi <= y + 1; yi++) {
			for (int xi = x - 1; xi <= x + 1; xi++) {
				if (xi >= 0 && yi >= 0 && xi < nbCases && yi < nbCases)
					if (!(xi == x && yi == y)) {
						if (board[yi][xi])
							nbCells++;
					}
			}
		}
		return nbCells;
	}

	private boolean[][] deepCopyBooleanArray(boolean[][] input) {
		if (input == null)
			return null;
		boolean[][] result = new boolean[input.length][];
		for (int r = 0; r < input.length; r++) {
			result[r] = input[r].clone();
		}
		return result;
	}
}