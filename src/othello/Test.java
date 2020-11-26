package othello;

import java.util.Scanner;

public class Test {
	
	public static void main(String[] args) {
		State state = new State(8);
		Scanner sc = new Scanner(System.in);
		
		state.printBoard();
		
		while (sc.hasNext()) {
		int row = sc.nextInt();
		int col = sc.nextInt();
		int color = sc.nextInt();
		if (!state.addDisc(new Move(row, col, color))) System.out.println("Invalid move");
		}
	}

}
