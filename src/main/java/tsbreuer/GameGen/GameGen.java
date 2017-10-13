package tsbreuer.GameGen;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GameGen {
	
	public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final int sx = 50;
    public static final int sy = 50;
    
    public static void main(String[] args) throws IOException {
		GameGrid grid = new GameGrid(WIDTH,HEIGHT, sx,sy);
		grid.Generate(50,50);
		while (grid.getTileCount() < 5500)
		{
			grid.Generate();
		}
		grid.tileList.get(grid.tileList.size()-1).setLast(true);
		grid.ShowGrid();
		
        Frame frame = new Frame();

        frame.setSize(WIDTH, HEIGHT);
        PixelCanvas gridImg = new PixelCanvas(grid);
        frame.add(gridImg);

        frame.setVisible(true);
        
        BufferedImage bi = new BufferedImage(WIDTH*10, HEIGHT*10, BufferedImage.TYPE_INT_RGB);
        bi.createGraphics();
        Graphics g = bi.getGraphics();
        gridImg.paint(g);
        ImageIO.write(bi, "PNG", new File("Maze.png"));
	}

}
