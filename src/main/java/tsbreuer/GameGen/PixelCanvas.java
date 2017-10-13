package tsbreuer.GameGen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class PixelCanvas extends Canvas {

	int HEIGHT;
	int WIDTH;
	GameGrid grid;

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				g.setColor(getColor(x, y));
				g.drawLine(x, y, x, y);
			}
		}
	}

	private Color getColor(int x, int y) {
		x = x / 5;
		y = y / 5;
		if (grid.getGrid()[y][x].getValue() == 0) {
			return new Color(255,0, 0);
		} else {
			if (y == GameGen.sy && x == GameGen.sx){
			return new Color(0,0,255);
			}
			else if (grid.getGrid()[y][x].lastTile) {
			return new Color(0,0,255);
			}
			else
			{
			return new Color(0,255, 0);
			}
		}
	}

	public PixelCanvas(GameGrid grid) {
		this.HEIGHT = grid.getHeight() * 5;
		this.WIDTH = grid.getWidth() * 5;
		this.grid = grid;
		this.repaint();
	}
}