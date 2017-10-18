package gameServer.src;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Enumeration;
import java.util.Hashtable;

public class WaitingRoom implements CommonConstant {

	private static Hashtable<String, ServerThread> users;
	private static Hashtable<Integer, ChattingRoom> rooms;

	private static final int ERR_USERFULL = -1;
	private static final int ERR_EXISTID = -2;
	private static final int ERR_ROOMFULL = -3;

	private MemberDTO dto = new MemberDTO();

	static {
		users = new Hashtable<String, ServerThread>(100);
		rooms = new Hashtable<Integer, ChattingRoom>(10);
	}

	public WaitingRoom() {
	}

	public int addUser(MemberDTO dto, ServerThread serverThread) {
		if (users.size() == 100) {
			return ERR_USERFULL;
		}
		Enumeration ids = users.keys();
		while (ids.hasMoreElements()) {
			String tempID = (String) ids.nextElement();
			if (tempID.equals(dto.getMemberId()))
				return ERR_EXISTID;
		}

		users.put(dto.getMemberId(), serverThread);

		return 0;
	} // 로그인 성공시에 클라이언트 객체를 유저 리스트에 추가한다.

	public void delUser(MemberDTO dto) {
		users.remove(dto);
	} // 클라이언트 객체를 삭제한다.

	public int addRoom(ChattingRoom chattingRoom) {
		if (rooms.size() == 10)
			return ERR_USERFULL;

		rooms.put(chattingRoom.GetRoomNo(), chattingRoom);

		return 0;

	}

	public String getUserList() {
		StringBuffer buf = new StringBuffer();
		String ids;
		Enumeration enu = users.keys();

		while (enu.hasMoreElements()) {
			buf.append(enu.nextElement());
			buf.append(",");
		}
		try {
			ids = new String(buf);
			ids = ids.substring(0, ids.length() - 1);

		} catch (StringIndexOutOfBoundsException e) {
			return "";
		}
		return ids;
	}

	public String getRoomList() {
		StringBuffer buf = new StringBuffer();
		String roomsInfo;
		Integer roomNo;
		Enumeration enu = rooms.keys();
		while (enu.hasMoreElements()) {
			roomNo = (Integer) enu.nextElement();
			ChattingRoom room = (ChattingRoom) rooms.get(roomNo);
			buf.append(room.toString());
			buf.append(SEPA);
		}

		try {
			roomsInfo = new String(buf);
			roomsInfo = roomsInfo.substring(0, roomsInfo.length() - 1);

		} catch (StringIndexOutOfBoundsException e) {
			return "";
		}
		return roomsInfo;
	}

	public String getRoomInfo(int roomNo) {
		ChattingRoom chattingRoom = (ChattingRoom) rooms.get(roomNo);
		return chattingRoom.getUserList();
	}

	public Hashtable getServerThread(int roomNo) {
		if (roomNo == 0) {
			return users;
		}
		ChattingRoom room = (ChattingRoom) rooms.get(roomNo);

		return room.users;
	}

	public int getRoomsCount() {
		return rooms.size();
	}

	public boolean joinRoom(ServerThread serverThread, String UserID, int roomNo, String password) {
		ChattingRoom chattingRoom = (ChattingRoom) rooms.get(roomNo);

		chattingRoom.addUser(dto, serverThread);

		return true;
	}

	public ChattingRoom join(ServerThread serverThread, String UserID, int roomNo, String password) {
		ChattingRoom chattingRoom = (ChattingRoom) rooms.get(roomNo);

		return chattingRoom;
	}
}
