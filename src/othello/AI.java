package othello;

import java.util.List;

public class AI {
	Player maxColor;
	Player minColor;
	Evaluator evaluator = new Evaluator();
	private int maxDepth;
	private int nodes;
	private int searchDepth;
	private List<Move> moveList;

	public AI(Player agentColor, Player pColor, int depth) {
		maxColor = agentColor;
		maxDepth = depth;
		minColor = pColor;
	}

	public Move abPruning(State state) {
		Move move = null;
		nodes = 0;
		searchDepth = 0;
		int v = max(state, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);

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
		nodes++;
		if (depth == 0) {
			moveList = moves;
		}
		
		if (depth > searchDepth) {
			searchDepth = depth;
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
		nodes++;
		if (depth > searchDepth) {
			searchDepth = depth;
		}
		
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
	
	public int getSearchDepth() {
		return searchDepth;
	}

	public int getNodes() {
		return this.nodes;
	}
	
	private double utility(State state) {
		return evaluator.evaluate(state);
	}

}
