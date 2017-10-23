
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class UIChattingRoom extends JFrame
		implements ActionListener, KeyListener, ListSelectionListener, Runnable, CommonConstant, MouseListener {
	private JPanel contentPane;
	private JTextField tfMsg;
	public static JTextArea taChatting;
	public JButton btnClose;
	public JButton btnOut;
	public JList<MemberDTO> listMember;
	private static ClientThread clientThread;
	private boolean isSelected;
	private String selectedId;
	private int myTurn;
	private int maxMember;
	JLabel memidlb, memsexlb, memgradelb, memwinlb, memloselb;

	public int getMaxMember() {
		return maxMember;
	}

	public void setMaxMember(int maxMember) {
		this.maxMember = maxMember;
	}

	public int getMyTurn() {
		return myTurn;
	}

	public void setMyTurn(int myTurn) {
		this.myTurn = myTurn;
	}

	public String AdminID;
	public static int roomNo;

	String userName;
	String host;
	static List<JButton> aList = new ArrayList<JButton>();
	static List<JButton> enemyCardList1 = new ArrayList<JButton>();
	static List<JButton> enemyCardList2 = new ArrayList<JButton>();
	static List<JButton> enemyCardList3 = new ArrayList<JButton>();

	// 카드 버튼
	JButton btn1;
	JButton btn2;
	JButton btn3;
	JButton btn4;
	JButton btn5;
	JButton btn6;
	JButton btn7;
	JButton btn8;
	JButton btn9;
	JButton btn10;
	JButton btn11;
	JButton btn12;
	JButton btn13;
	JButton btn14;
	JButton btn15;
	JButton btn16;
	JButton btn17;
	JButton btn18;
	JButton btn19;
	JButton btn20;
	JButton btn21;
	JButton btn22;
	JButton btn23;
	JButton btn24;
	JButton btn25;
	JButton btn26;
	JButton btn27;
	JButton btn28;
	JButton btn29;
	JButton btn30;
	JButton btn31;
	JButton btn32;
	JButton btn33;
	JButton btn34;
	JButton btn35;
	JButton btn36;
	JButton btn37;
	JButton btn38;
	JButton btn39;
	JButton btn40;
	JButton mainCard;

	JButton confirm;

	ImageIcon icon;

	static JButton subCard; // 카드뽑기

	DefaultTableModel tableModel;
	JTable table;
	JScrollPane scroll;

	JPanel pan;
	Socket socket = null;
	Thread th;

	JButton turn1;
	JButton turn2;
	JButton turn3;
	JButton turn4;
	JLabel winImage;
	JLabel loseImage;

	public UIChattingRoom(ClientThread clientThread) {

		confirm = new JButton(new ImageIcon("src/buttonImg/ok.png"));
		confirm.setBorderPainted(false);
		confirm.setContentAreaFilled(false);
		confirm.addActionListener(this);

		turn1 = new JButton(new ImageIcon("src/buttonImg/turn1.png"));// 아래쪽
		turn1.setBorderPainted(false);
		turn1.setContentAreaFilled(false);
		turn1.setVisible(false);

		turn2 = new JButton(new ImageIcon("src/buttonImg/turn2.png"));// 위쪽
		turn2.setBorderPainted(false);
		turn2.setContentAreaFilled(false);
		turn2.setVisible(false);

		turn3 = new JButton(new ImageIcon("src/buttonImg/turn3.png"));// 왼쪽
		turn3.setBorderPainted(false);
		turn3.setContentAreaFilled(false);
		turn3.setVisible(false);

		turn4 = new JButton(new ImageIcon("src/buttonImg/turn4.png"));// 오른쪽
		turn4.setBorderPainted(false);
		turn4.setContentAreaFilled(false);
		turn4.setVisible(false);

		setSize(new Dimension(510, 340));
		getContentPane().setLayout(null);

		this.clientThread = clientThread;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 555, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "채팅창", TitledBorder.LEADING, TitledBorder.TOP, null, Color.YELLOW));
		panel.setBounds(1200, 500, 330, 186);
		contentPane.add(panel);
		panel.setBackground(new Color(255, 0, 0, 0));
		panel.setLayout(null);

		taChatting = new JTextArea(); // 채팅 메세지 입력 창
		taChatting.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// taChatting.setBounds(12, 21, 313, 155);
		// panel.add(taChatting);

		taChatting.setEditable(false);

		JScrollPane scroll = new JScrollPane(taChatting);
		scroll.setBounds(12, 21, 313, 155);
		panel.add(scroll);
		icon = new ImageIcon("src/img/background.PNG");

		JPanel backPan = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, 1600, 900, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "참여자 리스트", TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.YELLOW));
		panel_1.setBounds(1200, 100, 330, 100);
		panel_1.setBackground(new Color(255, 0, 0, 0));
		contentPane.add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(null, "내 프로필", TitledBorder.LEADING, TitledBorder.TOP, null, Color.YELLOW));
		panel_2.setBounds(1200, 300, 330, 120);
		panel_2.setBackground(new Color(255, 0, 0, 0));

		contentPane.add(panel_2);

		// 아이디
		memidlb = new JLabel("아이디 : " + clientThread.dto.getMemberId());
		memidlb.setBounds(10, 20, 120, 30);
		panel_2.add(memidlb);

		// 성별
		memsexlb = new JLabel("성별 : " + clientThread.dto.getMemberGender());
		memsexlb.setBounds(10, 45, 120, 30);
		panel_2.add(memsexlb);

		// 등급
		memgradelb = new JLabel("랭크 : " + clientThread.dto.getRank());
		memgradelb.setBounds(10, 70, 120, 30);
		panel_2.add(memgradelb);

		// 승
		memwinlb = new JLabel("승 : " + clientThread.dto.getWins());
		System.out.println("win : " + clientThread.dto.getWins());
		memwinlb.setBounds(10, 95, 120, 30);
		panel_2.add(memwinlb);

		// 패
		memloselb = new JLabel("패 : " + clientThread.dto.getLoses());
		memloselb.setBounds(10, 120, 120, 30);
		panel_2.add(memloselb);

		listMember = new JList(); // 방유저 목록
		listMember.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listMember.setBounds(10, 25, 313, 60);
		listMember.addListSelectionListener(this);
		listMember.addMouseListener(this);
		panel_1.add(listMember);

		tfMsg = new JTextField(); // 메세지 입력창
		tfMsg.setBounds(1200, 700, 328, 21);
		tfMsg.addKeyListener(this);
		contentPane.add(tfMsg);
		tfMsg.setColumns(10);

		btnOut = new JButton(new ImageIcon("src/buttonImg/pk.png")); // 강퇴버튼
		btnOut.setBounds(1200, 730, 97, 23);
		btnOut.addActionListener(this);
		btnOut.setBorderPainted(false);
		btnOut.setContentAreaFilled(false);
		contentPane.add(btnOut);

		btnClose = new JButton(new ImageIcon("src/buttonImg/out.png")); // 나가기버튼
		btnClose.setBounds(1350, 730, 97, 23);
		btnClose.addActionListener(this);
		btnClose.setBorderPainted(false);
		btnClose.setContentAreaFilled(false);
		contentPane.add(btnClose);

		// JPanel cardPan = new JPanel();
		th = new Thread(this);
		th.start();

		mainCard = new JButton();
		mainCard.setIcon(new ImageIcon("src/img/BackCard.jpg"));
		mainCard.setBorderPainted(false);
		mainCard.setFocusPainted(false);
		mainCard.setContentAreaFilled(false);
		/*
		 * mainCard.setBorderPainted(false); mainCard.setFocusPainted(false);
		 * mainCard.setContentAreaFilled(false);
		 */
		mainCard.setBounds(650, 325, 80, 115);

		subCard = new JButton();
		subCard.setIcon(new ImageIcon("src/img/BackCard.jpg"));
		subCard.setBorderPainted(false);
		subCard.setFocusPainted(false);
		subCard.setContentAreaFilled(false);
		/*
		 * subCard.setBorderPainted(false); subCard.setFocusPainted(false);
		 * subCard.setContentAreaFilled(false);
		 */
		subCard.setBounds(450, 325, 80, 115);

		JPanel userTable1 = new JPanel(new GridLayout(1, 10)); // 플레이 하는 사용자 버튼 아래
		userTable1.add(btn1 = new JButton());
		aList.add(btn1);
		userTable1.add(btn2 = new JButton());
		aList.add(btn2);
		userTable1.add(btn3 = new JButton());
		aList.add(btn3);
		userTable1.add(btn4 = new JButton());
		aList.add(btn4);
		userTable1.add(btn5 = new JButton());
		aList.add(btn5);
		userTable1.add(btn6 = new JButton());
		aList.add(btn6);
		userTable1.add(btn7 = new JButton());
		aList.add(btn7);
		userTable1.add(btn8 = new JButton());
		aList.add(btn8);
		userTable1.add(btn9 = new JButton());
		aList.add(btn9);
		userTable1.add(btn10 = new JButton());
		aList.add(btn10);
		userTable1.setBounds(150, 700, 800, 100);
		turn1.setBounds(300, 600, 100, 50);
		userTable1.setOpaque(false);

		JPanel userTable2 = new JPanel(new GridLayout(1, 10)); // 2번사용자 버튼 위
		userTable2.add(btn11 = new JButton());
		enemyCardList1.add(btn11);
		userTable2.add(btn12 = new JButton());
		enemyCardList1.add(btn12);
		userTable2.add(btn13 = new JButton());
		enemyCardList1.add(btn13);
		userTable2.add(btn14 = new JButton());
		enemyCardList1.add(btn14);
		userTable2.add(btn15 = new JButton());
		enemyCardList1.add(btn15);
		userTable2.add(btn16 = new JButton());
		enemyCardList1.add(btn16);
		userTable2.add(btn17 = new JButton());
		enemyCardList1.add(btn17);
		userTable2.add(btn18 = new JButton());
		enemyCardList1.add(btn18);
		userTable2.add(btn19 = new JButton());
		enemyCardList1.add(btn19);
		userTable2.add(btn20 = new JButton());
		enemyCardList1.add(btn20);
		userTable2.setBounds(150, 10, 800, 100);
		turn2.setBounds(810, 250, 100, 50);
		userTable2.setOpaque(false);

		JPanel userTable3 = new JPanel(new GridLayout(10, 1)); // 3번 사용자 버튼 왼쪽
		userTable3.add(btn21 = new JButton());
		enemyCardList2.add(btn21);
		userTable3.add(btn22 = new JButton());
		enemyCardList2.add(btn22);
		userTable3.add(btn23 = new JButton());
		enemyCardList2.add(btn23);
		userTable3.add(btn24 = new JButton());
		enemyCardList2.add(btn24);
		userTable3.add(btn25 = new JButton());
		enemyCardList2.add(btn25);
		userTable3.add(btn26 = new JButton());
		enemyCardList2.add(btn26);
		userTable3.add(btn27 = new JButton());
		enemyCardList2.add(btn27);
		userTable3.add(btn28 = new JButton());
		enemyCardList2.add(btn28);
		userTable3.add(btn29 = new JButton());
		enemyCardList2.add(btn29);
		userTable3.add(btn30 = new JButton());
		enemyCardList2.add(btn30);
		userTable3.setBounds(10, 100, 100, 600);
		turn3.setBounds(300, 300, 100, 50);
		userTable3.setOpaque(false);

		JPanel userTable4 = new JPanel(new GridLayout(10, 1)); // 4번 사용자 버튼 오른쪽
		userTable4.add(btn31 = new JButton());
		enemyCardList3.add(btn31);
		userTable4.add(btn32 = new JButton());
		enemyCardList3.add(btn32);
		userTable4.add(btn33 = new JButton());
		enemyCardList3.add(btn33);
		userTable4.add(btn34 = new JButton());
		enemyCardList3.add(btn34);
		userTable4.add(btn35 = new JButton());
		enemyCardList3.add(btn35);
		userTable4.add(btn36 = new JButton());
		enemyCardList3.add(btn36);
		userTable4.add(btn37 = new JButton());
		enemyCardList3.add(btn37);
		userTable4.add(btn38 = new JButton());
		enemyCardList3.add(btn38);
		userTable4.add(btn39 = new JButton());
		enemyCardList3.add(btn39);
		userTable4.add(btn40 = new JButton());
		enemyCardList3.add(btn40);
		userTable4.setBounds(990, 100, 100, 600);
		turn4.setBounds(800, 600, 100, 50);
		userTable4.setOpaque(false);

		winImage = new JLabel(new ImageIcon("src/buttonImg/win.png"));
		winImage.setBounds(120, 100, 960, 686);
		winImage.setOpaque(false);
		winImage.setVisible(false);

		loseImage = new JLabel(new ImageIcon("src/buttonImg/lose.png"));
		loseImage.setBounds(120, 50, 960, 686);
		loseImage.setOpaque(false);
		loseImage.setVisible(false);

		confirm.setBounds(550, 600, 100, 50);
		confirm.setVisible(false);

		JScrollPane jp = new JScrollPane(backPan);// 게임판 전채 판
		backPan.add(confirm);
		backPan.add(winImage);
		backPan.add(loseImage);
		backPan.add(userTable1);
		backPan.add(userTable2);
		backPan.add(userTable3);
		backPan.add(userTable4);
		backPan.add(mainCard);
		backPan.add(subCard);
		backPan.setLayout(null);

		add(turn1);
		add(turn2);
		add(turn3);
		add(turn4);

		// 게임판 크기
		jp.setBounds(10, 10, 1600, 900);
		add(jp);

		// 버튼 이미지 가리기
		for (int i = 0; i < aList.size(); i++) {
			aList.get(i).addActionListener(this);
			aList.get(i).setBorderPainted(false);
			aList.get(i).setFocusPainted(false);
			aList.get(i).setContentAreaFilled(false);
		}

		for (int i = 0; i < enemyCardList1.size(); i++) {
			enemyCardList1.get(i).setBorderPainted(false);
			enemyCardList1.get(i).setFocusPainted(false);
			enemyCardList1.get(i).setContentAreaFilled(false);
		}

		for (int i = 0; i < enemyCardList2.size(); i++) {
			enemyCardList2.get(i).setBorderPainted(false);
			enemyCardList2.get(i).setFocusPainted(false);
			enemyCardList2.get(i).setContentAreaFilled(false);
		}

		for (int i = 0; i < enemyCardList3.size(); i++) {
			enemyCardList3.get(i).setBorderPainted(false);
			enemyCardList3.get(i).setFocusPainted(false);
			enemyCardList3.get(i).setContentAreaFilled(false);
		}
		mainCard.addActionListener(this);

		setSize(1600, 900);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				tfMsg.requestFocusInWindow();
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnOut) {
			if (!isSelected) {
				JOptionPane.showMessageDialog(this, "Id를 클릭해주세요", "메세지", JOptionPane.ERROR_MESSAGE);
			} else {
				if (AdminID.equals(clientThread.getUserID()) && selectedId != clientThread.getUserID()) {
					System.out.println(selectedId);

					clientThread.sendFire(selectedId);
				} else {
					JOptionPane.showMessageDialog(this, "방장이 아닙니다", "메세지", JOptionPane.ERROR_MESSAGE);
				}
				isSelected = false;
			}
		} else if (e.getSource() == btnClose) {
			clientThread.send(CLOSECHATROOM + SEPA);
			this.dispose();
		} else if (e.getSource() == mainCard) {
			clientThread.Sound("src/flipcard.wav");
			clientThread.send("" + SELECTCARD + SEPA + "10");
		} else if (e.getSource() == confirm) {
			winImage.setVisible(false);
			loseImage.setVisible(false);
			confirm.setVisible(false);
		} else {
			for (int i = 0; i < aList.size(); i++) {
				if (e.getSource() == aList.get(i)) {
					clientThread.Sound("src/flipcard.wav");
					System.out.println(i);
					clientThread.send(SELECTCARD + SEPA + i);
					break;
				}
			}
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		isSelected = true;
		selectedId = String.valueOf(((JList) e.getSource()).getSelectedValue());
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			clientThread.SendWord(tfMsg.getText());
			tfMsg.setText("");
			tfMsg.requestFocusInWindow();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void infUpdate(int win, int lose) {
		 memwinlb.setText("" + win);
		 memloselb.setText("" + lose);
	}
	
	public void ClearData() {
		tfMsg.setText("");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public static void buttonSetting(String[] str) {
		for (int i = 0; i < aList.size(); i++) {
			if (str.length - 1 < i) {
				aList.get(i).setIcon(new ImageIcon(""));
			} else {
				String str2 = "src/img/" + str[i] + ".jpg";
				System.out.println(str2);

				aList.get(i).setIcon(new ImageIcon(str2));

			}
		}
	}

	public void EnemyCard(int inf, int enemyTurn) {
		if (maxMember == 2) {
			for (int i = 0; i < enemyCardList1.size(); i++) {
				if (i < inf) {
					String str2 = "src/img/BackCard.jpg";
					enemyCardList1.get(i).setIcon(new ImageIcon(str2));
				} else {
					enemyCardList1.get(i).setIcon(new ImageIcon(""));
				}
			}
		} else if (maxMember == 3) {
			if ((myTurn + 1 <= 2 ? myTurn + 1 : myTurn + 1 - 3) == enemyTurn) {
				for (int i = 0; i < enemyCardList3.size(); i++) {
					if (i < inf) {
						String str2 = "src/img/BackCardRotation.jpg";
						enemyCardList3.get(i).setIcon(new ImageIcon(str2));
					} else {
						enemyCardList3.get(i).setIcon(new ImageIcon(""));
					}
				}
			} else if ((myTurn + 2 <= 2 ? myTurn + 2 : myTurn + 2 - 3) == enemyTurn) {
				for (int i = 0; i < enemyCardList1.size(); i++) {
					if (i < inf) {
						String str2 = "src/img/BackCard.jpg";
						enemyCardList1.get(i).setIcon(new ImageIcon(str2));
					} else {
						enemyCardList1.get(i).setIcon(new ImageIcon(""));
					}
				}
			}
		} else if (maxMember == 4) {
			System.out.println("enemyTurn : " + enemyTurn);
			if ((myTurn + 1 <= 3 ? myTurn + 1 : myTurn + 1 - 4) == enemyTurn) {
				for (int i = 0; i < enemyCardList3.size(); i++) {
					if (i < inf) {
						String str2 = "src/img/BackCardRotation.jpg";
						enemyCardList3.get(i).setIcon(new ImageIcon(str2));
					} else {
						enemyCardList3.get(i).setIcon(new ImageIcon(""));
					}
				}
			} else if ((myTurn + 2 <= 3 ? myTurn + 2 : myTurn + 2 - 4) == enemyTurn) {
				for (int i = 0; i < enemyCardList1.size(); i++) {
					if (i < inf) {
						String str2 = "src/img/BackCard.jpg";
						enemyCardList1.get(i).setIcon(new ImageIcon(str2));
					} else {
						enemyCardList1.get(i).setIcon(new ImageIcon(""));
					}
				}
			} else if ((myTurn + 3 <= 3 ? myTurn + 3 : myTurn + 3 - 4) == enemyTurn) {
				for (int i = 0; i < enemyCardList2.size(); i++) {
					if (i < inf) {
						String str2 = "src/img/BackCardRotation.jpg";
						enemyCardList2.get(i).setIcon(new ImageIcon(str2));
					} else {
						enemyCardList2.get(i).setIcon(new ImageIcon(""));
					}
				}
			}
		}

	}

	public static void shapeChoice() {
		String[] buttons = { "하트", "클로버", "스페이드", "다이아" };
		int selectShape = JOptionPane.showOptionDialog(null, "원하는 모양을 선택하세요.", "모양 선택",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "두번째값");
		System.out.println(selectShape);
		String str = "";
		if (selectShape == 0) {
			str = "하트";
		} else if (selectShape == 1) {
			str = "클로버";
		} else if (selectShape == 2) {
			str = "스페이드";
		} else if (selectShape == 3) {
			str = "다이아";
		}
		clientThread.send(SELECT_SHAPE + SEPA + str);
		clientThread.send(SENDWORD + SEPA + roomNo + SEPA + str + "모양을 선택했습니다!!!!");
	}

	public void nowTurn(int turn) {
		if (maxMember == 2) {
			if (turn == myTurn) {
				turn1.setVisible(true);
				turn2.setVisible(false);
				turn3.setVisible(false);
				turn4.setVisible(false);
			} else {
				turn1.setVisible(false);
				turn2.setVisible(true);
				turn3.setVisible(false);
				turn4.setVisible(false);
			}
		} else if (maxMember == 3) {
			if ((myTurn + 1 <= 2 ? myTurn + 1 : myTurn + 1 - 3) == turn) {
				turn2.setVisible(false);
				turn1.setVisible(false);
				turn4.setVisible(true);

			} else if ((myTurn + 2 <= 2 ? myTurn + 2 : myTurn + 2 - 3) == turn) {
				turn2.setVisible(true);
				turn1.setVisible(false);
				turn4.setVisible(false);
			} else {
				turn2.setVisible(false);
				turn1.setVisible(true);
				turn4.setVisible(false);
			}
		} else if (maxMember == 4) {
			if ((myTurn + 1 <= 3 ? myTurn + 1 : myTurn + 1 - 4) == turn) {
				turn1.setVisible(false);
				turn2.setVisible(false);
				turn3.setVisible(false);
				turn4.setVisible(true);
			} else if ((myTurn + 2 <= 3 ? myTurn + 2 : myTurn + 2 - 4) == turn) {
				turn1.setVisible(false);
				turn2.setVisible(true);
				turn3.setVisible(false);
				turn4.setVisible(false);
			} else if ((myTurn + 3 <= 3 ? myTurn + 3 : myTurn + 3 - 4) == turn) {
				turn1.setVisible(false);
				turn2.setVisible(false);
				turn3.setVisible(true);
				turn4.setVisible(false);
			} else {
				turn1.setVisible(true);
				turn2.setVisible(false);
				turn3.setVisible(false);
				turn4.setVisible(false);
			}
		}
	}

	public void win() {
		winImage.setVisible(true);
		confirm.setVisible(true);
		clientThread.dto.setWins(clientThread.dto.getWins() + 1);
	}

	public void lose() {
		loseImage.setVisible(true);
		confirm.setVisible(true);
		clientThread.dto.setLoses(clientThread.dto.getLoses() + 1);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if (obj == listMember && e.getClickCount() == 2) {
			selectedId = String.valueOf(((JList) e.getSource()).getSelectedValue());
			clientThread.mempro(selectedId);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

class meminfoframe extends JFrame implements ActionListener {

	JLabel memidlb, memsexlb, memgradelb, memwinlb, memloselb, memvaluelb;
	JButton okB;
	ClientThread ct;

	public meminfoframe(ClientThread ct) {
		this.ct = ct;

		JPanel idP = new JPanel();

		// 아이디
		memidlb = new JLabel("아이디 : " + ct.searchId);
		memidlb.setBounds(30, 30, 200, 50);
		add(memidlb);

		// 성별
		memsexlb = new JLabel("성별 : " + ct.searchSex);
		memsexlb.setBounds(30, 50, 200, 50);
		add(memsexlb);

		// 등급
		memgradelb = new JLabel("등급 : " + ct.searchGrade);
		memgradelb.setBounds(30, 140, 100, 30);
		add(memgradelb);

		// 승
		memwinlb = new JLabel("승 : " + ct.searchWin);
		memwinlb.setBounds(30, 170, 100, 30);
		add(memwinlb);

		// 패
		memloselb = new JLabel("패 : " + ct.searchLose);
		memloselb.setBounds(30, 200, 100, 30);
		add(memloselb);

		// // 경험치
		// memsexlb = new JLabel("경험치 : " );
		// memsexlb.setBounds(30, 140, 100, 30);
		// add(memsexlb);

		// 확인, 취소 버튼
		okB = new JButton(new ImageIcon("src/buttonImg/ok.png"));
		okB.setBounds(130, 330, 60, 30);
		okB.setBorderPainted(false);
		okB.setContentAreaFilled(false);
		add(okB);

		add(idP);// 붙이기

		// 버튼 리스너
		okB.addActionListener(this);

		setTitle("회원정보");
		setSize(330, 420);
		setVisible(true);
		setLocationRelativeTo(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();

		if (obj == okB) {
			this.dispose();
		}

	}
}
