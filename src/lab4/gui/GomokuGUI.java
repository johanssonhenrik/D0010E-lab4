package lab4.gui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
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
	GameGrid grid;
	JLabel messageLabel;
	JButton connectButton;
	JButton newGameButton;
	JButton disconnectButton;
	ConnectionWindow connectionWindow;
	private static int windowLocation = 0;
	
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
		if(windowLocation == 0){frame.setLocation(500, 150);}else{frame.setLocation(950, 150);}
		windowLocation = 1;
		frame.setSize(gamestate.DEFAULT_SIZE*GamePanel.UNIT_SIZE+10, gamestate.DEFAULT_SIZE*GamePanel.UNIT_SIZE+80);
		frame.setTitle("Gomoku");
		frame.setVisible(true);
		frame.setResizable(false);
		
		gameGridPanel = new GamePanel(gamestate.getGameGrid());
		gameGridPanel.setVisible(true);
		
		messageLabel = new JLabel();
		connectButton = new JButton("Connect");
		newGameButton = new JButton("New Game");
		disconnectButton = new JButton("Disconnect");
		if(gamestate.currentState == gamestate.NOT_STARTED){
			newGameButton.setEnabled(false);
			disconnectButton.setEnabled(false);
		}
		connectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String keyInput = arg0.getActionCommand();
				if(keyInput.equals("Connect")){
					connectionWindow = new ConnectionWindow(client);
					messageLabel.setText("Attempt to connect to OTHER player");
				}
			}
		});
		newGameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String buttonInput = e.getActionCommand();
				if(buttonInput.equals("New Game")){
					gamestate.newGame();
					messageLabel.setText("New game started");
				}
			}
		});
		disconnectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String buttonInput = e.getActionCommand();
				if(buttonInput.equals("Disconnect")){
					gamestate.disconnect();
					messageLabel.setText("Disconnected from the game");
				}
			}
		});
		MouseAdapter mouseListener = new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				gamestate.move(gameGridPanel.getGridPosition(e.getX(), e.getY())[0],
						(gameGridPanel.getGridPosition(e.getX(), e.getY()))[1]);
			}
		};
		
		gameGridPanel.addMouseListener(mouseListener);
		
		Box box = Box.createHorizontalBox();
		box.add(connectButton);
		box.add(newGameButton);
		box.add(disconnectButton);
		
		Box boxTwo = Box.createHorizontalBox();
		boxTwo.add(messageLabel);
		
		Box boxThree = Box.createVerticalBox();
		boxThree.add(gameGridPanel);
		boxThree.add(box);
		boxThree.add(boxTwo);
		frame.add(boxThree);
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
