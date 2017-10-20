
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
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.sql.CommonDataSource;

public class serverThread extends Thread implements CommonConstant {
	private boolean myTurn;
	private ArrayList<Card> myCard;
	rogic rg;
	private Thread th;
	String selectShape;
	private DataInputStream input;
	private DataOutputStream output;
	private StringBuffer buf;
	MemberDTO dto;
	MemberDAO dao;
	private Socket socket;
	private WaitingRoom waitingRoom;
	int roomNo;
	boolean gameLose;

	public serverThread(Socket socket) {
		try {
			gameLose = false;
			dao = MemberDAO.getInstance();
			dto = new MemberDTO();
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
		serverThread serverThread;
		Hashtable rooms = waitingRoom.getServerThread(roomNo);
		Enumeration enu = rooms.keys();
		while (enu.hasMoreElements()) {
			serverThread = (serverThread) rooms.get(enu.nextElement());
			serverThread.send(data);
		}
	}

	public String selectShape() {
		send(SELECT_SHAPE + SEPA);

		while (true) {
			String data;
			try {
				data = input.readUTF();
				StringTokenizer st = new StringTokenizer(data, SEPA);
				if (Integer.parseInt((st.nextToken())) == SELECT_SHAPE) {
					String str = st.nextToken();
					return str;
				}
			} catch (IOException e) {
				removeUser();
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
					String inputId = st.nextToken();
					String inputPassword = st.nextToken();

					buf.setLength(0);
					if (dao.login(inputId, inputPassword)) {
						buf.append(LOGIN_SUCCESS);
						buf.append(SEPA);

						dto = dao.GetClientInfo(inputId, inputPassword);
						System.out.println(dto.getMemberId());
						buf.append(dto.getMemberId());
						buf.append(SEPA);
						buf.append(dto.getMemberName());
						buf.append(SEPA);
						buf.append(dto.getMemberEmail());
						buf.append(SEPA);
						buf.append(dto.getMemberLocation());
						buf.append(SEPA);
						buf.append(dto.getMemberAge());
						buf.append(SEPA);
						buf.append(dto.getMemberGender());
						buf.append(SEPA);
						send(buf.toString());

						waitingRoom.addUser(dto.getMemberId(), this);

						SendUserList();
						SendRoomList();
					} else {
						buf.append(LOGIN_FAIL);
						buf.append(SEPA);
						send(buf.toString());
					}

					break;
				}
				case MEMBERSHIP: {
					String MemberId = st.nextToken();
					String MemberName = st.nextToken();
					String MemberGender = st.nextToken();
					int MemberAge = Integer.valueOf(st.nextToken());
					String MemberEmail = st.nextToken();
					String MemberLocation = st.nextToken();
					String MemberPassword = st.nextToken();
					/*
					 * if(md.memberExist(ID)) { buf.append(MEMBERSHIP_SUCCESS); buf.append(SEPA);
					 * }else { buf.append(MEMBERSHIP_FAIL); buf.append(SEPA); }
					 */
					if (dao.memberShipChk(MemberId)) { // 아이디가 있음.
						buf.setLength(0);
						buf.append(MEMBERSHIP_FAIL);

					} else { // 없을때
						System.out.println("회원 가입 요청 " + dto.getMemberId() + " 데이터베이스에 등록시도");
						buf.setLength(0);
						int countPlayer = dao.countPlayers();
						buf.append(MEMBERSHIP_SUCCESS);
						dao.insertMember(MemberId, MemberName, MemberGender, MemberAge, MemberEmail, MemberLocation,MemberPassword,countPlayer); // 데이타베이스에 회원 추가
						System.out.println("등록 성공");
					}
					send(buf.toString());
					break;
				}

				case LOGOUT_REQUEST: {
					send(buf.toString());

					buf.setLength(0);
					buf.append(LOGOUT_SUCCESS);
					send(buf.toString());

					new Exception();

					break;
				}
				case JOINROOM: {
					boolean rst;
					ChattingRoom cr;
					roomNo = Integer.parseInt(st.nextToken());
					System.out.println("UserID : " + dto.getMemberId());
					System.out.println("roomNo : " + roomNo);

					if (roomNo != 0) {
						rst = waitingRoom.joinRoom(this, dto.getMemberId(), roomNo, "");
						waitingRoom.delUser(dto.getMemberId());

						if (rst) {
							cr = waitingRoom.Join(roomNo);
							rg = cr.rg;
							rg.pl.add(this);
							cr.setMemberCount(cr.users.size());
							buf.setLength(0);
							buf.append(JOINROOM_SUCCESS);
							buf.append(SEPA);
							buf.append(cr.getAdminID());
							buf.append(SEPA);
							buf.append(roomNo);
							buf.append(SEPA);
							buf.append(cr.getMaxMamber());
							System.out.println("maxnum : " + cr.getMaxMamber());
							send(buf.toString());
							SendRoomUserList(roomNo);
						} else {
							send(JOINROOM_FAIL + SEPA);
						}
					}

					break;
				}
				case SENDWORD: {
					int roomNo = Integer.parseInt(st.nextToken());

					String msg;
					try {
						msg = st.nextToken();
					} catch (NoSuchElementException e) {
						msg = "";
					}

					buf.setLength(0);
					buf.append(SENDWORD);
					buf.append(SEPA);
					buf.append(dto.getMemberId());
					buf.append(SEPA);
					buf.append(roomNo);
					buf.append(SEPA);
					buf.append(msg);

					broadcast(buf.toString(), roomNo);
					break;
				}

				case CREATEROOM_REQUEST: {
					String roomTitle, roomPassword;
					int roomMaxUser, isRock;

					roomTitle = st.nextToken();
					roomMaxUser = Integer.parseInt(st.nextToken());
					isRock = Integer.parseInt(st.nextToken());
					roomPassword = st.nextToken();
					System.out.println("roommax : " + roomMaxUser);
					rg = new rogic(roomMaxUser);
					ChattingRoom chattingRoom = new ChattingRoom(waitingRoom.getRoomsCount() + 1, dto.getMemberId(),
							roomTitle, roomMaxUser, isRock, roomPassword, rg);
					rg.pl.add(this);
					if (waitingRoom.addRoom(chattingRoom) == 0) {
						chattingRoom.addUser(dto.getMemberId(), this);
						waitingRoom.delUser(dto.getMemberId());
						chattingRoom.setMemberCount(chattingRoom.users.size());

						buf.setLength(0);
						buf.append(CREATEROOM_SUCCESS);
						buf.append(SEPA);
						buf.append(chattingRoom.GetRoomNo());
						buf.append(SEPA);
						buf.append(chattingRoom.getMaxMamber());

						send(buf.toString());
						roomNo = chattingRoom.GetRoomNo();
						SendRoomList();

						SendRoomUserList(chattingRoom.GetRoomNo());

						System.out.println("'" + dto.getMemberId() + "' CREATEROOM_REQUEST");
					} else {
						send(String.valueOf(CREATEROOM_FAIL));
						System.out.println("'" + dto.getMemberId() + "' CREATEROOM_REQUEST");
					}
					break;
				}

				case SELECTCARD: {
					if (myTurn == true) {
						rg.cardCheck(st.nextToken());
					}
					break;
				}

				case CLOSECHATROOM: {

					String id;

					id = st.nextToken();
					removeUser();
					// chattingRoom.delUser(id);
					waitingRoom.addUser(id, this);

					buf.setLength(0);
					buf.append(CLOSECHATROOM_SUCCESS);
					buf.append(SEPA);
					send(buf.toString());
					if (waitingRoom.getRooms().get(roomNo) == null) {

					} else {
						SendRoomUserList(roomNo);
					}

					roomNo = 0;

					SendUserList();
					SendRoomList();

					break;
				}
				case FIRE: {
					String roomNo = st.nextToken();
					String FireId = st.nextToken();
					System.out.println(FireId);
					ChattingRoom server = waitingRoom.Join(Integer.parseInt(roomNo));
					serverThread serverTh = (serverThread) server.users.get(FireId);
					if (serverTh.rg != null) {
						System.out.println("zz");
						serverTh.rg.pl.remove(serverTh);
						serverTh.rg = null;
					}
					serverTh.roomNo = 0;

					waitingRoom.removeChattingUser(FireId, Integer.parseInt(roomNo));
					waitingRoom.addUser(FireId, serverTh);

					buf.setLength(0);
					buf.append(CLOSECHATROOM_SUCCESS);
					buf.append(SEPA);
					serverTh.send(buf.toString());

					SendRoomUserList(Integer.parseInt(roomNo));
					SendUserList();
					SendRoomList();
					break;
				}
				case IDCHECK: {
					MemberDAO dao = MemberDAO.getInstance();
					String name = st.nextToken();
					if (dao.memberShipChk(name)) { // 아이디가 있음.
						buf.setLength(0);
						buf.append(IDCHECK_SUCCESS + SEPA);
						send(buf.toString());
					} else { // 없을때
						buf.setLength(0);
						buf.append(IDCHECK_FAIL + SEPA);
						send(buf.toString());
					}
					break;
				} // end IDCHECK//////////////////////
				}
			}
		} catch (IOException e) {
			removeUser();
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

	public void removeUser() {
		if (rg != null) {
			rg.pl.remove(this);
			rg = null;
		}
		try {
			waitingRoom.delUser(dto.getMemberId());
			waitingRoom.removeChattingUser(dto.getMemberId(), roomNo);
		} catch (NullPointerException e) {
			System.out.println("지울 정보가 없습니다");

		}

		waitingRoom.removeRooms(roomNo);

	}
}
