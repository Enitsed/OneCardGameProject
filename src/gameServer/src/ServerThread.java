
package gameServer.src;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author gkdl
 */
public class ServerThread extends Thread implements CommonConstant {
	/*
	 * public static final String SEPA = "|"; public static final int LOGIN_REQUEST
	 * = 0; public static final int LOGIN_SUCCESS = 1; public static final int
	 * LOGIN_FAIL = 2; public static final int WAITUSERLIST = 3; public static final
	 * int LOGOUT_SUCCESS = 5; public static final int LOGOUT_REQUEST = 4; public
	 * static final int CREATEROOM_REQUEST = 6; public static final int
	 * CREATEROOM_SUCCESS = 7; public static final int CREATEROOM_FAIL = 8; public
	 * static final int ROOMLIST = 9;
	 */
	private boolean myTurn;
	private ArrayList<Card> myCard;
	GameLogic lg;

	private Thread th;
	String selectShape;

	private DataInputStream input;
	private DataOutputStream output;
	private StringBuffer buf;

	private Socket socket;
	private String loginId;

	private WaitingRoom waitingRoom;

	private static MemberDAO dao = MemberDAO.getInstance();

	public ServerThread(Socket socket) {
		try {
			selectShape = "";
			myTurn = false;
			myCard = new ArrayList<>();

			this.socket = socket;
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			buf = new StringBuffer(1024);
			waitingRoom = new WaitingRoom();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public boolean isMyTurn() {
		return myTurn;
	}

	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}

	public ArrayList<Card> getMyCard() {
		return myCard;
	}

	public void setMyCard(Card myCard) {
		this.myCard.add(myCard);
	}

	public void send(String data) {
		try {
			synchronized (output) {
				output.writeUTF(data);
				output.flush();
			}
		} catch (IOException e) {
		}
	}

	private synchronized void broadcast(String data, int roomNo) throws IOException {
		ServerThread serverThread;
		Hashtable<MemberDTO, ServerThread> rooms = waitingRoom.getServerThread(roomNo);
		Enumeration<MemberDTO> enu = rooms.keys();
		while (enu.hasMoreElements()) {
			serverThread = (ServerThread) rooms.get(enu.nextElement());
			serverThread.send(data);
		}
	}

	private void SendUserList() throws IOException {
		String clients = waitingRoom.getUserList();
		buf.setLength(0);
		buf.append(WAITUSERLIST);
		buf.append(SEPA);
		buf.append(clients);
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
					loginId = st.nextToken();
					String loginPassword = st.nextToken();

					MemberDTO dto = new MemberDTO();

					dto.setMemberId(loginId);
					dto.setMemberPassword(loginPassword);

					buf.setLength(0);

					if (dao.login(dto)) {
						buf.append(LOGIN_SUCCESS);
						buf.append(SEPA);
						send(buf.toString());
						dto = dao.GetClientInfo(dto);
						waitingRoom.addUser(dto, this);
						SendUserList();
						SendRoomList();
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

					buf.setLength(0);

					if (dao.memberShipChk(dto)) { // 아이디가 있음.
						buf.append(MEMBERSHIP_SUCCESS);
					} else { // 없을때
						System.out.println("회원 가입 요청 " + dto.getMemberId() + " 데이터베이스에 등록시도");
						dao.insertMember(dto); // 데이타베이스에 회원 추가
						buf.append(REGISTER_SUCCESS);
						System.out.println("등록 성공");
					}

					send(buf.toString());
					break;
				}

				case LOGOUT_REQUEST: {
					loginId = st.nextToken();
					System.out.println("'" + loginId + "' 로그아웃 되었습니다!\n");

					buf.setLength(0);
					buf.append(LOGOUT_SUCCESS);
					send(buf.toString());

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
					int roomMaxUser, isLocked;

					lg = new GameLogic();
					lg.pl.add(this);

					UserID = st.nextToken();
					roomTitle = st.nextToken();
					roomMaxUser = Integer.parseInt(st.nextToken());
					isLocked = Integer.parseInt(st.nextToken());
					roomPassword = st.nextToken();

					MemberDTO dto = new MemberDTO();

					ChattingRoom chattingRoom = new ChattingRoom(waitingRoom.getRoomsCount() + 1, dto, roomTitle,
							roomMaxUser, isLocked, roomPassword, lg);
					if (waitingRoom.addRoom(chattingRoom) == 0) {
						chattingRoom.addUser(dto, this);
						waitingRoom.delUser(dto);

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

				case SELECTCARD: {
					if (myTurn == true) {
						lg.cardCheck(st.nextToken());
					}
					break;
				}

				case SELECT_SHAPE: {
					if (myTurn == true) {
						System.out.println(st.nextToken());
						if (Integer.parseInt(st.nextToken()) == 0) {
							selectShape = "하트";
						} else if (Integer.parseInt(st.nextToken()) == 1) {
							selectShape = "클로버";
						} else if (Integer.parseInt(st.nextToken()) == 2) {
							selectShape = "스페이스";
						} else if (Integer.parseInt(st.nextToken()) == 3) {
							selectShape = "다이아";
						}
					}
					break;
				}
				} // end switch-case
			}
		} catch (IOException e) {
			System.out.println("접속자가 종료하였습니다.");

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
				System.out.println("소켓연결 오류");
			} finally {
				socket = null;
			}

			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e2) {
				System.out.println("입력 오류");
			} finally {
				input = null;
			}
		}
	}
}
