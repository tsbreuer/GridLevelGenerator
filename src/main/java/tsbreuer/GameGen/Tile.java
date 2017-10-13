package tsbreuer.GameGen;

public class Tile {
	private int value = 0;
	public int x;
	public int y;
	boolean lastTile = false;
	
	public Tile(int value, int x, int y) {
		this.value = value;
		this.x = x;
		this.y = y;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public void setValue(int val)
	{
		this.value = val;
	}

	public void setLast(boolean b) {
		this.lastTile = b;
	}
}
