package othello;

public class Evaluator {
	private State state;
	/*
	 * Heuristic evaluation
	 * Number of discs
	 * Corner discs ?
	 * Stable discs ?
	 * 
	 */
	
	public double evaluate(State state) {
		this.state = state;
		
		return discs() + corners();
	}
	
	private double discs() {
		return 100 * (state.getBlackDiscs()) / (state.getWhiteDiscs() + state.getBlackDiscs());
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
		return 100 * (blackCorners) / (whiteCorners + blackCorners);
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
