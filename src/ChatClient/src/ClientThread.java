package ChatClient.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

public class ClientThread extends Thread implements CommonConstant {

	/*
	 * private static final String SEPA = "|"; private static final int
	 * LOGIN_REQUEST = 0; private static final int LOGIN_SUCCESS = 1; private static
	 * final int LOGIN_FAIL = 2; private static final int LOGOUT_SUCCESS = 3;
	 * private static final int LOGOUT_REQUEST = 4;
	 */
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private StringBuffer buf;

	private Thread selfThread;

	private String UserID;

	private UIWaitRoom UiWaitRoom;
	private UIChattingRoom UiChattingRoom;
	private Login lg;

	public ClientThread() {
		try {
			socket = new Socket(InetAddress.getLocalHost(), 555);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			buf = new StringBuffer(1024);

			lg = new Login(this);

			selfThread = this;
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "���� ���� ����" + e + "\n ä�ù��� �����մϴ�.", "�α���",
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	public void run() {
		try {
			Thread currThread = Thread.currentThread();
			while (selfThread == currThread) {
				String recvData = input.readUTF();
				StringTokenizer st = new StringTokenizer(recvData, SEPA);
				int cmd = Integer.parseInt(st.nextToken());
				switch (cmd) {
				case LOGIN_SUCCESS: {
					JOptionPane.showConfirmDialog(null, "로그인이 완료 되었습니다.", "메세지", JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					UiWaitRoom = new UIWaitRoom(this);

					lg.dispose();
					break;
					/*
					 * StringTokenizer st_test = new StringTokenizer("USER1|USER2|USER3|USER4",
					 * SEPA);
					 * 
					 * Vector userList = new Vector<>(); while(st_test.hasMoreTokens()){
					 * userList.addElement(st_test.nextToken()); }
					 */
				}
				case MEMBERSHIP_SUCCESS: {
					JOptionPane.showConfirmDialog(null, "회원가입에 실패했습니다.", "메세지", JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					break;
				}
				case ROOMLIST: {
					// StringTokenizer st_test2 = new StringTokenizer(st.nextToken(), SEPA);//new
					// StringTokenizer("USER1|USER2|USER3|USER4", SEPA);

					Vector roomList = new Vector<>();
					while (st.hasMoreTokens()) {
						roomList.addElement(st.nextToken());
					}

					UiWaitRoom.UserList.setListData(roomList);
					UiWaitRoom.setVisible(true);

					break;
				}
				case WAITUSERLIST: {
					StringTokenizer st1 = new StringTokenizer(st.nextToken(), ",");

					Vector userList = new Vector<>();
					while (st1.hasMoreTokens()) {
						userList.addElement(st1.nextToken());
					}

					UiWaitRoom.UserList.setListData(userList);

					UiWaitRoom.setVisible(true);

					break;
				}
				case CREATEROOM_SUCCESS: {
					UiWaitRoom.setVisible(false);

					if (UiChattingRoom == null) {
						UiChattingRoom = new UIChattingRoom(this);
					}
					UiChattingRoom.AdminID = UserID;
					UiChattingRoom.roomNo = Integer.parseInt(st.nextToken());
					UiChattingRoom.ClearData();
					UiChattingRoom.setVisible(true);

					break;
				}
				case JOINROOM_SUCCESS: {
					UiWaitRoom.setVisible(false);

					if (UiChattingRoom == null) {
						UiChattingRoom = new UIChattingRoom(this);
					}
					UiChattingRoom.ClearData();
					UiChattingRoom.setVisible(true);

					break;
				}

				case SENDWORD: {
					String id = st.nextToken();
					int roomNo = Integer.parseInt(st.nextToken());

					String data = st.nextToken();

					UIChattingRoom.taChatting.append(id + " : " + data + "\n");
					break;
				}

				case ROOMUSERLIST: {
					StringTokenizer st1 = new StringTokenizer(st.nextToken(), ",");
					Vector userlist = new Vector<>();
					while (st1.hasMoreTokens()) {
						userlist.addElement(st1.nextToken());
					}

					UiChattingRoom.listMember.setListData(userlist);
					;

					UiChattingRoom.taChatting.append(" ok ");
					break;
				}
				case LOGOUT_SUCCESS: {
					JOptionPane.showConfirmDialog(null, "로그아웃 성공", "메세지", JOptionPane.CLOSED_OPTION,
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
					break;
				}
				case LOGIN_FAIL: {
					JOptionPane.showConfirmDialog(null, "로그인 실패", "메세지", JOptionPane.CLOSED_OPTION,
							JOptionPane.ERROR_MESSAGE);
					break;
				}

				}
			}
		} catch (IOException e) {
			System.err.println(e);
			ThreadRelease();
		}
	}

	public void ThreadRelease() {
		if (selfThread != null) {
			selfThread = null;
		}

		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException e) {

		} finally {
			output = null;
		}

		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException e) {

		} finally {
			input = null;
		}

		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {

		} finally {
			socket = null;
		}
		System.exit(0);
	}

	public void login(String id, String password) {
		UserID = id;

		int ans = JOptionPane.showConfirmDialog(null, "'" + id + "'로 로그인을 하시겠습니까?", "메세지", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (ans == JOptionPane.YES_OPTION) {
			buf.setLength(0);
			buf.append(LOGIN_REQUEST);
			buf.append(SEPA);
			buf.append(id);
			buf.append(SEPA);
			buf.append(password);
			send(buf.toString());

			System.out.println("login" + buf.toString());
		} else if (ans == JOptionPane.NO_OPTION) {
			JOptionPane.showConfirmDialog(null, "접속을 종료합니다.", "메세지", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}

	public boolean memberShip(String id, String password) {
		int ans = JOptionPane.showConfirmDialog(null, "'" + id + "'로 로그인을 하시겠습니까?", "메세지", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (ans == JOptionPane.YES_OPTION) {
			buf.setLength(0);
			buf.append(MEMBERSHIP);
			buf.append(SEPA);
			buf.append(id);
			buf.append(SEPA);
			buf.append(password);
			send(buf.toString());
			System.out.println("login" + buf.toString());
		} else if (ans == JOptionPane.NO_OPTION) {
			JOptionPane.showConfirmDialog(null, "접속을 종료합니다.", "메세지", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		return false;

	}

	public void logOut() {
		buf.setLength(0);
		buf.append(LOGOUT_REQUEST);
		buf.append(SEPA);
		buf.append(UserID);
	}

	public void CreateRoom(String title, int MaxUser, int isRock, String password) {
		buf.setLength(0);
		buf.append(CREATEROOM_REQUEST);
		buf.append(SEPA);
		buf.append(UserID);
		buf.append(SEPA);
		buf.append(title);
		buf.append(SEPA);
		buf.append(MaxUser);
		buf.append(SEPA);
		buf.append(isRock);
		buf.append(SEPA);
		buf.append(password);
		send(buf.toString());
		System.out.println("CreateRoom" + buf.toString());
	}

	public void JoinChattingRoom(int roomNo, String password) {
		buf.setLength(0);
		buf.append(JOINROOM);
		buf.append(SEPA);
		buf.append(UserID);
		buf.append(SEPA);
		buf.append(roomNo);
		buf.append(SEPA);
		buf.append(password);
		send(buf.toString());
		System.out.println("JoinChattingRoom" + buf.toString());
	}

	public void SendWord(String msg) {
		buf.setLength(0);
		buf.append(SENDWORD);
		buf.append(SEPA);
		buf.append(UserID);
		buf.append(SEPA);
		buf.append(UiChattingRoom.roomNo);
		buf.append(SEPA);
		buf.append(msg);
		send(buf.toString());
		System.out.println("SendWord" + buf.toString());
	}

	public void send(String data) {
		try {
			output.writeUTF(data);
			output.flush();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
