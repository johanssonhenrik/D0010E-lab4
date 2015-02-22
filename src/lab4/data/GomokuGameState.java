/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	public final int DEFAULT_SIZE = 15;	//Public for GomokuGUI
	private GameGrid gameGrid;
	
    //Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINISHED = 3;
	private int currentState;
	
	private GomokuClient client;
	
	private String message;
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString(){
		return message;//message to display notify obs, setchanged
	}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y){
		//carries out a move the player \me" makes, if possible.
		if(currentState != FINISHED || currentState != NOT_STARTED){
			if(currentState==MY_TURN){
				if(gameGrid.move(x, y, GameGrid.ME )){
					message = "Square is empty! Move"+"("+x+","+y+") made";
					// (GomokuClient)client.sendMoveMessage(x,y)
					receivedMove(x,y);
					gameGrid.isWinner(GameGrid.ME);
					currentState=OTHER_TURN;
					setChangedNnotify();
				}
				else{
					message = "Square is not empty, move is not made!";
					setChangedNnotify();
				}
			}
			else{
				message = "It´s not your turn, move is not made!";
				setChangedNnotify();
			}
		}
		else{
			message = "The game is not started or already finished!";
			setChangedNnotify();
		}	
	}
	public void setChangedNnotify(){
		setChanged();
		notifyObservers();
	/**
	 * Starts a new game with the current client
	 */
	public void newGame(){
		gameGrid.clearGrid();
		currentState = OTHER_TURN;
		message = "You have just started a NEW GAME!";
		setChangedNnotify();
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame(){
		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "The other player started a NEW GAME!";
		setChangedNnotify();
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){
		gameGrid.clearGrid();
		message = "The connection to the other player is lost";
		currentState = NOT_STARTED;
		setChangedNnotify();
	}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect(){
		gameGrid.clearGrid();
		message = "You are now disconnecting..";
		currentState = NOT_STARTED;
		// client.disconnect();
		setChangedNnotify();
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y){
		if(gameGrid.isWinner(GameGrid.OTHER)){
			// need to update board.
			message = "The other player won!";
			currentState = FINISHED;
			setChangedNnotify();
		}	
		else {
			message = "The other player did not win after this move, its now your turn!";
			currentState = MY_TURN;
			setChangedNnotify();
		}
	}
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		setChangedNnotify();
		
		
	}
	
}
