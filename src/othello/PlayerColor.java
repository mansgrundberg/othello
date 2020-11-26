package othello;

public enum PlayerColor {
	WHITE(-1), BLACK(1);
	
	public final int value;
	
	private PlayerColor(int value) {
		this.value = value;
	}
}
