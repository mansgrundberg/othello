package othello;

public enum Player {
	WHITE(-1), BLACK(1);
	
	public final int value;
	
	private Player(int value) {
		this.value = value;
	}
}
