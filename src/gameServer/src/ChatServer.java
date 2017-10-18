package gameServer.src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(555);
			System.out.println("서버 열림");

			Socket socket = null;

			while (true) {
				try {
					System.out.println("대기중");
					socket = serverSocket.accept();

					System.out.println("연결성공 : " + socket.getLocalAddress());
					ServerThread ServerThread = new ServerThread(socket);
					ServerThread.start();
				} catch (IOException ex) {
					System.err.println("ServerThread 오류\n" + ex);
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
			System.out.println("이미 서버가 존재합니다.");
		}
	}

}
