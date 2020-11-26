package othello;

import java.util.Scanner;

public class Game {
	State board;
	Player player;
	AI agent;
	PlayerColor turn;
	
	public Game() {
		board = new State();
		board.addGUI(new GUI(board));
		player = new Player(PlayerColor.WHITE);
		agent = new AI(PlayerColor.BLACK, PlayerColor.WHITE, 6);
		turn = PlayerColor.BLACK;
	}
	
	void start() {
		System.out.println(board.getBlackDiscs());
		System.out.println(board.getWhiteDiscs());
		while(!board.isTerminal()) {
			if (turn == PlayerColor.WHITE) {
				board.addDisc(getInput());
				board.printBoard();
				board.repaint();
				turn = PlayerColor.BLACK;
			} else if (turn == PlayerColor.BLACK) {
				Move move = agent.abPruning(new State(board));
				board.addDisc(move);
				board.printBoard();
				board.repaint();
				turn = PlayerColor.WHITE;
			}
		}
	}
	
	private Move getInput() {
		Scanner sc = new Scanner(System.in);
		int row = sc.nextInt();
		int col = sc.nextInt();
		
		return new Move(row, col, player.color.value);
	}

}
