import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(555);
			System.out.println("서버 생성중...");
			Socket socket = null;
			while (true) {
				try {
					System.out.println("서버시작");
					socket = serverSocket.accept();
					System.out.println("소캣생성 : " + socket.getLocalAddress());
					serverThread ServerThread = new serverThread(socket);
					ServerThread.start();
				} catch (IOException ex) {
					System.err.println("ServerThread  \n" + ex + "오류   ");
					try {
						if (socket != null) {
							socket.close();
						}
					} catch (IOException e) {
						System.err.println(e);
					} finally {
						socket = null;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("이미 서버 생성중");
		}
	}
}
