
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
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " + "INTO win_lose (id, win_rate, win_count, lose_count) "
					+ "VALUES (?, ?, ?, ?) " + "INTO grade_info (id, grade, rank_score, ranking) "
					+ "VALUES (?, ?, ?, ?) " + "SELECT * FROM DUAL";
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
			pstmt.setInt(10, 0); // win_rate
			pstmt.setInt(11, 0); // win_count
			pstmt.setInt(12, 0); // lose_count

			// 등급, 랭크점수, 랭크 삽입
			pstmt.setString(13, id);
			pstmt.setString(14, "짐승"); // grade
			pstmt.setInt(15, 0); // rank_score
			pstmt.setInt(16, countPlayer); // ranking

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

	public void WinupdatePlayerInfo(MemberDTO dto, serverThread sv) {

		try {
			System.out.println("win : " + dto.getWins() + 1);

			conn = init();
			String sql = "UPDATE win_lose SET win_rate=?, win_count=? WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setFloat(1, dto.calRate());
			pstmt.setInt(2, dto.getWins() + 1);
			sv.dto.setWins(dto.getWins() + 1);
			pstmt.setString(3, dto.getMemberId());

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
		

	public void LoseupdatePlayerInfo(MemberDTO dto, serverThread sv) {

		try {
			conn = init();
			String sql = "UPDATE win_lose SET win_rate=?, lose_count=? WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setFloat(1, dto.calRate());
			pstmt.setInt(2, dto.getLoses() + 1);
			sv.dto.setLoses(dto.getLoses() + 1);
			pstmt.setString(3, dto.getMemberId());

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
	
	public void WinRankUpdate(MemberDTO dto, serverThread sv) {
		try {
			conn = init();
			String sql = "UPDATE grade_info SET rank_score=? WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getRank_score() + 20);
			sv.dto.setRank_score(dto.getRank_score() + 20);
			pstmt.setString(2, dto.getMemberId());

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
	}

	
	public void LoseRankUpdate(MemberDTO dto, serverThread sv) {
		try {
			conn = init();
			String sql = "UPDATE grade_info SET rank_score=? WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getRank_score() - 10);
			sv.dto.setRank_score(dto.getRank_score() - 10);
			pstmt.setString(2, dto.getMemberId());

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
	}
	public void gradeUpdate1() {

	      try {
	         String grade = "짐승";
	         int scoreLast = 100;

	         conn = init();
	         String sql = "UPDATE grade_info SET grade = ? WHERE rank_score < ?";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, grade);
	         pstmt.setInt(2, scoreLast);

	         pstmt.executeQuery();
	      } catch (ClassNotFoundException |

	            SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            exit();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	   } // 게임 끝난 후 디비 업데이트

	   public void gradeUpdate2() {

	      try {
	         String grade = "노비";
	         int scoreStart = 100, scoreLast = 500;

	         conn = init();
	         String sql = "UPDATE grade_info SET grade = ? WHERE rank_score BETWEEN ? AND ?";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, grade);
	         pstmt.setInt(2, scoreStart);
	         pstmt.setInt(3, scoreLast);

	         pstmt.executeQuery();
	      } catch (ClassNotFoundException |

	            SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            exit();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	   } // 게임 끝난 후 디비 업데이트

	   public void gradeUpdate3() {

	      try {
	         String grade = "평민";
	         int scoreStart = 500, scoreLast = 1000;

	         conn = init();
	         String sql = "UPDATE grade_info SET grade = ? WHERE rank_score BETWEEN ? AND ?";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, grade);
	         pstmt.setInt(2, scoreStart);
	         pstmt.setInt(3, scoreLast);

	         pstmt.executeQuery();
	      } catch (ClassNotFoundException |

	            SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            exit();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	   } // 게임 끝난 후 디비 업데이트

	   public void gradeUpdate4() {

	      try {
	         String grade = "양반";
	         int scoreStart = 1000, scoreLast = 5000;

	         conn = init();
	         String sql = "UPDATE grade_info SET grade = ? WHERE rank_score BETWEEN ? AND ?";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, grade);
	         pstmt.setInt(2, scoreStart);
	         pstmt.setInt(3, scoreLast);

	         pstmt.executeQuery();
	      } catch (ClassNotFoundException |

	            SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            exit();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	   } // 게임 끝난 후 디비 업데이트

	   public void gradeUpdate5() {

	      try {
	         String grade = "왕족";
	         int scoreStart = 5000, scoreLast = 10000;

	         conn = init();
	         String sql = "UPDATE grade_info SET grade = ? WHERE rank_score BETWEEN ? AND ?";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, grade);
	         pstmt.setInt(2, scoreStart);
	         pstmt.setInt(3, scoreLast);

	         pstmt.executeQuery();
	      } catch (ClassNotFoundException |

	            SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            exit();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	   } // 게임 끝난 후 디비 업데이트

	   public void gradeUpdate6() {

	      try {
	         String grade = "왕";
	         int scoreStart = 10000, scoreLast = 50000;

	         conn = init();
	         String sql = "UPDATE grade_info SET grade = ? WHERE rank_score BETWEEN ? AND ?";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, grade);
	         pstmt.setInt(2, scoreStart);
	         pstmt.setInt(3, scoreLast);

	         pstmt.executeQuery();
	      } catch (ClassNotFoundException |

	            SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            exit();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	   } // 게임 끝난 후 디비 업데이트
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
			String sql = "SELECT * FROM mem_info m, win_lose w,grade_info g WHERE m.id = ? AND m.password = ? AND m.id = w.id AND m.id=g.id";
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
				dto.setWins(rs.getInt("WIN_COUNT"));
				dto.setLoses(rs.getInt("LOSE_COUNT"));
				dto.setRank(rs.getInt("ranking"));
				dto.setRank_score(rs.getInt("rank_score"));
				dto.setGrade(rs.getString("grade"));
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

	///////// 승패 관련 메소드

	public void updateRanking() {
		try {
			conn = init();
			String sql = "UPDATE grade_info a " + "SET ranking = (SELECT rn "
					+ "FROM (SELECT id, ranking, ROWNUM rn FROM (select a.id, ranking, rank_score from mem_info a, win_lose b, grade_info c WHERE a.id = b.id AND b.id = c.id ORDER BY c.rank_score desc)) "
					+ "WHERE id = a.id)";
			pstmt = conn.prepareStatement(sql);

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
	} // 랭킹업데이트

	public Vector<MemberDTO> getWinRateInfo(MemberDTO winDTO) {
		Vector<MemberDTO> memberList = new Vector<>();

		try {
			conn = init();
			stmt = conn.createStatement();
			String sql = "select m.id, m.name, m.location, r.ranking, r.win_rate, r.win_count, r.lose_count \r\n"
					+ "from MEM_INFO m , WIN_LOSE r where m.id = r.id";

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				winDTO.setMemberId(rs.getString("m.id"));
				winDTO.setMemberName(rs.getString("m.name"));
				winDTO.setMemberLocation(rs.getString("m.location"));
				winDTO.setRank(rs.getInt("r.ranking"));
				winDTO.setWinRate(rs.getFloat("r.win_rate"));
				winDTO.setWins(rs.getInt("r.win_count"));
				winDTO.setLoses(rs.getInt("r.lose_count"));

				memberList.add(winDTO);
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

		return memberList;
	}

	public MemberDTO getPlayerWinRateInfo(MemberDTO winDTO) {

		try {
			conn = init();
			stmt = conn.createStatement();
			String sql = "select m.id, m.name, m.location, r.ranking, r.win_rate, r.win_count, r.lose_count \r\n"
					+ "from MEM_INFO m , WIN_LOSE r where m.id = r.id and m.id = " + winDTO.getMemberId();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				winDTO.setMemberId(rs.getString("m.id"));
				winDTO.setMemberName(rs.getString("m.name"));
				winDTO.setMemberLocation(rs.getString("m.location"));
				winDTO.setRank(rs.getInt("r.ranking"));
				winDTO.setWinRate(rs.getFloat("r.win_rate"));
				winDTO.setWins(rs.getInt("r.win_count"));
				winDTO.setLoses(rs.getInt("r.lose_count"));
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
		return winDTO;
	}

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
