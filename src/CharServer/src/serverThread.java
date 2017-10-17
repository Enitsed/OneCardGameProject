
package CharServer.src;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author gkdl
 */
public class serverThread extends Thread implements CommonConstant {
	/*
	 * public static final String SEPA = "|"; public static final int LOGIN_REQUEST
	 * = 0; public static final int LOGIN_SUCCESS = 1; public static final int
	 * LOGIN_FAIL = 2; public static final int WAITUSERLIST = 3; public static final
	 * int LOGOUT_SUCCESS = 5; public static final int LOGOUT_REQUEST = 4; public
	 * static final int CREATEROOM_REQUEST = 6; public static final int
	 * CREATEROOM_SUCCESS = 7; public static final int CREATEROOM_FAIL = 8; public
	 * static final int ROOMLIST = 9;
	 */

	private DataInputStream input;
	private DataOutputStream output;
	private StringBuffer buf;

	private Socket socket;
	private String loginID;
	private WaitingRoom waitingRoom;

	public serverThread(Socket socket) {
		try {
			this.socket = socket;
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			buf = new StringBuffer(1024);
			waitingRoom = new WaitingRoom();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private void send(String data) {
		try {
			synchronized (output) {
				output.writeUTF(data);
				output.flush();
			}
		} catch (IOException e) {
		}
	}

	private synchronized void broadcast(String data, int roomNo) throws IOException {
		serverThread serverThread;
		Hashtable rooms = waitingRoom.getServerThread(roomNo);
		Enumeration enu = rooms.keys();
		while (enu.hasMoreElements()) {
			serverThread = (serverThread) rooms.get(enu.nextElement());
			serverThread.send(data);
		}
	}

	private void SendUserList() throws IOException {
		String ids = waitingRoom.getUserList();
		buf.setLength(0);
		buf.append(WAITUSERLIST);
		buf.append(SEPA);
		buf.append(ids);
		broadcast(buf.toString(), 0);
	}

	private void SendRoomUserList(int roomNumber) throws IOException {
		String ids = waitingRoom.getRoomInfo(roomNumber);
		buf.setLength(0);
		buf.append(ROOMUSERLIST);
		buf.append(SEPA);
		buf.append(ids);
		broadcast(buf.toString(), roomNumber);
	}

	private void SendRoomList() throws IOException {
		String rooms = waitingRoom.getRoomList();
		buf.setLength(0);
		buf.append(ROOMLIST);
		buf.append(SEPA);
		buf.append(rooms);
		broadcast(buf.toString(), 0);

	}

	public void run() {
		try {
			while (true) {
				String data = input.readUTF();
				System.out.println(data);
				StringTokenizer st = new StringTokenizer(data, SEPA);
				int cmd = Integer.parseInt((st.nextToken()));
				switch (cmd) {
				case LOGIN_REQUEST: {
					loginID = st.nextToken();

					MemberDAO dao = MemberDAO.getInstance();
					MemberDTO dto = new MemberDTO();

					if (dao.login(dto)) {
						buf.append(LOGIN_SUCCESS);
						buf.append(SEPA);
						send(buf.toString());
						waitingRoom.addUser(loginID, this);
						SendUserList();
					} else {
						buf.append(LOGIN_FAIL);
						buf.append(SEPA);
						send(buf.toString());
					}

					break;
				}
				case REGISTER: {
					MemberDAO dao = MemberDAO.getInstance();
					MemberDTO dto = new MemberDTO();

					dto.setMemberId(st.nextToken());
					dto.setMemberName(st.nextToken());
					dto.setMemberGender(st.nextToken());
					dto.setMemberAge(Integer.valueOf(st.nextToken()));
					dto.setMemberEmail(st.nextToken());
					dto.setMemberLocation(st.nextToken());
					dto.setMemberPassword(st.nextToken());

					if (dao.memberShipChk(dto)) { // 아이디가 있음.
						buf.append(MEMBERSHIP_SUCCESS);
					} else { // 없을때
						dao.insertMember(dto); // 데이타베이스에 회원 추가
						buf.append(REGISTER_SUCCESS);
					}
					send(buf.toString());

					break;
				}

				case LOGOUT_REQUEST: {
					buf.setLength(0);
					buf.append(LOGOUT_SUCCESS);
					send(buf.toString());
					System.out.println("'" + loginID + "' 로그아웃 되었습니다!\n");

					new Exception();

					break;
				}
				case JOINROOM: {
					String UserID, password = "";
					int roomNo;
					boolean rst;

					UserID = st.nextToken();
					roomNo = Integer.parseInt(st.nextToken());
					rst = waitingRoom.joinRoom(this, UserID, roomNo, password);

					if (rst) {
						buf.setLength(0);
						buf.append(JOINROOM_SUCCESS);
						buf.append(SEPA);
						buf.append(roomNo);
						buf.append(SEPA);
						buf.append(UserID);
						send(buf.toString());

						SendRoomUserList(roomNo);
					} else {
						send(String.valueOf(JOINROOM_FAIL));
						System.out.println("'" + UserID + "' JOINROOM �떎�뙣");
					}
					break;
				}
				case SENDWORD: {
					String id = st.nextToken();
					int roomNo = Integer.parseInt(st.nextToken());

					String msg = st.nextToken();

					buf.setLength(0);
					buf.append(SENDWORD);
					buf.append(SEPA);
					buf.append(id);
					buf.append(SEPA);
					buf.append(roomNo);
					buf.append(SEPA);
					buf.append(msg);

					broadcast(buf.toString(), roomNo);
					break;
				}

				case CREATEROOM_REQUEST: {
					String UserID, roomTitle, roomPassword;
					int roomMaxUser, isRock;

					UserID = st.nextToken();
					roomTitle = st.nextToken();
					roomMaxUser = Integer.parseInt(st.nextToken());
					isRock = Integer.parseInt(st.nextToken());
					roomPassword = st.nextToken();

					ChattingRoom chattingRoom = new ChattingRoom(waitingRoom.getRoomsCount() + 1, UserID, roomTitle,
							roomMaxUser, isRock, roomPassword);
					if (waitingRoom.addRoom(chattingRoom) == 0) {
						chattingRoom.addUser(UserID, this);
						waitingRoom.delUser(UserID);

						buf.setLength(0);
						buf.append(CREATEROOM_SUCCESS);
						buf.append(SEPA);
						buf.append("1");
						send(buf.toString());

						SendRoomList();

						SendRoomUserList(chattingRoom.GetRoomNo());

						System.out.println("'" + UserID + "' CREATEROOM_REQUEST �꽦怨�");
					} else {
						send(String.valueOf(CREATEROOM_FAIL));
						System.out.println("'" + UserID + "' CREATEROOM_REQUEST �꽦怨�");
					}
				}
				}
			}
		} catch (IOException e) {
			System.err.println("�뿰寃� 醫낅즺" + "\n[" + e + "\n");

			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e2) {

			} finally {
				output = null;
			}

			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e2) {

			} finally {
				socket = null;
			}

			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e2) {

			} finally {
				input = null;
			}
		}
	}
}
