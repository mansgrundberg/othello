package othello;

public class Evaluator {
	private State state;
	private Player maxColor;
	/*
	 * Heuristic evaluation Number of discs Corner discs ? Stable discs ?
	 * 
	 */
	
	public Evaluator(Player maxColor) {
		this.maxColor = maxColor;
	}

	public double evaluate(State state) {
		this.state = state;

		return discs() + corners();
	}

	private double discs() {
		if (maxColor == Player.BLACK) {
		return 100 * (state.getBlackDiscs()) / (state.getWhiteDiscs() + state.getBlackDiscs());
		} else {
			return  100 * (state.getWhiteDiscs()) / (state.getWhiteDiscs() + state.getBlackDiscs());
		}
	}

	private double corners() {
		int[][] board = state.getBoard();
		int whiteCorners = 0;
		int blackCorners = 0;

		checkCorner(board[0][0], whiteCorners, blackCorners);
		checkCorner(board[7][7], whiteCorners, blackCorners);
		checkCorner(board[0][7], whiteCorners, blackCorners);
		checkCorner(board[7][0], whiteCorners, blackCorners);
		if (whiteCorners + blackCorners != 0) {
			if (maxColor == Player.BLACK) {
			return 100 * (blackCorners) / (whiteCorners + blackCorners);
			} else {
				return 100 * (whiteCorners) / (whiteCorners + blackCorners);
			}
		}
		return 0;
	}

	private void checkCorner(int x, int whiteCorners, int blackCorners) {
		if (x == 1) {
			blackCorners++;
		} else if (x == -1) {
			whiteCorners++;
		}
	}
}
