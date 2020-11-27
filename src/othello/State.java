package othello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Keeps track of actual board / game state
 */

public class State {
	private int[][] board;
	static final int COLS = 8;
	static final int ROWS = 8;

	public State() {
		board = new int[ROWS][COLS];
		board[3][3] = 1;
		board[3][4] = -1;
		board[4][3] = -1;
		board[4][4] = 1;
	}

	public State(State state) {
		board = copyArr(state.getBoard());
	}
	
	boolean addDisc(Move move) {
		return (isEmpty(move.row, move.col) && flipDiscs(move, true));
	}

	public List<Move> getValidMoves(int color) {
		List<Move> validMoves = new ArrayList<>();

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				Move move = new Move(i, j, color);
				if (validMove(move)) {
					validMoves.add(move);
				}
			}
		}
		return validMoves;
	}

	boolean validMove(Move move) {
		boolean valid = false;
		int[][] copy = copyArr(board);
		if (isEmpty(move.row, move.col) && flipDiscs(move, false)) {
			valid = true;
		}
		board = copy;
		return valid;
	}
	
	/*
	 * If flip = true, actually flip discs. Otherwise just check if move is legal.
	 */
	boolean flipDiscs(Move move, boolean flip) {
		int x;
		int y;
		int temp;
		boolean flipped = false;
		board[move.row][move.col] = move.color;

		// Check each direction
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				x = move.row + i;
				y = move.col + j;

				boolean first = true;
				while (checkBounds(x, y)) {
					temp = board[x][y];

					if (first && (temp == 0 || temp == move.color)) {
						break;
					} else {
						first = false;
					}

					if (temp == move.color) {
						if (!flip)
							return true;
						flip(x, y, i, j, move);
						flipped = true;
						break;
					} else if (temp == 0) {
						break;
					}
					x += i;
					y += j;
				}
			}
		}
		board[move.row][move.col] = (flipped) ? move.color : 0;
		return flipped;
	}

	private void flip(int x, int y, int i, int j, Move move) {
		x -= i;
		y -= j;
		int temp = board[x][y];
		while (temp != move.color) {
			board[x][y] = move.color;
			x -= i;
			y -= j;
			temp = board[x][y];
		}
	}

	public int[][] getBoard() {
		return board;
	}

	public void printBoard() {
		System.out.println("  1 2 3 4 5 6 7 8");
		for (int i = 0; i < board.length; i++) {
			System.out.print(i + 1);
			for (int j = 0; j < board.length; j++) {
				System.out.print(" " + toColor(board[i][j]));
			}
			System.out.println();
		}
		System.out.println();
	}

	public char toColor(int x) {
		switch(x) {
		case 1:
			return 'B';
		case -1:
			return 'W';
		default:
			return '-';
		}
	}

	private int[][] copyArr(int[][] arr) {
		int[][] copy = new int[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			copy[i] = Arrays.copyOf(arr[i], ROWS);
		}
		return copy;
	}

	boolean checkBounds(int x, int y) {
		return (x >= 0 && x < State.COLS && y >= 0 && y < State.ROWS);
	}

	int getWhiteDiscs() {
		return (int) Arrays.stream(board).flatMapToInt(Arrays::stream).filter(x -> (x == -1)).count();
	}

	int getBlackDiscs() {
		return (int) Arrays.stream(board).flatMapToInt(Arrays::stream).filter(x -> (x == 1)).count();
	}

	boolean isTerminal() {
		return (getWhiteDiscs() + getBlackDiscs() == State.ROWS * State.COLS);
	}

	boolean isEmpty(int row, int col) {
		return board[row][col] == 0;
	}

}
