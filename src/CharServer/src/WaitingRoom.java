/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Enumeration;
import java.util.Hashtable;

public class WaitingRoom implements CommonConstant{
    
    private static Hashtable users;
    private static Hashtable rooms;
    
    private static final int ERR_USERFULL = -1;
    private static final int ERR_EXISTID = -2;
    private static final int ERR_ROOMFULL = -3;
    
    static{
        users = new Hashtable(100);  
        rooms = new Hashtable(10);
    }
    
    public static Hashtable getUsers() {
		return users;
	}

	public static void setUsers(Hashtable users) {
		WaitingRoom.users = users;
	}

	public static Hashtable getRooms() {
		return rooms;
	}

	public static void setRooms(Hashtable rooms) {
		WaitingRoom.rooms = rooms;
	}

	public WaitingRoom(){ }
    
    public int addUser(String id, serverThread serverThread){
            if(users.size() == 100){
                return ERR_USERFULL;
            }
            Enumeration ids = users.keys();
            while(ids.hasMoreElements()){
                String tempID = (String)ids.nextElement();
                if(tempID.equals(id))return ERR_EXISTID;
            }
            
            users.put(id, serverThread);
            
            return 0;
    }
    
    public void delUser(String id){
        users.remove(id);
    }
    
    public int addRoom(ChattingRoom chattingRoom){
        if(rooms.size() == 10)
            return ERR_USERFULL;
        
        rooms.put(chattingRoom.GetRoomNo(), chattingRoom);
        
        return 0;  
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
    
    public String getRoomList(){
        StringBuffer buf = new StringBuffer();
        String roomsInfo;
        Integer roomNo;
        Enumeration enu = rooms.keys();
        while(enu.hasMoreElements()){
            roomNo = (Integer)enu.nextElement();
            ChattingRoom room = (ChattingRoom)rooms.get(roomNo);
            buf.append(room.toString());
            buf.append(SEPA);
        }
        
         try{
            roomsInfo = new String(buf);
            roomsInfo = roomsInfo.substring(0, roomsInfo.length() - 1);
            
        }catch(StringIndexOutOfBoundsException e){
            return "";
        }
         System.out.println("room : " + roomsInfo);
         return roomsInfo;
    }
    
    public String getRoomInfo(int roomNuber){
        ChattingRoom chattingRoom = (ChattingRoom)rooms.get(roomNuber);
        return chattingRoom.getUserList();
    }
    
    public Hashtable getServerThread(int roomNo){
        if(roomNo == 0){           
            return users;
        }
        ChattingRoom room = (ChattingRoom)rooms.get(roomNo);
         
        return room.users;
    }
    
    public int getRoomsCount(){
        return rooms.size();
    }
    
    public boolean joinRoom(serverThread serverThread, String UserID, int roomNo, String password){
        ChattingRoom chattingRoom = (ChattingRoom)rooms.get(roomNo);
        if(chattingRoom.addUser(UserID, serverThread))
        	return true;
        else
        	return false;
    }
    
    public ChattingRoom Join(int roomNo){
        ChattingRoom chattingRoom = (ChattingRoom)rooms.get(roomNo);
        return chattingRoom;
    }
    
    public void removeRooms(int roomNo) {
    	if(roomNo != 0) {
    		ChattingRoom chattingRoom = (ChattingRoom)rooms.get(roomNo);
       	 	if(chattingRoom.users.size() <= 0) {
       	 		rooms.remove(roomNo);
       	 	}	
    	}
    }
    
    public void removeChattingUser(String id, int roomNo) {
    	if(roomNo != 0) {
    		ChattingRoom chattingRoom = (ChattingRoom)rooms.get(roomNo);
    		
    		chattingRoom.delUser(id);
    	   	chattingRoom.setMemberCount(chattingRoom.users.size());
    		
    		if(chattingRoom.getAdminID().equals(id)) {
    			Enumeration enu = chattingRoom.users.keys();
    			while(enu.hasMoreElements()) {
    				chattingRoom.setAdminID(enu.nextElement().toString());
    			}
 
    			Enumeration enu2 = chattingRoom.users.keys();
    			while(enu2.hasMoreElements()) {
    				serverThread a = (serverThread) chattingRoom.users.get(enu2.nextElement());
    				a.send(ADMIN_RESET + SEPA + chattingRoom.getAdminID());
    			}
 
    		}  
    	}else if(roomNo==0) {
    		delUser(id);
    	}
    }
}
