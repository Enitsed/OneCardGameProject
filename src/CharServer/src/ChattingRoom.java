package CharServer.src;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.lang.reflect.Member;
import java.util.Enumeration;
import java.util.Hashtable;

public class ChattingRoom {
	public Hashtable users;
	private String roomTitle;
	private int roomNo, MaxMamber, MemberCount;
	private String AdminID;
	private String password;
	private int isRock;

	public ChattingRoom(int roomNo, String UserID, String roomTitle, int roomMaxUser, int isRock, String roomPassword) {
		this.roomTitle = roomTitle;
		this.AdminID = AdminID;
		this.MaxMamber = roomMaxUser;
		this.isRock = isRock;
		this.password = roomPassword;
		this.roomNo = roomNo;

		users = new Hashtable(MaxMamber);
	}

	public boolean addUser(String id, serverThread serverThread) {
		if (users.size() == MaxMamber)
			return false;

		users.put(id, serverThread);
		return true;
	}

	public boolean delUser(String id) {
		users.remove(id);
		return users.isEmpty();
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

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.setLength(0);
		buf.append(roomNo);
		buf.append(",");
		buf.append(MemberCount);
		buf.append(",");
		buf.append(MaxMamber);
		buf.append(",");
		if (isRock == 1)
			buf.append("鍮꾧났媛�");
		else {
			buf.append("怨듦컻");
		}

		buf.append(",");
		buf.append(AdminID);

		return buf.toString();
	}

	public int GetRoomNo() {
		return roomNo;
	}
}
