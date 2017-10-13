package tsbreuer.GameGen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("unused")
public class GameGrid {

	// Game Grid total size
	private int sizeX;
	private int sizeY;

	// Starting (aka. base) point of the game grid.
	private int startX = 0;
	private int startY = 0;

	// Game Grid
	private Tile[][] grid;
	
	// Tile List
	List<Tile> tileList = new ArrayList<Tile>();

	private int tilesGenerated;

	public GameGrid(int x, int y) {
		this(x,y, 0, 0);
	}

	public GameGrid(int x, int y, int sx, int sy) {
		this.sizeX = x;
		this.sizeY = y;
		this.grid = new Tile[y][x];
		this.startX = sx;
		this.startY = sy;
		
		for (int ax = 0; ax < x; ax++) {
			for (int ay = 0; ay < y; ay++) {
				this.grid[ay][ax] = new Tile(0, ax, ay);
			}
		}
	}

	public void Generate() {
		Random random = new Random();
		for (Tile tile : tileList) {
			Set<Direction> validDirections = new HashSet<>();
			if (CanGoUp(tile.x, tile.y))
				validDirections.add(Direction.UP);
			if (CanGoDown(tile.x, tile.y))
				validDirections.add(Direction.DOWN);
			if (CanGoRight(tile.x, tile.y))
				validDirections.add(Direction.RIGHT);
			if (CanGoLeft(tile.x, tile.y))
				validDirections.add(Direction.LEFT);
			List<Direction> directions = new ArrayList<Direction>(validDirections);
			if (directions.size() > 0) {
			Direction randomDirection = directions.get(random.nextInt(directions.size()));
			Generate(tile.x, tile.y);
			break;
			}
		}
	}
	
	public void Generate(int cpx, int cpy) {
		changeTileValue(cpx, cpy, 1);
		boolean canContinue = true;
		int newTile = tilesGenerated;;
		while (canContinue) {
			// up, down, right, left (arriba, abajo, derecha, izq)

			Set<Direction> validDirections = new HashSet<>();
			if (CanGoUp(cpx, cpy))
				validDirections.add(Direction.UP);
			if (CanGoDown(cpx, cpy))
				validDirections.add(Direction.DOWN);
			if (CanGoRight(cpx, cpy))
				validDirections.add(Direction.RIGHT);
			if (CanGoLeft(cpx, cpy))
				validDirections.add(Direction.LEFT);

			Random random = new Random(); // in a perfect world, you should only have one of these
			List<Direction> directions = new ArrayList<Direction>(validDirections);

			if (directions.size() == 0) {
				canContinue = false;
				System.out.println("Finished Generating");
				System.out.println("Number of new tiles: " + newTile);
				this.tilesGenerated = newTile;
				break;
			}
			newTile++;
			// Get a random direction, and move there
			Direction randomDirection = directions.get(random.nextInt(directions.size()));
			int[] newPos = newPosition(cpx, cpy, randomDirection);
			System.out.println("New tile! " + randomDirection.getText());
			cpx = newPos[0];
			cpy = newPos[1];
			System.out.println(cpx + " " + cpy + " ");
			changeTileValue(cpx, cpy, 1);
			tileList.add(this.grid[cpy][cpx]);
			
		}
	}

	public int[] newPosition(int sx, int sy, Direction dir) {
		int[] newPos = { sx, sy };
		if (dir == Direction.UP) {
			newPos[1] = sy - 1;
		} else if (dir == Direction.DOWN) {
			newPos[1] = sy + 1;
		} else if (dir == Direction.LEFT) {
			newPos[0] = sx - 1;
		} else if (dir == Direction.RIGHT) {
			newPos[0] = sx + 1;
		}
		return newPos;
	}

	public void changeTileValue(int x, int y, int value) {
		this.grid[y][x].setValue(value);
		this.grid[y][x].x = x;
		this.grid[y][x].y = y;
	}
	public boolean CanGoUp(int sx, int sy) {
		boolean can = true;
		// Can we go up?
		if (sy < 1) // Are we on the limit of grid?
		{
			can = false;
		} else {
			if (this.grid[sy - 1][sx].getValue() == 1) // If not, is the space up free (0)?
			{
				can = false;
			}
			if (sx > 0) {
				if (this.grid[sy - 1][sx - 1].getValue() == 1) // Is the space alone on side to left?
				{
					can = false;
				}
			}
			if (sx < (this.grid[sy - 1].length - 1)) {
				if (this.grid[sy - 1][sx + 1].getValue() == 1) // Is the space alone on side to right?
				{
					can = false;
				}
			}
			if (sy > 1) {
				if (this.grid[sy - 2][sx].getValue() == 1) // Is the grid under the one we want free too?
				{
					can = false;
				}
			}
		}
		return can;
	}

	public boolean CanGoDown(int sx, int sy) {
		boolean can = true;
		// Can we go down?
		if (sy < (this.grid.length - 1)) // Are we on the limit of grid?
		{
			if (this.grid[sy + 1][sx].getValue() == 1) // If not, is the space down free (0)?
			{
				can = false;
			}
			if (sy < (this.grid.length - 2)) {
				if (this.grid[sy + 2][sx].getValue() == 1) // Is grid 2 down free?
				{
					can = false;
				}
			}
			if (sx > 0) {
				if (this.grid[sy + 1][sx - 1].getValue() == 1) // is grid down left free?
				{
					can = false;
				}
			}
			if (sx < (this.grid[sy + 1].length - 1)) {
				if (this.grid[sy + 1][sx + 1].getValue() == 1) // is grid down right free?
				{
					can = false;
				}
			}
		} else {
			can = false;
		}
		return can;
	}

	public boolean CanGoRight(int sx, int sy) {
		boolean can = true;
		// Can we go right?
		if (sx < (this.grid[sy].length - 1)) // Are we on the limit of grid?
		{
			if (this.grid[sy][sx + 1].getValue() == 1) // If not, is the space right free (0)?
			{
				can = false;
			}
			if (sy > 0) {
				if (this.grid[sy - 1][sx + 1].getValue() == 1)// is the space right up free?
				{
					can = false;
				}
			}
			if (sx < (this.grid[sy].length - 2)) {
				if (this.grid[sy][sx + 2].getValue() == 1) // is the space right x2 free?
				{
					can = false;
				}
			}
			if (sy < (this.grid.length - 1)) {
				if (this.grid[sy + 1][sx + 1].getValue() == 1) // is the space right down free?
				{
					can = false;
				}
			}
		} else {
			can = false;
		}
		return can;
	}

	public boolean CanGoLeft(int sx, int sy) {
		boolean can = true;
		// Can we go right?
		if (sx < 1) // Are we on the limit of grid?
		{
			can = false;
		} else {
			if (this.grid[sy][sx - 1].getValue() == 1) // If not, is the space right free (0)?
			{
				can = false;
			}
			if (sx > 1) {
				if (this.grid[sy][sx - 2].getValue() == 1) // Is the space left x2 free?
				{
					can = false;
				}
			}
			if (sy > 0) {
				if (this.grid[sy - 1][sx - 1].getValue() == 1)// is the space left up free?
				{
					can = false;
				}
			}
			if (sy < (this.grid.length - 1)) {
				if (this.grid[sy + 1][sx - 1].getValue() == 1) // is ther space left down free?
				{
					can = false;
				}
			}
		}
		return can;
	}

	public void ShowGrid() {
		for (int i = 0; i < this.grid.length; i++) {
			String line = "";
			for (int a = 0; a < this.grid[i].length; a++) {
				line = line + " " + this.grid[i][a].getValue();
			}
			System.out.println(line);
		}
	}

	public int getTileCount() {
		return this.tilesGenerated;
	}

	public int getHeight() {
		return this.grid.length;
	}

	public int getWidth() {
		return this.grid[0].length;
	}

	public Tile[][] getGrid() {
		return this.grid;
	}

}
