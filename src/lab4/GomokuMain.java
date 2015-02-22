package lab4;
import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain{

	public static void main(String[] args){ //Port Number as argument.
		
		//Local player vs Online player.
		int portNumber = (args.length<1) ? 9001 : Integer.parseInt(args[0]);
		GomokuClient GC = new GomokuClient(portNumber);
		GomokuGameState GGS = new GomokuGameState(GC);
		GomokuGUI GGUI = new GomokuGUI(GGS,GC);

		//Local vs Local
		//Client 1.
		/*int portNumberOne = (args.length<1) ? 9001 : Integer.parseInt(args[0]);
		GomokuClient GCOne = new GomokuClient(portNumberOne);
		GomokuGameState GGSOne = new GomokuGameState(GCOne);
		GomokuGUI GGUIOne = new GomokuGUI(GGSOne,GCOne);
		
		//Client 2.
		int portNumberTwo = (args.length<2) ? 9002 : Integer.parseInt(args[1]);
		GomokuClient GCTwo = new GomokuClient(portNumberTwo);
		GomokuGameState GGSTwo = new GomokuGameState(GCTwo);
		GomokuGUI GGUITwo = new GomokuGUI(GGSTwo,GCTwo);*/
		
	}
}
