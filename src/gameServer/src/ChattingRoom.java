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
	GameLogic logic;

	public ChattingRoom(int roomNo, MemberDTO dto, String roomTitle, int roomMaxUser, int isLocked, String roomPassword,
			GameLogic logic) {
		this.roomNo = roomNo;
		this.roomTitle = roomTitle;
		this.adminId = dto.getMemberId();
		this.maxMember = roomMaxUser;
		this.isLocked = isLocked;
		this.password = roomPassword;
		this.roomNo = roomNo;
		this.logic = logic;

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
			return "buf 문자열 오류";
		}

		return ids;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.setLength(0);
		buf.append(roomNo);
		buf.append(",");
		buf.append(roomTitle);
		buf.append(",");
		buf.append(memberCount + "/" + maxMember);
		buf.append(",");
		if (isLocked == 1)
			buf.append("비공개");
		else {
			buf.append("공개");
		}

		buf.append(",");
		buf.append(adminId);

		return buf.toString();
	}

	public int getRoomNo() {
		return roomNo;
	}

}
