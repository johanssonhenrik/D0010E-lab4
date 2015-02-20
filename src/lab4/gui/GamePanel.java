package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/**
 * A panel providing a graphical view of the game board
 */
public class GamePanel extends JPanel implements Observer {
	private Color colorEMPTY = Color.black;
	private Color colorME = Color.green;
	private Color colorOTHER = Color.red;
	public static int UNIT_SIZE = 20;
	public GameGrid grid;
	private GomokuGameState gameState;

	/**
	 * The constructor
	 * 
	 * @param grid
	 *            The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid) {
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize() * UNIT_SIZE + 1,
				grid.getSize() * UNIT_SIZE + 1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.white);
		this.setVisible(true);
		getGridPosition(55,43);
	}

	/**
	 * Returns a grid position given pixel coordinates of the panel
	 * 
	 * @param x
	 *            the x coordinates
	 * @param y
	 *            the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y) {
		int[] integerArray = { x/UNIT_SIZE, y/UNIT_SIZE}; // unit size for conversion??
		System.out.println(integerArray[0]+" , "+integerArray[1]);
		return integerArray;
	}

	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < Math.sqrt(grid.getSize()); i++) { // Y
			for (int ii = 0; ii < Math.sqrt(grid.getSize()); ii++) {// X
			// if(grid.getLocation(1,2)==grid.EMPTY)
				buildingRect(ii, i, grid.EMPTY, colorEMPTY, g);
				if(grid.getLocation(ii,i)==grid.ME){
					buildingOval(ii, i, grid.ME, colorME, g);
				}
				if(grid.getLocation(ii,i)==grid.OTHER){
					buildingCross(ii, i, grid.OTHER, colorOTHER, g);
				}
				//
				//
			}
		}
		//this.repaint();
	}

	/*
	 * Creating gameFigures
	 */
	public void buildingRect(int Xs, int Ys, int State, Color color, Graphics g) {
		// if(grid.getLocation(Xs,Ys)==State){ // gör ingen skillnad
		int x = UNIT_SIZE;
		int y = UNIT_SIZE;
		g.setColor(color);
		// g.create(Xs+20, Ys+20, UNIT_SIZE, UNIT_SIZE);
		g.drawRect(Xs * x-Xs, Ys * y-Ys, UNIT_SIZE, UNIT_SIZE);
		//g.setColor(color);
		//g.fillRect(Xs * x, Ys * y, UNIT_SIZE, UNIT_SIZE);
		// }
	}

	public void buildingOval(int Xs, int Ys, int State, Color color, Graphics g) {
		// if(grid.getLocation(Xs,Ys)==State){ // gör ingen skillnad
		int x = UNIT_SIZE + 1;
		int y = UNIT_SIZE + 1;
		g.setColor(color);
		g.drawOval(Xs * x-Xs, Ys * y-Ys, UNIT_SIZE - 3, UNIT_SIZE - 3);
		// }
	}

	public void buildingCross(int Xs, int Ys, int State, Color color, Graphics g) {
		// if(grid.getLocation(Xs,Ys)==State){ // gör ingen skillnad
		int x = UNIT_SIZE;
		int y = UNIT_SIZE;
		g.setColor(color);
		g.drawLine(Xs * x + Xs, Ys * y + Ys, Xs * x + x + Xs, y * Ys + y + Ys);
		g.drawLine(Xs * x + Xs, y * Ys + y + Ys, Xs * x + Xs + x, Ys * y + Ys);
		// }
	}
}
