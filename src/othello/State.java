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
		board[3][3] = 1;
		board[3][4] = -1;
		board[4][3] = -1;
		board[4][4] = 1;
	}
	
	
	boolean addDisc(Move move) {
		boolean ok = false;
		if (isEmpty(move.row, move.col)) {
			if (flipDiscs(move)) {
			printBoard();
			countDiscs();
			ok = true;
			} else {
				board[move.row][move.col] = 0; // If no discs were flipped, reset board
			}
		}
		return ok;
	}
	
	boolean flipDiscs(Move move) {
		int x;
		int y;
		int temp;
		boolean searching;
		boolean flipped = false;
		
		board[move.row][move.col] = move.color;
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				x = move.row + i;
				y = move.col + j;
				
				if (!checkBounds(x, y)) {
					continue;
				}
				
				temp = board[x][y];
				
				if (temp == 0 || temp == move.color) continue;
				
				searching = true;
				while (searching && checkBounds(x, y)) {
					x += i;
					y += j;
					temp = board[x][y];
					
					if (temp == move.color) {
						x -= i;
						y -= j;
						temp = board[x][y];
						while (temp != move.color) {
							board[x][y] = move.color;
							x -= i;
							y -= j;
							temp = board[x][y];
						}
						searching = false;
						flipped = true;
						
					} else if (temp == 0) {
						searching = false;
					} 
				}
			}
		}
		return flipped;
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
	
	boolean checkBounds(int x, int y) {
		return (x > 0 && x < State.COLS - 1 && y > 0 && y < State.ROWS - 1); 
	}
	
	void countDiscs() {
		white = (int) Arrays.stream(board).flatMapToInt(Arrays::stream).filter(x -> (x == -1)).count();
		black = (int) Arrays.stream(board).flatMapToInt(Arrays::stream).filter(x -> (x == 1)).count();
	}
	
	int getWhiteDiscs() {
		return white;
	}
	
	int getBlackDiscs() {
		return black;
	}
	
	boolean isTerminal() {
		return (black + white == State.ROWS * State.COLS);
	}
	
	boolean isEmpty(int row, int col) {
		return board[row][col] == 0;
	}
	
	
}
