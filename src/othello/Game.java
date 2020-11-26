package othello;

import java.util.Scanner;

public class Game {
	State board;
	Player player;
	AI agent;
	PlayerColor turn;
	boolean noMovesPlayer = false;
	boolean noMovesAI = false;

	public Game() {
		board = new State();
		board.addGUI(new GUI(board));
		player = new Player(PlayerColor.WHITE);
		agent = new AI(PlayerColor.BLACK, PlayerColor.WHITE, 6);
		turn = PlayerColor.WHITE;
	}

	void start() {
		board.printBoard();
		while (!ending()) {
			if (turn == PlayerColor.WHITE && validMove(player.color, noMovesPlayer)) {
				Move move = getInput();
				if (board.addDisc(move)) {
					System.out.println("Player's move: row " + move.row + ", col " + move.col + "\n");
					update();
				} else {
					System.out.println("Invalid move..");
				}

			} else if (turn == PlayerColor.BLACK && validMove(agent.maxColor, noMovesAI)) {
				long before = System.currentTimeMillis();
				Move move = agent.abPruning(new State(board));
				long time = (System.currentTimeMillis() - before);
				if (time > 5000) {
					System.out.println("Agent draw time exceeded, ending game..");
				}
				System.out.println("Agent's move: row " + move.row + ", col " + move.col + "\nDraw Time: " + time);
				board.addDisc(move);
				update();
			}
		}
		end();
	}

	private void update() {
		board.printBoard();
		System.out.println();
		board.repaint();
		turn = (turn == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
	}

	private boolean validMove(PlayerColor c, boolean noMoves) {
		if (board.getValidMoves(c.value).isEmpty()) { // No valid moves, other player's turn
			System.out.println("No legal moves for " + c + "\n");
			turn = (turn == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
			noMoves = true;
			return false;
		}
		noMoves = false;
		return true;
	}
	
	private boolean ending() {
		return board.isTerminal() || (noMovesAI && noMovesPlayer);
	}
	
	private void end() {
		System.out.println("Game is over!!");
		int whites = board.getWhiteDiscs();
		int blacks = board.getBlackDiscs();
		int diff = whites - blacks;
		
		System.out.println("White Discs: " + whites + " , Black Discs " + blacks);
		
		if (diff < 0) {
			System.out.println("Black (AI Agent) wins!");
		} else if (diff > 0) {
			System.out.println("White (Human) wins!");
		} else {
			System.out.println("It's a tie!");
		}
		
		System.exit(0);
	}

	private Move getInput() {
		Scanner sc = new Scanner(System.in);
		int row = sc.nextInt();
		int col = sc.nextInt();
		
		while (!validateInput(row) || !validateInput(col)) {
			System.out.println("Invalid row or column, please input a number between 0 and 7 inclusive");
			row = sc.nextInt();
			col = sc.nextInt();
		}
		return new Move(row, col, player.color.value);
	}
	
	private boolean validateInput(int x) {
		return (x >= 0 && x <= 7);
	}

}
