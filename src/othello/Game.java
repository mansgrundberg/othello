package othello;

import java.util.Scanner;

public class Game {
	State board;
	AI agent;
	Player player;
	Player turn;
	boolean playerHasMoves = true;
	boolean aiHasMoves = true;

	public Game() {
		board = new State();
		player = Player.WHITE;
		agent = new AI(Player.BLACK, Player.WHITE, 6);
		turn = Player.WHITE;
	}

	void start() {
		welcome();
		while (!ending()) {
			if (turn == Player.WHITE) {
				if (playerHasMoves) {
					playerMove();
				} else {
					System.out.println("Player has no legal moves... Agent's turn!");
					turn = Player.BLACK;
				}

			} else if (turn == Player.BLACK) {
				if (aiHasMoves) {
					agentMove();
				} else {
					System.out.println("Agent has no legal moves... Player's turn!");
					turn = Player.WHITE;
				}
			}
		}
		end();
	}
	
	private void playerMove() {
		Move move = getInput();
		if (board.addDisc(move)) {
			System.out.println("Player's move: row " + (move.row + 1) + ", col " + (move.col + 1) + "\n");
			update();
		} else {
			System.out.println("Invalid move..");
		}
	}

	private void agentMove() {
		long before = System.currentTimeMillis();
		Move move = agent.abPruning(new State(board));
		long time = (System.currentTimeMillis() - before);
		
		if (time > 5000) {
			System.out.println("Agent draw time exceeded, ending game..");
			System.exit(0);
		}
		
		System.out.println("Agent's move: row " + (move.row + 1) + ", col " + (move.col + 1) + "\nDraw Time: " + time + "ms");
		System.out.println("Depth of search: " + agent.getSearchDepth());
		System.out.println("Nodes examined: " + agent.getNodes() + "\n");
		board.addDisc(move);
		update();
	}
	
	
	
	private void update() {
		board.printBoard();
		// board.repaint();
		turn = (turn == Player.WHITE) ? Player.BLACK : Player.WHITE;
	}

	private void validMoves() {
		aiHasMoves = (board.getValidMoves(agent.maxColor.value).isEmpty()) ? false : true;
		playerHasMoves = (board.getValidMoves(player.value).isEmpty()) ? false : true;
	}

	private boolean ending() {
		validMoves();
		return board.isTerminal() || (!playerHasMoves && !aiHasMoves);
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
		System.out.println("Input the row and column where you want to place a disc (on separate lines)");
		Scanner sc = new Scanner(System.in);
		int row = sc.nextInt();
		int col = sc.nextInt();
		
		while (!validateInput(row) || !validateInput(col)) {
			System.out.println("Invalid row or column, please input a number between 0 and 7 inclusive");
			row = sc.nextInt();
			col = sc.nextInt();
		}
		return new Move(row-1, col-1, player.value);
	}

	private boolean validateInput(int x) {
		return (x >= 1 && x <= 8);
	}
	
	private void welcome() {
		System.out.println("Welcome to Othello! Try to beat the AI Agent!");
		System.out.println("You are playing as white. Your turn! \n");
		board.printBoard();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

}
