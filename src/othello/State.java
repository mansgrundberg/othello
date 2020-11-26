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
	GUI gui;
	List<Move> validMoves;

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

	public void addGUI(GUI gui) {
		this.gui = gui;
	}

	public void repaint() {
		gui.repaint();
	}

	boolean addDisc(Move move) {
		if (isEmpty(move.row, move.col)) {
			if (flipDiscs(move)) {
				return true;
			} else {
				board[move.row][move.col] = 0; // If no discs were flipped, reset board
			}
		}
		return false;
	}

	public List<Move> getValidMoves(int color) {
		validMoves(color);
		return this.validMoves;
	}
	
	private void validMoves(int color) {
		validMoves = new ArrayList<>();

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				Move move = new Move(i, j, color);
				if (validMove(move)) {
					validMoves.add(move);
				}
			}
		}
	}

	boolean validMove(Move move) {
		int[][] copy = copyArr(board);
		if (isEmpty(move.row, move.col) && wouldFlip(move)) {
			board = copy;
			return true;
		}
		board = copy;
		return false;
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

				if (temp == 0 || temp == move.color)
					continue;

				searching = true;
				x += i;
				y += j;
				while (searching && checkBounds(x, y)) {
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
					x += i;
					y += j;
				}
			}
		}
		return flipped;
	}

	boolean wouldFlip(Move move) {
		int x;
		int y;
		int temp;
		boolean searching;

		board[move.row][move.col] = move.color;

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				x = move.row + i;
				y = move.col + j;

				if (!checkBounds(x, y)) {
					continue;
				}

				temp = board[x][y];

				if (temp == 0 || temp == move.color)
					continue;

				searching = true;
				x += i;
				y += j;
				while (searching && checkBounds(x, y)) {
					temp = board[x][y];

					if (temp == move.color) {
						return true;
					} else if (temp == 0) {
						searching = false;
					}
					x += i;
					y += j;
				}
			}
		}
		return false;
	}

	public int[][] getBoard() {
		return board;
	}

	public void printBoard() {
		System.out.println("  0 1 2 3 4 5 6 7");
		for (int i = 0; i < board.length; i++) {
			System.out.print(i);
			for (int j = 0; j < board.length; j++) {
				System.out.print(" " + toColor(board[i][j]));
			}
			System.out.println();
		}
		System.out.println();
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
