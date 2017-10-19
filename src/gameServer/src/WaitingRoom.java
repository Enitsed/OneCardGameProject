package gameServer.src;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Enumeration;
import java.util.Hashtable;

public class WaitingRoom implements CommonConstant {

	private static Hashtable<MemberDTO, ServerThread> users;
	private static Hashtable<Integer, ChattingRoom> rooms;

	private static final int ERR_USERFULL = -1;
	private static final int ERR_EXISTID = -2;
	private static final int ERR_ROOMFULL = -3;
	public MemberDTO dto;

	static {
		users = new Hashtable<MemberDTO, ServerThread>(100);
		rooms = new Hashtable<Integer, ChattingRoom>(10);
	}

	public WaitingRoom() {
	}

	public int addUser(MemberDTO dto, ServerThread serverThread) {
		if (users.size() == 100) {
			return ERR_USERFULL;
		}
		Enumeration<MemberDTO> ids = users.keys();
		while (ids.hasMoreElements()) {
			String idCheck = ids.nextElement().getMemberId();
			if (idCheck.equals(dto.getMemberId()))
				return ERR_EXISTID;
		}

		users.put(dto, serverThread);
		return 0;
	} // 로그인 성공시에 클라이언트 객체를 유저 리스트에 추가한다.

	public void delUser(MemberDTO dto) {
		users.remove(dto);
	} // 클라이언트 객체를 삭제한다.

	public int addRoom(ChattingRoom chattingRoom) {
		if (rooms.size() == 10)
			return ERR_USERFULL;

		rooms.put(chattingRoom.getRoomNo(), chattingRoom);

		return 0;

	}

	public String getUserList() {
		StringBuffer buf = new StringBuffer();
		Enumeration<MemberDTO> eNum = users.keys();
		String dtos = "";
		buf.setLength(0);

		while (eNum.hasMoreElements()) {
			dto = eNum.nextElement();
			buf.append(dto.getMemberId() + ":" + dto.getMemberName() + ":" + dto.getMemberGender() + ":"
					+ dto.getMemberAge() + ":" + dto.getMemberEmail() + ":" + dto.getMemberLocation() + ":"
					+ dto.getMemberJoinDate() + ":" + dto.getMemberPassword());
			buf.append(",");
		}
		try {
			dtos = new String(buf);
			dtos = dtos.substring(0, dtos.length() - 1);
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("getUserList() 메서드 문자열 오류");
		}
		return dtos;
	} // 대기실에 있는 유저 객체정보들을 스트링으로 반환한다.

	public String getRoomList() {
		StringBuffer buf = new StringBuffer();
		int roomNo;
		String roomTitle;
		Enumeration<Integer> eNum = rooms.keys();
		while (eNum.hasMoreElements()) {
			roomNo = (int) eNum.nextElement();
			ChattingRoom room = (ChattingRoom) rooms.get(roomNo);
			buf.append(room.toString());
			buf.append(SEPA);
		}

		try {
			roomTitle = new String(buf);
			roomTitle = roomTitle.substring(0, roomTitle.length() - 1);

		} catch (StringIndexOutOfBoundsException e) {
			return "";
		}
		return roomTitle;
	}

	public String getRoomInfo(int roomNo) {
		ChattingRoom chattingRoom = (ChattingRoom) rooms.get(roomNo);
		return chattingRoom.getUserList();
	}

	public Hashtable<MemberDTO, ServerThread> getServerThread(int roomNo) {
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

	public ChattingRoom join(ServerThread serverThread, String UserID, String roomNo, String password) {
		ChattingRoom chattingRoom = (ChattingRoom) rooms.get(roomNo);

		return chattingRoom;
	}

	public void removeRooms(String roomTitle) {
		if (roomTitle != null) {
			ChattingRoom chattingRoom = (ChattingRoom) rooms.get(roomTitle);
			if (chattingRoom.users.size() <= 0) {
				rooms.remove(roomTitle);
			}
		}
	}

	public void removeChattingUser(MemberDTO dto, String roomTitle) {
		if (roomTitle != null) {
			ChattingRoom chattingRoom = (ChattingRoom) rooms.get(roomTitle);
			chattingRoom.delUser(dto);
		}
	}
}
