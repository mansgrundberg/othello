package othello;

import java.util.List;

public class AI {
	PlayerColor maxColor;
	PlayerColor minColor;
	Evaluator evaluator = new Evaluator();
	private int maxDepth;
	private List<Move> moveList;

	public AI(PlayerColor agentColor, PlayerColor pColor, int depth) {
		maxColor = agentColor;
		maxDepth = depth;
		minColor = pColor;
	}

	public Move abPruning(State state) {
		Move move = null;
		int v = max(state, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);

		// List<Move> moves = state.getValidMoves(maxColor.value);
		for (Move m : moveList) {
			if (m.value == v) {
				move = m;
				break;
			}
		}
		return move;
	}

	private int max(State state, int alpha, int beta, int depth) {
		List<Move> moves = state.getValidMoves(maxColor.value);
		if (depth == 0) {
			moveList = moves;
		}
		
		if (state.isTerminal() || depth == maxDepth || moves.isEmpty()) { // or cutoff
			return (int) utility(state);
		}
		int v = Integer.MIN_VALUE;
		
		for (Move move : moves) {
			State temp = new State(state);
			temp.addDisc(move);
			move.value = min(temp, alpha, beta, depth + 1);
			v = Math.max(v, move.value);
			if (v >= beta) {
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;
	}

	private int min(State state, int alpha, int beta, int depth) {
		List<Move> moves = state.getValidMoves(minColor.value);
		if (state.isTerminal() || depth == maxDepth || moves.isEmpty()) { // or cutoff
			return (int) utility(state);
		}
		int v = Integer.MAX_VALUE;

		for (Move move : moves) {
			State temp = new State(state);
			temp.addDisc(move);
			move.value = max(temp, alpha, beta, depth + 1);
			v = Math.min(v, move.value);
			if (v <= alpha) {
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}

	private double utility(State state) {
		return evaluator.evaluate(state);
	}

}
