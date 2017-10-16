package CharServer.src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(555);
			System.out.println("���� ������...");

			Socket socket = null;

			while (true) {
				try {
					System.out.println("��������");
					socket = serverSocket.accept();

					System.out.println("��Ĺ���� : " + socket.getLocalAddress());
					serverThread ServerThread = new serverThread(socket);
					ServerThread.start();
				} catch (IOException ex) {
					System.err.println("ServerThread ��������\n" + ex);
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
			System.out.println("�̹� ���� ������");
		}
	}

}
