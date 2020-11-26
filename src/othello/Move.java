package othello;

public class Move {
	int row;
	int col;
	int color;
	int value; // Assign heuristic values to moves for AI agent
	
	public Move (int row, int col, int color) {
		this.row = row;
		this.col = col;
		this.color = color;
	}

}
