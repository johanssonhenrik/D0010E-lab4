package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
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
	public static int UNIT_SIZE = 40;

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
		Dimension d = new Dimension(grid.getSize() * UNIT_SIZE, grid.getSize()
				* UNIT_SIZE);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.white);
		this.setVisible(true);
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
		int[] integerArray = { x / UNIT_SIZE, y / UNIT_SIZE }; // unit size for
																// conversion??
		
		return integerArray;
	}

	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}

	/**
	 * 
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * @param g
	 * 
	 * This method paints all the squares and markers on the grid.
	 * 
	 */
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < grid.getSize(); i++) { // Y
			for (int ii = 0; ii < grid.getSize(); ii++) {// X
				//if (grid.getLocation(ii, i) == GameGrid.EMPTY)
					buildingRect(ii, i, colorEMPTY, g);
				if (grid.getLocation(ii, i) == GameGrid.ME) {
					buildingOval(ii, i, colorME, g);
				}
				if (grid.getLocation(ii, i) == GameGrid.OTHER) {
					buildingCross(ii, i, colorOTHER, g);
				}
			}
		}
		this.repaint();
	}

	/**
	 * @ color Color of the rectangel
	 * @param g	Graphic Object used for drawing the rectangel
	 * @param Xs X-start Coord for the rectangle
	 * @param Ys Y-start Coord for the rectangle
	 * Creating gameFigures
	 */
	public void buildingRect(int Xs, int Ys, Color color, Graphics g) {

		int x = UNIT_SIZE;
		int y = UNIT_SIZE;
		g.setColor(color);
		g.drawRect(Xs * x, Ys * y, UNIT_SIZE, UNIT_SIZE);
	}
	/**
	 * 
	 * @param Xs Ys color g 
	 * Creating gameFigures
	 */
	public void buildingOval(int Xs, int Ys, Color color, Graphics g) {
		int x = UNIT_SIZE;
		int y = UNIT_SIZE;
		g.setColor(color);
		g.drawOval(Xs * x + 1, Ys * y + 1, UNIT_SIZE - 2, UNIT_SIZE - 2);
	}
	/**
	 * 
	 * @param Xs Ys color g 
	 * Creating gameFigures
	 */
	public void buildingCross(int Xs, int Ys, Color color, Graphics g) {

		int x = UNIT_SIZE;
		int y = UNIT_SIZE;
		g.setColor(color);
		g.drawLine(Xs * x, Ys * y, Xs * x + x, y * Ys + y);
		g.drawLine(Xs * x, y * Ys + y, Xs * x + x, Ys * y);
	}
}

