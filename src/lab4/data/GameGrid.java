package lab4.data;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */
public class GameGrid extends Observable{

	public static int EMPTY = 0;
	public static int ME = 1;
	public static int OTHER = 2;
	public static int INROW = 5;
	
	private int[][] gameGridArray;
	private int consoleStringOutputCounter = 0;
	
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		gameGridArray = new int[size][size];
		for(int x=0;x<gameGridArray.length;x++){
			for(int y=0;y<gameGridArray[x].length;y++){
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
		return gameGridArray.length;//*gameGridArray[0].length;	//N^2
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
			for(int y=0;y<gameGridArray[x].length;y++){
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
		
		for(int x=0;x<gameGridArray.length;x++){
			for(int y=0;y<gameGridArray[x].length;y++){
				
				for(int rh=0;rh<INROW && x+rh<gameGridArray.length;rh++){	//RightHorizontally X-Coord rh.
					if(getLocation(x+rh,y)==player){	/*pekarens position + ett steg åt höger (+1 för varje loopar varv)*/
						winnerCounter++;
						System.out.println("RH winnerCounter="+winnerCounter+" rhloops="+rh);
					}else{
						winnerCounter = 0;
					}
					if(winnerCounter == INROW){return true;}
				}
				winnerCounter = 0;
				
				for(int uv=0;uv<INROW && y+uv<gameGridArray[x].length;uv++){	//UpVertically X-Coord uv.
					if(getLocation(x,y+uv)==player){
						winnerCounter++;
						System.out.println("UV winnerCounter="+winnerCounter+" uvloops="+uv);
					}else{
						winnerCounter = 0;
					}
					if(winnerCounter == INROW){return true;}
				}
				winnerCounter = 0;
				
				int sedy = y;
				for(int sedx=0;sedx<INROW && x+sedx<gameGridArray.length && sedy>=0;sedx++){	//SouthEastDiagonally X & Y-Coord sedx, sedy.
					if(getLocation(x+sedx,y-sedx)==player){	//Obs y-sedx. inte y-sedy
						winnerCounter++;
						System.out.println("SED winnerCounter="+winnerCounter+" sedxloops="+sedx);
					}else{
						winnerCounter = 0;
					}
					if(winnerCounter == INROW){return true;}
					sedy--;
				}
				winnerCounter = 0;
				
				//int nedy = y;
				for(int nedx=0;nedx<INROW && x+nedx<=gameGridArray.length-INROW && y+nedx<=gameGridArray[nedx].length-INROW;nedx++){	//NorthEastDiagonally X & Y-Coord nedx, nedy.
					if(getLocation(x+nedx,y+nedx)==player){
						winnerCounter++;
						System.out.println("NED winnerCounter="+winnerCounter+" nedxloops="+nedx);
					}else{
						winnerCounter = 0;
					}
					if(winnerCounter == INROW){return true;}
				}
				winnerCounter = 0;
			}
		}
		System.out.println(" ");
		System.out.println("---------------------------["+consoleStringOutputCounter+"]----------------------------INROW="+INROW+"-----");
		consoleStringOutputCounter++;
		return false;
	}
}