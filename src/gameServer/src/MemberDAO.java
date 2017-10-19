package gameServer.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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

		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";

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

	public void insertMember(MemberDTO dto) {
		try {
			conn = init();
			String sql = "INSERT INTO mem_info (id, name, sex, age, email, location, join_date, password) VALUES (?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getMemberName());
			pstmt.setString(3, dto.getMemberGender());
			pstmt.setInt(4, dto.getMemberAge());
			pstmt.setString(5, dto.getMemberEmail());
			pstmt.setString(6, dto.getMemberLocation());
			pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(8, dto.getMemberPassword());

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
	} // 회원가입 메서드

	public boolean login(MemberDTO dto) {

		ArrayList<MemberDTO> aList = new ArrayList<MemberDTO>();
		aList = (ArrayList<MemberDTO>) memberList();

		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getMemberId().trim().equals(dto.getMemberId())) {
				if (aList.get(i).getMemberPassword().equals(dto.getMemberPassword())) { // 비밀번호 확인
					return true; // 로그인 성공
				} else {
					break; // 로그인 실패 비밀번호 오류
				}
			}
		} // 로그인 아이디 및 비밀번호 확인

		return false;
	} // 로그인 메서드

	public MemberDTO GetClientInfo(MemberDTO dto) {
		try {
			conn = init();
			String sql = "SELECT * FROM mem_info m WHERE m.id = ? AND m.password = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getMemberPassword());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dto.setMemberId(rs.getString("id"));
				dto.setMemberPassword(rs.getString("password"));
				dto.setMemberName(rs.getString("name"));
				dto.setMemberGender(rs.getString("sex"));
				dto.setMemberAge(rs.getInt("age"));
				dto.setMemberEmail(rs.getString("email"));
				dto.setMemberLocation(rs.getString("location"));
				dto.setMemberJoinDate(rs.getDate("join_date"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (dto.getMemberId().equals(null)) {
			System.exit(0);
		}
		return dto;
	}

	public boolean memberShipChk(MemberDTO dto) {

		ArrayList<MemberDTO> aList = new ArrayList<MemberDTO>();
		aList = (ArrayList<MemberDTO>) memberList();

		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getMemberId().trim().equals(dto.getMemberId()))
				return true; // 있습니다.
		}

		return false; // 없습니다.
	} // 회원인지 아닌지 확인 ID로 조회

	public List<MemberDTO> memberList() {
		List<MemberDTO> aList = new ArrayList<MemberDTO>();
		try {
			conn = init();
			stmt = conn.createStatement();
			String sql = "SELECT *" + " FROM mem_info";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();

				dto.setMemberId(rs.getString("id"));
				dto.setMemberName(rs.getString("name"));
				dto.setMemberGender(rs.getString("sex"));
				dto.setMemberAge(rs.getInt("age"));
				dto.setMemberEmail(rs.getString("email"));
				dto.setMemberLocation(rs.getString("location"));
				dto.setMemberJoinDate(rs.getDate("join_date"));
				dto.setMemberPassword(rs.getString("password"));

				aList.add(dto);
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
	} // 멤버 리스트 조회
}
