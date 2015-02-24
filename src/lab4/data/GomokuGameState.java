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
public class GomokuGameState extends Observable implements Observer {
	// Game variables
	public final int DEFAULT_SIZE = 10; // Public for GomokuGUI
	public GameGrid gameGrid;
	// Possible game states
	public final int NOT_STARTED = 0;
	public final int MY_TURN = 1;
	public final int OTHER_TURN = 2;
	public final int FINISHED = 3;
	public int currentState;
	public GomokuClient client;
	public String message;

	/**
	 * The constructor
	 * 
	 * @param gc
	 *            The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc) {
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
	public String getMessageString() {
		return message;// message to display notify obs, setchanged
	}

	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid() {
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public void move(int x, int y) {
		// carries out a move the player \me" makes, if possible.
		if (currentState != FINISHED || currentState != NOT_STARTED){
			if (currentState == MY_TURN) {
				if (gameGrid.move(x, y, GameGrid.ME)){

					message = "Square is empty! Move" + "(" + x + "," + y
							+ ") made";
					client.sendMoveMessage(x, y);
					receivedMove(x,y);
					currentState = OTHER_TURN;
					if (gameGrid.isWinner(GameGrid.ME)){
						currentState = FINISHED;
						message = "You WON!";
						setChangedNnotify();
					}else{
						currentState = OTHER_TURN;
						setChangedNnotify();
					}
				}else{
					message = "Square is not empty, move is not made!";
					setChangedNnotify();
				}
			}else if(currentState == NOT_STARTED){
				message = "Connect to the OTHER player before clicking the grid";
				setChangedNnotify();
			}else{
				message = "It's not your turn, move is not made!";
				setChangedNnotify();
			}
		}else{
			message = "The game is not started or already finished!";
			setChangedNnotify();
		}
	}

	public void setChangedNnotify() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Starts a new game with the current client
	 */
	public void newGame() {
		System.out.println(currentState);
		if(currentState == FINISHED){
			gameGrid.clearGrid();
			message = "Click the New Game Button to play again";
			client.sendNewGameMessage();
			setChangedNnotify();
		}else if(currentState == NOT_STARTED){
			message = "Connect to the OTHER player before clicking New Game";
			client.sendNewGameMessage();
			setChangedNnotify();
		}else{
			gameGrid.clearGrid();
			currentState = OTHER_TURN;
			message = "You have just started a NEW GAME!";
			client.sendNewGameMessage();
			setChangedNnotify();
		}
	}

	/**
	 * Other player has requested a new game, so the game state is changed
	 * accordingly
	 */
	public void receivedNewGame() {
		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "The other player started a NEW GAME!";
		setChangedNnotify();
	}

	/**
	 * The connection to the other player is lost, so the game is interrupted
	 */
	public void otherGuyLeft() {
		gameGrid.clearGrid();
		message = "The connection to the other player is lost";
		currentState = NOT_STARTED;
		setChangedNnotify();
	}

	/**
	 * The player disconnects from the client
	 */
	public void disconnect() {
		gameGrid.clearGrid();
		message = "You are now disconnecting..";
		currentState = NOT_STARTED;
		client.disconnect();
		setChangedNnotify();
	}

	/**
	 * The player receives a move from the other player
	 * 
	 * @param x
	 *            The x coordinate of the move
	 * @param y
	 *            The y coordinate of the move
	 */
	public void receivedMove(int x, int y) {
		gameGrid.move(x,y,GameGrid.OTHER);
		if(gameGrid.isWinner(GameGrid.OTHER)) {
			message = "The other player won!";
			currentState = FINISHED;
			setChangedNnotify();
		}else{
			message = "OTHER player made his move, now it's your turn!";
			currentState = MY_TURN;
			setChangedNnotify();
		}
	}

	public void update(Observable o, Object arg) {
		switch (client.getConnectionStatus()) {
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
	public int returnCurrentState(){
		return currentState;
	}
}

