package CharServer.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private MemberDAO() {

	}

	private static MemberDAO dao = new MemberDAO();

	public static MemberDAO getInstance() {
		return dao;
	}

	private Connection init() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.OracleDriver");

		String url = "jdbc:oracle:thin://@127.0.0.1:1521:xe";
		String username = "hr";
		String password = "a1234";
		conn = DriverManager.getConnection(url, username, password);

		return conn;
	}

	private void exit() throws SQLException {
		if (rs != null)
			rs.close();

		if (stmt != null)
			stmt.close();

		if (pstmt != null)
			pstmt.close();

		if (conn != null)
			conn.close();
	}

	public void insertMember(MemberDTO memDTO) {
		try {
			conn = init();
			String sql = "INSERT INTO member (mem_code, mem_id, mem_name, mem_password) VALUES (mem_code_seq.nextval, ?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memDTO.getMemberId());
			pstmt.setString(2, memDTO.getMemberPassword());
			pstmt.executeQuery();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	} // 회원가입

	public boolean loginEx(String name, String password) {
		ArrayList<MemberDTO> aList = new ArrayList<MemberDTO>();
		aList = (ArrayList<MemberDTO>) memberList();

		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getMemberId().trim().equals(name)) {
				if (aList.get(i).getMemberPassword().equals(password)) { // 비밀번호 체크
					System.out.println(aList.get(i).getMemberPassword());
					return true;
				} else {
					break;
					// 비밀번호 오류 , 서버에서 오류메세지 전송
				}
			}
		}

		return false;
	} // 회원 존재 여부 확인

	public boolean memberShipEx(String name) {
		ArrayList<MemberDTO> aList = new ArrayList<MemberDTO>();
		aList = (ArrayList<MemberDTO>) memberList();

		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getMemberId().trim().equals(name))
				return true;
		}

		return false;
	} // 회원 존재 여부 확인

	public List<MemberDTO> memberList() {
		List<MemberDTO> aList = new ArrayList<MemberDTO>();
		try {
			conn = init();
			stmt = conn.createStatement();
			String sql = "SELECT *" + " FROM member";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				MemberDTO mdto = new MemberDTO();

				mdto.setMemberId(rs.getString("mem_id"));
				mdto.setMemberPassword(rs.getString("mem_password"));
				aList.add(mdto);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				exit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return aList;
	} // 회원 조회
}
