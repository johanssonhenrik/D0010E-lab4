package lab4.data;
import java.util.Arrays;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */
public class GameGrid extends Observable{

	public static int EMPTY;
	public static int ME;
	public static int OTHER;
	public static int INROW = 5;
	
	private int[][] gameGridArray;
	
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		gameGridArray = new int[size][size];
		for(int x=0;x<gameGridArray.length;x++){
			for(int y=0;y<gameGridArray[y].length;y++){
				gameGridArray[x][y] = EMPTY;
			}
		}
	}
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return gameGridArray[x][y];
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return gameGridArray.length*gameGridArray[0].length;	//N^2
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player){
		if(gameGridArray[x][y]!=EMPTY){
			return false;
		}else{
			gameGridArray[x][y] = player;
			setChanged();
			notifyObservers();
			return true;
		}
	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		for(int x=0;x<gameGridArray.length;x++){
			for(int y=0;y<gameGridArray[y].length;y++){
				gameGridArray[x][y] = EMPTY;
			}
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 * 
	 */
	public boolean isWinner(int player){
		
		int winnerCounter = 0;
		int moveCounter = INROW;
		
		for(int x=0;x<gameGridArray.length;x++){
			for(int y=0;y<gameGridArray[y].length;y++){
				
				for(int rh=0;rh<=moveCounter && rh<=gameGridArray.length;rh++){	//RightHorizontally X-Coord rh.
					if(getLocation(rh,y)==player){
						winnerCounter++;
						moveCounter++;
					}else{
						winnerCounter = moveCounter = 0;
					}
					if(winnerCounter == INROW){return true;}
				}
				winnerCounter = moveCounter = 0;
				
				for(int uv=0;uv<=moveCounter && uv<=gameGridArray[uv].length;uv++){	//UpVertically X-Coord uv.
					if(getLocation(x,uv)==player){
						winnerCounter++;
						moveCounter++;
					}else{
						winnerCounter = moveCounter = 0;
					}
					if(winnerCounter == INROW){return true;}
				}
				winnerCounter = moveCounter = 0;
				
				int sedy = y;
				for(int sedx=x;sedx<=moveCounter && sedx<=gameGridArray.length  && sedy>=gameGridArray[sedx].length;sedx++){	//SouthEastDiagonally X & Y-Coord sedx, sedy.
					if(getLocation(sedx,sedy)==player){
						winnerCounter++;
						moveCounter++;
					}else{
						winnerCounter = moveCounter = 0;
					}
					if(winnerCounter == INROW){return true;}
					sedy--;
				}
				winnerCounter = moveCounter = 0;
				
				int nedy = y;
				for(int nedx=x;nedx<=moveCounter && nedx<=gameGridArray.length  && nedy>=gameGridArray[nedx].length;nedx++){	//NorthEastDiagonally X & Y-Coord nedx, nedy.
					if(getLocation(nedx,nedy)==player){
						winnerCounter++;
						moveCounter++;
					}else{
						winnerCounter = moveCounter = 0;
					}
					if(winnerCounter == INROW){return true;}
					nedy++;
				}
				winnerCounter = moveCounter = 0;
			}
		}
		return false;
	}
}