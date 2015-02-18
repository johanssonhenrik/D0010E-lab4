package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{
	private int x= 1;
	private int y= 2;
	private Color colorEMPTY = Color.white;
	private Color colorME = Color.yellow;
	private Color colorOTHER = Color.black;
	public final int UNIT_SIZE = 20;	//Public for GomokuGUI
	private GameGrid grid;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y){
		int[] integerArray = {x,y}; // unit size for conversion??
		return integerArray;
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i=0;i<grid.getSize(); i++){
			for(int ii =0; ii<Math.sqrt(grid.getSize());ii++){
				
				if(grid.getLocation(ii,i)==GameGrid.EMPTY){
					g.setColor(colorEMPTY);
					g.drawRect(x,y,UNIT_SIZE,UNIT_SIZE);
					g.fillRect(x,y,UNIT_SIZE,UNIT_SIZE);
					x = x + UNIT_SIZE;
					y = y + UNIT_SIZE;
				}
				if(grid.getLocation(ii,i)==GameGrid.ME){
					g.setColor(colorME);
					g.drawRect(x,y,UNIT_SIZE,UNIT_SIZE);
					g.fillRect(x,y,UNIT_SIZE,UNIT_SIZE);
					x = x + UNIT_SIZE;
					y = y + UNIT_SIZE;
				}
				if(grid.getLocation(ii,i)==GameGrid.OTHER){
					g.setColor(colorOTHER);
					g.drawRect(x,y,UNIT_SIZE,UNIT_SIZE);
					g.fillRect(x,y,UNIT_SIZE,UNIT_SIZE);
					x = x + UNIT_SIZE;
					y = y + UNIT_SIZE;
				}
			}
			
		}
		
		
		
	}
	public void haxx(){
		
	}
	
}