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
    rogic rg;
    

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxMamber() {
		return MaxMamber;
	}

	public void setMaxMamber(int maxMamber) {
		MaxMamber = maxMamber;
	}

	public ChattingRoom(int roomNo, String UserID, String roomTitle, int roomMaxUser, int isRock, String roomPassword, rogic rg){
        this.roomTitle = roomTitle;
        this.AdminID= UserID;
        this.MaxMamber = roomMaxUser;
        this.isRock= isRock;
        this.password = roomPassword;
        this.roomNo = roomNo;
        this.rg = rg;
        
        users = new Hashtable(MaxMamber);
    }
    
	public String getUserIdData(String id) {
		serverThread data = (serverThread)users.get(id);
		String searchId = data.dto.getMemberId();
		String searchSex = data.dto.getMemberGender();
		String searchGrade = data.dto.getGrade();
		int searchRank = data.dto.getRank();
		int searchWin = data.dto.getWins();
		int searchLose = data.dto.getLoses();
		

		return searchId + "|" + searchSex + "|" + searchGrade + "|" + searchRank + "|" + searchWin + "|" + searchLose;
	}
	
    public boolean addUser(String id,serverThread serverThread){
        if(users.size()== MaxMamber)
            return false;
        
        users.put(id, serverThread);
        return true;
    }
    
    public boolean delUser(String id){
        users.remove(id);
        return users.isEmpty();
    }
    
    public String getUserList(){
        StringBuffer buf = new StringBuffer();
        String ids;
        Enumeration enu = users.keys();
        while(enu.hasMoreElements()){
            buf.append(enu.nextElement());
            buf.append(",");
        }
        
        try{
            ids = new String(buf);
            ids = ids.substring(0, ids.length() - 1);
        }catch(StringIndexOutOfBoundsException e){
            return "";
        }
        
        return ids;
    }
    
    public String getAdminID() {
		return AdminID;
	}

	public void setAdminID(String adminID) {
		AdminID = adminID;
	}

	public int getMemberCount() {
		return MemberCount;
	}

	public void setMemberCount(int memberCount) {
		MemberCount = memberCount;
	}

	public String toString(){
        StringBuffer buf = new StringBuffer();
        buf.setLength(0);
        buf.append(roomNo);
        buf.append(",");
        buf.append(roomTitle);
        buf.append(",");
        buf.append(MemberCount + "/" + MaxMamber);
        buf.append(",");
        if(isRock == 1)
            buf.append("비공개");
        else{
            buf.append("열림");
        }
        
        buf.append(",");
        buf.append(AdminID);
        
        return buf.toString();
    }
    
    public int GetRoomNo(){
        return roomNo;
    }
}
