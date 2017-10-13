package db;

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
			pstmt.setString(2, memDTO.getMemberName());
			pstmt.setInt(3, memDTO.getMemberPassword());
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

	}

	public boolean memberExist(MemberDTO memDTO) {
		ArrayList<MemberDTO> aList = new ArrayList<MemberDTO>();
		aList = (ArrayList<MemberDTO>) memberList();

		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getMemberId().trim().equals(memDTO.getMemberId().trim()))
				return true;
		}

		return false;
	}

	public List<MemberDTO> memberList() {
		List<MemberDTO> aList = new ArrayList<MemberDTO>();
		try {
			conn = init();
			stmt = conn.createStatement();
			String sql = "SELECT *" + " FROM member";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				MemberDTO mdto = new MemberDTO();

				mdto.setMemberCode(rs.getInt("mem_code"));
				mdto.setMemberId(rs.getString("mem_id"));
				mdto.setMemberName(rs.getString("mem_name"));
				mdto.setMemberPassword(rs.getInt("mem_password"));
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
	}

}
