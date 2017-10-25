import java.io.IOException;

import javax.swing.JOptionPane;

public class ChatClient {

	public static void main(String[] args) {
		try{
			ClientThread clientThread = new ClientThread();
			clientThread.start();
			//clientThread.login();
		}catch(Exception e){
			System.out.println(e);
		}
	}
}