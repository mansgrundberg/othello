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
		turn = PlayerColor.WHITE;
	}

	void start() {
		board.printBoard();
		while (!board.isTerminal()) {
			if (turn == PlayerColor.WHITE) {
				if (!board.validMoves(PlayerColor.WHITE.value).isEmpty()) {
					if (board.addDisc(getInput())) {
						update();
					} else {
						System.out.println("Invalid move..");
					}
				} else {
					System.out.println("No legal moves for WHITE... Black's turn");
					turn = PlayerColor.BLACK;
				}
			} else if (turn == PlayerColor.BLACK) {
				Move move = agent.abPruning(new State(board));
				board.addDisc(move);
				update();
				turn = PlayerColor.WHITE;
			}
		}
	}

	private void update() {
		board.printBoard();
		board.repaint();
		turn = (turn == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
	}

	private Move getInput() {
		Scanner sc = new Scanner(System.in);
		int row = sc.nextInt();
		int col = sc.nextInt();

		return new Move(row, col, player.color.value);
	}

}
