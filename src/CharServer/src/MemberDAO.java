
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

	public void insertMember(String id, String name, String sex, int age, String email, String location,
			String password, int countPlayer) {
		try {
			System.out.println("password : " + id);
			System.out.println("password : " + name);
			System.out.println("password : " + sex);
			System.out.println("password : " + age);
			System.out.println("password : " + email);
			System.out.println("password : " + location);

			System.out.println("password : " + password);
			conn = init();
			String sql = "INSERT ALL " + "INTO mem_info (id, name, sex, age, email, location, join_date, password) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
					+ "INTO win_lose (id, ranking, win_rate, win_count, lose_count) " + "VALUES (?, ?, ?, ?, ?) "
					+ "SELECT * FROM DUAL";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, sex);
			pstmt.setInt(4, age);
			pstmt.setString(5, email);
			pstmt.setString(6, location);
			pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(8, password);

			// 회원 가입시 승패 테이블 삽입
			pstmt.setString(9, id);
			pstmt.setInt(10, countPlayer); // ranking
			pstmt.setInt(11, 0); // win_rate
			pstmt.setInt(12, 0); // win_count
			pstmt.setInt(13, 0); // lose_count

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

	public boolean login(String inputId, String inputPassword) {

		ArrayList<MemberDTO> aList = new ArrayList<MemberDTO>();
		aList = (ArrayList<MemberDTO>) memberList();

		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getMemberId().trim().equals(inputId)) {
				if (aList.get(i).getMemberPassword().equals(inputPassword)) { // 비밀번호 확인
					return true; // 로그인 성공
				} else {
					break; // 로그인 실패 비밀번호 오류
				}
			}
		} // 로그인 아이디 및 비밀번호 확인

		return false;
	} // 로그인 메서드

	public MemberDTO GetClientInfo(String inputId, String inputPassword) {
		MemberDTO dto = new MemberDTO();
		try {
			conn = init();
			String sql = "SELECT * FROM mem_info m WHERE m.id = ? AND m.password = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputId);
			pstmt.setString(2, inputPassword);
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

	public boolean memberShipChk(String inputId) {

		ArrayList<MemberDTO> aList = new ArrayList<MemberDTO>();
		aList = (ArrayList<MemberDTO>) memberList();

		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getMemberId().trim().equals(inputId))
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

	public void WinupdatePlayerInfo(MemberDTO dto , serverThread sv) {

		try {
			System.out.println("win : " + dto.getWins() + 1);

			conn = init();
			String sql = "UPDATE win_lose SET win_rate=?, win_count=?, lose_count=?, ranking=? WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setFloat(1, dto.calRate());
			pstmt.setInt(2, dto.getWins() + 1);
			sv.dto.setWins(dto.getWins() + 1);
			pstmt.setInt(3, dto.getLoses());
			pstmt.setInt(4, dto.getRank());
			pstmt.setString(5, dto.getMemberId());

			pstmt.executeQuery();
			System.out.println("win : " + dto.getWins() + 1);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				exit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} // 게임 끝난 후 디비 업데이트

	public void LoseupdatePlayerInfo(MemberDTO dto,  serverThread sv) {

		try {
			conn = init();
			String sql = "UPDATE win_lose SET win_rate=?, win_count=?, lose_count=?, ranking=? WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setFloat(1, dto.calRate());
			pstmt.setInt(2, dto.getWins());
			pstmt.setInt(3, dto.getLoses() + 1);
			sv.dto.setLoses(dto.getLoses() + 1);

			pstmt.setInt(4, dto.getRank());
			pstmt.setString(5, dto.getMemberId());

			pstmt.executeQuery();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				exit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} // 게임 끝난 후 디비 업데이트
	
	public int countPlayers() {
		int counts = 0;
		try {
			conn = init();
			stmt = conn.createStatement();
			String sql = "select count(id) from mem_info";

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				counts = rs.getInt("count(id)");
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
		System.out.println(counts);
		return counts + 1;
	}

}
