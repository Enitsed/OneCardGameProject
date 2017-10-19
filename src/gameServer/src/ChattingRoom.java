package gameServer.src;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Enumeration;
import java.util.Hashtable;
import gameServer.src.GameLogic;

public class ChattingRoom {
	public Hashtable<MemberDTO, ServerThread> users;
	private String roomTitle;

	private int roomNo, maxMember, memberCount;
	private String adminId;
	private String password;
	private int isLocked;
	GameLogic lg;

	public ChattingRoom(int roomNo, MemberDTO dto, String roomTitle, int roomMaxUser, int isLocked, String roomPassword,
			GameLogic lg) {
		this.roomNo = roomNo;
		this.roomTitle = roomTitle;
		this.adminId = dto.getMemberId();
		this.maxMember = roomMaxUser;
		this.isLocked = isLocked;
		this.password = roomPassword;
		this.roomNo = roomNo;
		this.lg = lg;

		users = new Hashtable<MemberDTO, ServerThread>(maxMember);
	}

	public boolean addUser(MemberDTO dto, ServerThread serverThread) {
		if (users.size() == maxMember)
			return false;

		users.put(dto, serverThread);
		return true;
	}

	public boolean delUser(MemberDTO dto) {
		users.remove(dto);
		return users.isEmpty();
	}

	public String getUserList() {
		StringBuffer buf = new StringBuffer();
		String ids;
		Enumeration<MemberDTO> enu = users.keys();
		while (enu.hasMoreElements()) {
			buf.append(enu.nextElement());
			buf.append(",");
		}

		try {
			ids = new String(buf);
			ids = ids.substring(0, ids.length() - 1);
		} catch (StringIndexOutOfBoundsException e) {
			return "대기실 초과";
		}

		return ids;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.setLength(0);
		buf.append(roomNo);
		buf.append(",");
		buf.append(memberCount);
		buf.append(",");
		buf.append(maxMember);
		buf.append(",");
		if (isLocked == 1)
			buf.append("잠김");
		else {
			buf.append("열림");
		}

		buf.append(",");
		buf.append(adminId);

		return buf.toString();
	}

	public int GetRoomNo() {
		return roomNo;
	}

	public String getRoomTitle() {
		return roomTitle;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}
}
