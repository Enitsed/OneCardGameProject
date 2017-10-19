package gameClient.src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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

import gameServer.src.MemberDTO;

public class UIChattingRoom extends JFrame
		implements ActionListener, KeyListener, ListSelectionListener, CommonConstant {

	private JPanel contentPane;
	private JTextField tfMsg;
	public static JTextArea taChatting;
	public JButton btnClose;
	public JButton btnOut;
	public JList<String> listMember;
	private static ClientThread clientThread;
	private boolean isSelected;
	private String selectedId;

	public String AdminID;
	public int roomNo;
	public String roomTitle;

	String host;
	static List<JButton> myCardList = new ArrayList<JButton>();
	static List<JButton> enemyCardList = new ArrayList<JButton>();

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

	static JButton subCard; // 카드뽑기

	JPanel pan;
	Socket socket = null;
	Thread th;

	public UIChattingRoom(ClientThread clientThread) {
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
		panel.setBorder(new TitledBorder(null, "채팅창", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(1200, 500, 330, 186);
		contentPane.add(panel);
		panel.setLayout(null);

		taChatting = new JTextArea(); // 채팅 메세지 입력 창
		taChatting.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		taChatting.setBounds(12, 21, 313, 155);
		panel.add(taChatting);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "참여자 리스트", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(1200, 100, 330, 100);
		contentPane.add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(null, "내 프로필", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(1200, 300, 330, 120);
		contentPane.add(panel_2);

		JLabel userID = new JLabel("ID");
		userID.setBounds(12, 21, 10, 10);
		// userID.setLayout(null);

		panel_2.add(userID);

		listMember = new JList(); // 방유저 목록
		listMember.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listMember.setBounds(10, 25, 313, 60);
		listMember.addListSelectionListener(this);
		panel_1.add(listMember);

		tfMsg = new JTextField(); // 메세지 입력창
		tfMsg.setBounds(1200, 700, 328, 21);
		tfMsg.addKeyListener(this);
		contentPane.add(tfMsg);
		tfMsg.setColumns(10);

		btnOut = new JButton("강퇴"); // 강퇴버튼
		btnOut.setBounds(1200, 730, 97, 23);
		btnOut.addActionListener(this);
		contentPane.add(btnOut);

		btnClose = new JButton("나가기"); // 나가기버튼
		btnClose.setBounds(1350, 730, 97, 23);
		btnClose.addActionListener(this);
		contentPane.add(btnClose);

		mainCard = new JButton(new ImageIcon("gameClient/src/img/BackCard.jpg"));
		/*
		 * mainCard.setBorderPainted(false); mainCard.setFocusPainted(false);
		 * mainCard.setContentAreaFilled(false);
		 */
		mainCard.setBounds(650, 325, 80, 115);

		subCard = new JButton(); //
		/*
		 * subCard.setBorderPainted(false); subCard.setFocusPainted(false);
		 * subCard.setContentAreaFilled(false);
		 */
		subCard.setBounds(450, 325, 80, 115);

		JPanel userTable1 = new JPanel(new GridLayout(1, 10)); // 플레이 하는 사용자 버튼 아래
		userTable1.add(btn1 = new JButton());
		myCardList.add(btn1);
		userTable1.add(btn2 = new JButton());
		myCardList.add(btn2);
		userTable1.add(btn3 = new JButton());
		myCardList.add(btn3);
		userTable1.add(btn4 = new JButton());
		myCardList.add(btn4);
		userTable1.add(btn5 = new JButton());
		myCardList.add(btn5);
		userTable1.add(btn6 = new JButton());
		myCardList.add(btn6);
		userTable1.add(btn7 = new JButton());
		myCardList.add(btn7);
		userTable1.add(btn8 = new JButton());
		myCardList.add(btn8);
		userTable1.add(btn9 = new JButton());
		myCardList.add(btn9);
		userTable1.add(btn10 = new JButton());
		myCardList.add(btn10);
		userTable1.setBounds(150, 700, 800, 100);

		JPanel userTable2 = new JPanel(new GridLayout(1, 10)); // 2번사용자 버튼 위
		userTable2.add(btn11 = new JButton());
		enemyCardList.add(btn11);
		userTable2.add(btn12 = new JButton());
		enemyCardList.add(btn12);
		userTable2.add(btn13 = new JButton());
		enemyCardList.add(btn13);
		userTable2.add(btn14 = new JButton());
		enemyCardList.add(btn14);
		userTable2.add(btn15 = new JButton());
		enemyCardList.add(btn15);
		userTable2.add(btn16 = new JButton());
		enemyCardList.add(btn16);
		userTable2.add(btn17 = new JButton());
		enemyCardList.add(btn17);
		userTable2.add(btn18 = new JButton());
		enemyCardList.add(btn18);
		userTable2.add(btn19 = new JButton());
		enemyCardList.add(btn19);
		userTable2.add(btn20 = new JButton());
		enemyCardList.add(btn20);
		userTable2.setBounds(150, 10, 800, 100);

		JPanel userTable3 = new JPanel(new GridLayout(10, 1)); // 3번 사용자 버튼 왼쪽
		userTable3.add(btn21 = new JButton());
		userTable3.add(btn22 = new JButton());
		userTable3.add(btn23 = new JButton());
		userTable3.add(btn24 = new JButton());
		userTable3.add(btn25 = new JButton());
		userTable3.add(btn26 = new JButton());
		userTable3.add(btn27 = new JButton());
		userTable3.add(btn28 = new JButton());
		userTable3.add(btn29 = new JButton());
		userTable3.add(btn30 = new JButton());
		userTable3.setBounds(10, 100, 100, 600);

		JPanel userTable4 = new JPanel(new GridLayout(10, 1)); // 4번 사용자 버튼 오른쪽
		userTable4.add(btn31 = new JButton());
		userTable4.add(btn32 = new JButton());
		userTable4.add(btn33 = new JButton());
		userTable4.add(btn34 = new JButton());
		userTable4.add(btn35 = new JButton());
		userTable4.add(btn36 = new JButton());
		userTable4.add(btn37 = new JButton());
		userTable4.add(btn38 = new JButton());
		userTable4.add(btn39 = new JButton());
		userTable4.add(btn40 = new JButton());
		userTable4.setBounds(990, 100, 100, 600);

		JPanel jp = new JPanel();// 게임판 전채 판
		jp.add(userTable1);
		jp.add(userTable2);
		jp.add(userTable3);
		jp.add(userTable4);
		jp.add(mainCard);
		jp.add(subCard);
		jp.setLayout(null);

		// 게임판 크기
		jp.setBounds(10, 10, 1500, 900);
		add(jp);

		for (int i = 0; i < myCardList.size(); i++) {
			myCardList.get(i).addActionListener(this);
			myCardList.get(i).setBorderPainted(false);
			myCardList.get(i).setFocusPainted(false);
			myCardList.get(i).setContentAreaFilled(false);
		}

		for (int i = 0; i < enemyCardList.size(); i++) {
			enemyCardList.get(i).setBorderPainted(false);
			enemyCardList.get(i).setFocusPainted(false);
			enemyCardList.get(i).setContentAreaFilled(false);
		}
		// 버튼 이미지 가리기
		/*
		 * for (int i = 0; i < aList.size(); i++) {
		 * aList.get(i).addActionListener(this); aList.get(i).setBorderPainted(false);
		 * aList.get(i).setFocusPainted(false);
		 * aList.get(i).setContentAreaFilled(false); }
		 */
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
		if (e.getSource() == btnOut) {
			if (!isSelected) {
				JOptionPane.showMessageDialog(this, "Id를 클릭해주세요", "메세지", JOptionPane.ERROR_MESSAGE);
			} else {
				if (AdminID.equals(clientThread.getDto().getMemberId())
						&& selectedId != clientThread.getDto().getMemberId()) {
					System.out.println(selectedId);

					clientThread.sendFire(selectedId);
				} else {
					JOptionPane.showMessageDialog(this, "방장이 아닙니다", "메세지", JOptionPane.ERROR_MESSAGE);
				}
				isSelected = false;
			}
		} else if (e.getSource() == btnClose) {
			clientThread.closeChatRoom();
		}

		for (int i = 0; i < myCardList.size(); i++) {
			if (e.getSource() == myCardList.get(i)) {
				System.out.println(i);
				clientThread.send("" + SELECTCARD + SEPA + i);
				break;
			}
		}

		if (e.getSource() == mainCard) {
			clientThread.send("" + SELECTCARD + SEPA + "10");
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

	}

	public void ClearData() {
		tfMsg.setText("");
	}

	public static void buttonSetting(String[] str) {
		for (int i = 0; i < myCardList.size(); i++) {
			if (str.length - 1 < i) {
				myCardList.get(i).setIcon(new ImageIcon(""));
			} else {
				String str2 = "src/img/" + str[i] + ".jpg";
				System.out.println(str2);

				myCardList.get(i).setIcon(new ImageIcon(str2));

			}
		}
	}

	public static int shapeChoice() {
		String[] buttons = { "하트", "클로버", "스페이드", "다이아" };
		int selectShape = JOptionPane.showOptionDialog(null, "원하는 모양을 선택하세요.", "모양 선택",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "두번째값");
		System.out.println(selectShape);
		clientThread.send("" + SELECT_SHAPE + SEPA + selectShape);
		return selectShape;
	}

	public void enemyCard(int num, int inf) {
		for (int i = 0; i < enemyCardList.size(); i++) {
			if (i < inf) {
				String str2 = "src/img/BackCard.jpg";
				enemyCardList.get(i).setIcon(new ImageIcon(str2));
			} else {
				enemyCardList.get(i).setIcon(new ImageIcon(""));
			}
		}
	}
}