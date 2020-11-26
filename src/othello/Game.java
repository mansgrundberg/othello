package othello;

import java.util.Scanner;

public class Game {
	State board;
	Player player;
	AI agent;
	PlayerColor turn;
	boolean playerHasMoves = true;
	boolean aiHasMoves = true;

	public Game() {
		board = new State();
		// board.addGUI(new GUI(board));
		player = new Player(PlayerColor.WHITE);
		agent = new AI(PlayerColor.BLACK, PlayerColor.WHITE, 6);
		turn = PlayerColor.WHITE;
	}

	void start() {
		welcome();
		while (!ending()) {
			if (turn == PlayerColor.WHITE) {
				if (playerHasMoves) {
					playerMove();
				} else {
					turn = PlayerColor.BLACK;
				}

			} else if (turn == PlayerColor.BLACK) {
				if (aiHasMoves) {
					agentMove();
				} else {
					turn = PlayerColor.WHITE;
				}
			}
		}
		end();
	}
	
	private void playerMove() {
		Move move = getInput();
		if (board.addDisc(move)) {
			System.out.println("Player's move: row " + move.row + ", col " + move.col + "\n");
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
		}
		
		System.out.println("Agent's move: row " + move.row + ", col " + move.col + "\nDraw Time: " + time + "\n");
		board.addDisc(move);
		update();
	}
	
	
	
	private void update() {
		board.printBoard();
		// board.repaint();
		turn = (turn == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
	}

	private void validMoves() {
		aiHasMoves = (board.getValidMoves(agent.maxColor.value).isEmpty()) ? false : true;
		playerHasMoves = (board.getValidMoves(player.color.value).isEmpty()) ? false : true;
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
		return new Move(row, col, player.color.value);
	}

	private boolean validateInput(int x) {
		return (x >= 0 && x <= 7);
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
