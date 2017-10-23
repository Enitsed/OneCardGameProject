import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ClientThread extends Thread implements CommonConstant {

	/*
	 * private static final String SEPA = "|"; private static final int
	 * LOGIN_REQUEST = 0; private static final int LOGIN_SUCCESS = 1; private static
	 * final int LOGIN_FAIL = 2; private static final int LOGOUT_SUCCESS = 3;
	 * private static final int LOGOUT_REQUEST = 4;
	 */
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private StringBuffer buf;
	MemberDTO dto;
	private Thread selfThread;

	private UIWaitRoom UiWaitRoom;
	private UIChattingRoom UiChattingRoom;
	private Login lg;

	// 회원 정보 조회 데이터
	public String searchId;
	public String searchSex;
	public String searchGrade;
	public String searchWin;
	public String searchLose;
	public String searchValue;

	public ClientThread() {
		try {
			socket = new Socket(InetAddress.getLocalHost(), 555);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			buf = new StringBuffer(1024);

			lg = new Login(this);

			selfThread = this;
		} catch (IOException e) {
			System.exit(0);
		}
	}

	public void sendFire(String FireId) {
		buf.setLength(0);
		buf.append(FIRE);
		buf.append(SEPA);
		buf.append(UiChattingRoom.roomNo);
		buf.append(SEPA);
		buf.append(FireId);
		send(buf.toString());
	}

	public void run() {
		try {
			Thread currThread = Thread.currentThread();
			while (selfThread == currThread) {
				String recvData = input.readUTF();
				System.out.println("Date : " + recvData);
				StringTokenizer st = new StringTokenizer(recvData, SEPA);
				int cmd = Integer.parseInt(st.nextToken());
				switch (cmd) {
				case LOGIN_SUCCESS: {
					Sound("src/login.wav");
					JOptionPane.showConfirmDialog(null, "로그인이 완료 되었습니다.", "메세지", JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					UiWaitRoom = new UIWaitRoom(this);
					String id = st.nextToken();
					String name = st.nextToken();
					String email = st.nextToken();
					String loc = st.nextToken();
					int age = Integer.parseInt(st.nextToken());
					String gender = st.nextToken();
					int rank = Integer.parseInt(st.nextToken());
					float winRate = Float.parseFloat(st.nextToken());
					int wins = Integer.parseInt(st.nextToken());
					int loses = Integer.parseInt(st.nextToken());

					dto = new MemberDTO();
					dto.setMemberId(id);
					dto.setMemberEmail(email);
					dto.setMemberName(name);
					dto.setMemberLocation(loc);
					dto.setMemberAge(age);
					dto.setMemberGender(gender);
					dto.setRank(rank);
					dto.setWinRate(winRate);
					dto.setWins(wins);
					dto.setLoses(loses);

					lg.dispose();
					break;
				}
				case MEMBERSHIP_SUCCESS: {
					JOptionPane.showConfirmDialog(null, "회원가입에 성공했습니다.", "메세지", JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					lg.registerFrame.dispose();
					break;
				}
				case ROOMLIST: {
					// StringTokenizer st_test2 = new StringTokenizer(st.nextToken(), SEPA);//new
					// StringTokenizer("USER1|USER2|USER3|USER4", SEPA);
					Vector roomList = new Vector<>();
					while (st.hasMoreTokens()) {
						roomList.addElement(st.nextToken());
					}

					UiWaitRoom.roomList.setListData(roomList);
					UiWaitRoom.show();

					break;
				}

				case WAITUSERLIST: {
					StringTokenizer st1 = new StringTokenizer(st.nextToken(), ",");

					Vector userList = new Vector();
					while (st1.hasMoreTokens()) {
						userList.addElement(st1.nextToken());
					}
					UiWaitRoom.UserList.setListData(userList);
					UiWaitRoom.show();

					break;
				}
				case CREATEROOM_SUCCESS: {
					UiWaitRoom.hide();

					if (UiChattingRoom == null) {
						UiChattingRoom = new UIChattingRoom(this);
					}
					UiChattingRoom.AdminID = dto.getMemberId();
					UiChattingRoom.roomNo = Integer.parseInt(st.nextToken());
					UiChattingRoom.setMaxMember(Integer.parseInt(st.nextToken()));
					UiChattingRoom.ClearData();

					UiChattingRoom.show();

					break;
				}
				case JOINROOM_SUCCESS: {
					UiWaitRoom.hide();

					if (UiChattingRoom == null) {
						UiChattingRoom = new UIChattingRoom(this);
					}
					UiChattingRoom.AdminID = st.nextToken();
					UiChattingRoom.roomNo = Integer.parseInt(st.nextToken());
					UiChattingRoom.setMaxMember(Integer.parseInt(st.nextToken()));
					UiChattingRoom.ClearData();
					UiChattingRoom.show();

					break;
				}
				case JOINROOM_FAIL: {
					JOptionPane.showConfirmDialog(null, "방 입장에 실패했습니다.", "메세지", JOptionPane.CLOSED_OPTION,
							JOptionPane.ERROR_MESSAGE);
					break;
				}
				case SENDWORD: {
					String id = st.nextToken();
					int roomNo = Integer.parseInt(st.nextToken());

					String data = st.nextToken();

					UIChattingRoom.taChatting.append(id + " : " + data + "\n");
					break;
				}
				case ROOMUSERLIST: {
					StringTokenizer st1 = new StringTokenizer(st.nextToken(), ",");
					Vector userlist = new Vector<>();
					while (st1.hasMoreTokens()) {
						userlist.addElement(st1.nextToken());
					}

					UiChattingRoom.listMember.setListData(userlist);

					UiChattingRoom.taChatting.append(" ok ");
					break;
				}
				case LOGOUT_SUCCESS: {
					JOptionPane.showConfirmDialog(null, "로그아웃 되셨습니다!", "메세지", JOptionPane.CLOSED_OPTION,
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
					break;
				}
				case LOGIN_FAIL: {
					JOptionPane.showConfirmDialog(null, "로그아웃에 실패했습니다", "메세지", JOptionPane.CLOSED_OPTION,
							JOptionPane.ERROR_MESSAGE);
					break;
				}
				case SELECT_SHAPE: {
					System.out.println("dl");
					UIChattingRoom.shapeChoice();
					break;
				}
				case MYCARD_INFORMATION: {
					String[] str2;
					if (st.hasMoreTokens() == false) {
						str2 = new String[0];
						UIChattingRoom.buttonSetting(str2);
					} else {
						str2 = new String[st.countTokens()];
						int num = st.countTokens();
						for (int i = 0; i < num; i++) {
							str2[i] = st.nextToken();
							System.out.println("MyCard : " + str2[i]);
						}
						UIChattingRoom.buttonSetting(str2);
					}
					break;
				}
				case SUBCARD_INFORMATION: {
					if (st.hasMoreTokens() == false) {
						UIChattingRoom.subCard.setIcon(new ImageIcon("src/img/BackCard.jpg"));
					} else {
						UIChattingRoom.subCard.setIcon(new ImageIcon("src/img/" + st.nextToken() + ".jpg"));
					}
					break;
				}
				case CLOSECHATROOM_SUCCESS: {
					UiWaitRoom = new UIWaitRoom(this);

					UiChattingRoom.dispose();

					JOptionPane.showConfirmDialog(null, "채팅방에서 나가셨습니다.", "메세지", JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					UiChattingRoom.taChatting.append("나가셨습니다 \n");
					break;
				}

				case ENEMYCARD_INFORMATION: {
					String inf = st.nextToken();
					int enemyTurn = Integer.parseInt(st.nextToken());
					UiChattingRoom.EnemyCard(Integer.parseInt(inf), enemyTurn);
					break;
				}
				case MY_TURN_NUM: {
					int num = Integer.parseInt(st.nextToken());
					UiChattingRoom.setMyTurn(num);
					break;
				}
				case NOW_TURN: {
					UiChattingRoom.nowTurn(Integer.parseInt(st.nextToken()));
					break;

				}
				case IDCHECK_SUCCESS: {
					JOptionPane.showMessageDialog(null, "아이디가 존재합니다.");
					break;
				}

				case IDCHECK_FAIL: {
					JOptionPane.showMessageDialog(null, "사용가능한 아이디입니다.");
					break;
				}

				case GAME_WIN: {
					Sound("src/승리.wav");
					UiChattingRoom.win();
					break;
				}

				case GAME_LOSE: {
					Sound("src/패배.wav");
					UiChattingRoom.lose();
					break;
				}

				case ADMIN_RESET: {
					UiChattingRoom.AdminID = st.nextToken();
					break;
				}
				case SOUND: {
					Sound("src/" + st.nextToken());
					break;
				}
				case MEMPRO_SUCCESS: {
					searchId = st.nextToken();
					searchSex = st.nextToken();
					searchGrade = st.nextToken();
					searchWin = st.nextToken();
					searchLose = st.nextToken();
					searchValue = st.nextToken();

					break;
				}

				}
			}
		} catch (IOException e) {
			System.err.println(e);
			ThreadRelease();
		}
	}

	public void mempro(String id) {
		buf.setLength(0);
		buf.append(MEMPRO);
		buf.append(SEPA);
		buf.append(id);
		send(buf.toString());
		System.out.println("mempro : " + buf.toString());
	}

	public void closeChatRoom() {
		int ans = JOptionPane.showConfirmDialog(null, "정말로 나가시겠습니까?", "메세지", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (ans == JOptionPane.YES_OPTION) {
			buf.setLength(0);
			buf.append(CLOSECHATROOM);
			buf.append(SEPA);
			buf.append(dto.getMemberId());
			send(buf.toString());
			System.out.println("CloseChatRoom" + buf.toString());
		}
	}

	public void ThreadRelease() {
		if (selfThread != null) {
			selfThread = null;
		}

		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException e) {

		} finally {
			output = null;
		}

		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException e) {

		} finally {
			input = null;
		}

		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {

		} finally {
			socket = null;
		}
		System.exit(0);
	}

	public void login(String id, String password) {
		int ans = JOptionPane.showConfirmDialog(null, "'" + id + "'로 로그인을 하시겠습니까?", "메세지", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (ans == JOptionPane.YES_OPTION) {
			buf.setLength(0);
			buf.append(LOGIN_REQUEST);
			buf.append(SEPA);
			buf.append(id);
			buf.append(SEPA);
			buf.append(password);
			send(buf.toString());

			System.out.println("login" + buf.toString());
		} else if (ans == JOptionPane.NO_OPTION) {
			JOptionPane.showConfirmDialog(null, "접속을 종료합니다.", "메세지", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}

	public boolean memberShip(String id, String password) {
		int ans = JOptionPane.showConfirmDialog(null, "'" + id + "'로 로그인을 하시겠습니까?", "메세지", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (ans == JOptionPane.YES_OPTION) {
			buf.setLength(0);
			buf.append(MEMBERSHIP);
			buf.append(SEPA);
			buf.append(id);
			buf.append(SEPA);
			buf.append(password);
			send(buf.toString());
			System.out.println("login" + buf.toString());
		} else if (ans == JOptionPane.NO_OPTION) {
			JOptionPane.showConfirmDialog(null, "접속을 종료합니다.", "메세지", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		return false;

	}

	public void logOut() {
		buf.setLength(0);
		buf.append(LOGOUT_REQUEST);
		buf.append(SEPA);
		buf.append(dto.getMemberId());
		send(buf.toString());
	}

	public void CreateRoom(String title, int MaxUser, int isRock, String password) {
		buf.setLength(0);
		buf.append(CREATEROOM_REQUEST);
		buf.append(SEPA);
		buf.append(title);
		buf.append(SEPA);
		buf.append(MaxUser);
		buf.append(SEPA);
		buf.append(isRock);
		buf.append(SEPA);
		buf.append(password);
		send(buf.toString());
		System.out.println("CreateRoom" + buf.toString());
	}

	public void JoinChattingRoom(int roomNo, String password) {
		buf.setLength(0);
		buf.append(JOINROOM);
		buf.append(SEPA);
		buf.append(roomNo);
		buf.append(SEPA);
		buf.append(password);
		send(buf.toString());
		System.out.println("JoinChattingRoom" + buf.toString());
	}

	public void SendWord(String msg) {
		buf.setLength(0);
		buf.append(SENDWORD);
		buf.append(SEPA);
		buf.append(UiChattingRoom.roomNo);
		buf.append(SEPA);
		buf.append(msg);
		send(buf.toString());
		System.out.println("SendWord" + buf.toString());
	}

	public void send(String data) {
		try {
			output.writeUTF(data);
			output.flush();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void register(String string, String string2, String string3, String string4, String string5, String string6,
			char[] cs) {
		buf.setLength(0);
		buf.append(MEMBERSHIP + SEPA + string + SEPA + string2 + SEPA + string3 + SEPA + string4 + SEPA + string5 + SEPA
				+ string6 + SEPA + String.valueOf(cs));
		send(buf.toString());
	}

	public void Sound(String name) {
		try {
			File Clap = new File(name);
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Clap));
			clip.start();
		} catch (Exception ex) {

		}
	}
}
