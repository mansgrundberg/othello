package othello;

import java.util.Arrays;

/*
 * Keeps track of actual board / game state
 */

public class State {
	private int[][] board;
	static final int COLS = 8;
	static final int ROWS = 8;
	private int white;
	private int black;

	public State (int size) {
		board = new int[size][size];
		white = 0;
		black = 0;
	}
	
	
	public void addDisc(int row, int col, int color) {
		if (validMove(row, col)) {
			board[row][col] = color;
			flipDiscs(row, col, color);
		}
		printBoard();
		countDiscs();
	}
	
	boolean validMove(int row, int col) {
		return board[row][col] == 0;
	}
	
	void flipDiscs(int row, int col, int color) {
		int x;
		int y;
		int temp;
		boolean searching;
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				x = row + i;
				y = col + j;
				
				if (!checkBounds(x, y)) {
					continue;
				}
				
				temp = board[x][y];
				
				if (temp == 0 || temp == color) continue;
				
				searching = true;
				while (searching && checkBounds(x, y)) {
					x += i;
					y += j;
					temp = board[x][y];
					
					if (temp == color) {
						x -= i;
						y -= j;
						temp = board[x][y];
						while (temp != color) {
							board[x][y] = color;
							x -= i;
							y -= j;
							temp = board[x][y];
						}
						searching = false;
						
					} else if (temp == 0) {
						searching = false;
					} 
				}
			}
		}
	}
	
	boolean checkBounds(int x, int y) {
		return (x > 0 && x < State.COLS - 1 && y > 0 && y < State.ROWS - 1); 
	}
	
	void countDiscs() {
		white = (int) Arrays.stream(board).flatMapToInt(Arrays::stream).filter(x -> (x == -1)).count();
		black = (int) Arrays.stream(board).flatMapToInt(Arrays::stream).filter(x -> (x == 1)).count();
	}
	
	boolean isTerminal() {
		return (black + white == State.ROWS * State.COLS);
	}
	
	
	public void printBoard() {
		System.out.println("  0 1 2 3 4 5 6 7");
		for (int i = 0; i < board.length; i++) {
			System.out.print(i);
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(" " + toColor(board[i][j]));
			}
			System.out.println();
		}
	}
	
	public char toColor(int x) {
		if (x < 0) {
			return 'W';
		} else if (x > 0) {
			return 'B';
		} else {
			return '-';
		}
	}
	
}
