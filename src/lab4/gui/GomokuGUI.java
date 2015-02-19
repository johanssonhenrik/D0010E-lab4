package lab4.gui;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 */
public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	
	GamePanel gameGridPanel;
	JLabel messageLabel;
	JButton connectButton;
	JButton newGameButton;
	JButton disconnectButton;
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(gamestate.DEFAULT_SIZE*gameGridPanel.UNIT_SIZE, gamestate.DEFAULT_SIZE*gameGridPanel.UNIT_SIZE);
		frame.setTitle("Gomoku");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setBackground(Color.WHITE);
		gameGridPanel = new GamePanel(gamestate.getGameGrid());
		gameGridPanel.setSize(200,200);
		gameGridPanel.setVisible(true);
		// frame.add(gameGridPanel)
		messageLabel = new JLabel();
	}
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
}
