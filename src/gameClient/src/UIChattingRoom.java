package gameClient.src;

import java.awt.Color;
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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gameServer.src.MemberDTO;

public class UIChattingRoom extends JFrame
		implements ActionListener, KeyListener, ListSelectionListener, Runnable, CommonConstant {

	private JPanel contentPane;
	private JTextField tfMsg;
	public static JTextArea taChatting;
	public JButton btnClose;
	public JButton btnOut;
	public JList<MemberDTO> listMember;

	private static ClientThread clientThread;
	private boolean isSelected;
	private String selectedId;

	public String AdminID;
	public int roomNo;

	String userName;
	String host;
	static List<JButton> aList = new ArrayList<JButton>();

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
	JButton mainCard;

	static JButton subCard;

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
		panel.setBorder(
				new TitledBorder(null, "\uCC44\uD305\uCC3D", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 35, 337, 186);
		contentPane.add(panel);
		panel.setLayout(null);

		taChatting = new JTextArea();
		taChatting.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		taChatting.setBounds(12, 21, 313, 155);
		panel.add(taChatting);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uCC38\uC5EC\uC790 \uB9AC\uC2A4\uD2B8",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(383, 35, 146, 186);
		contentPane.add(panel_1);

		listMember = new JList();
		listMember.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listMember.setBounds(12, 25, 122, 151);
		listMember.addListSelectionListener(this);
		panel_1.add(listMember);

		tfMsg = new JTextField();
		tfMsg.setBounds(22, 231, 328, 21);
		tfMsg.addKeyListener(this);
		contentPane.add(tfMsg);
		tfMsg.setColumns(10);

		btnOut = new JButton("\uAC15\uD1F4");
		btnOut.setBounds(393, 230, 97, 23);
		btnOut.addActionListener(this);
		contentPane.add(btnOut);

		btnClose = new JButton("\uB098\uAC00\uAE30");
		btnClose.setBounds(393, 263, 97, 23);
		btnClose.addActionListener(this);
		contentPane.add(btnClose);

		JPanel cardPan = new JPanel();
		th = new Thread(this);
		th.start();
		JPanel mainTable = new JPanel();
		mainTable.setLayout(null);

		mainCard = new JButton(new ImageIcon("src/img/BackCard.jpg"));
		mainCard.setBorderPainted(false);
		mainCard.setFocusPainted(false);
		mainCard.setContentAreaFilled(false);
		mainCard.setBounds(550, 80, 100, 150);

		subCard = new JButton();
		subCard.setBorderPainted(false);
		subCard.setFocusPainted(false);
		subCard.setContentAreaFilled(false);
		subCard.setBounds(750, 80, 100, 150);

		mainTable.add(mainCard);
		mainTable.add(subCard);
		JPanel userTable1 = new JPanel(new GridLayout(1, 10));

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

		JPanel userTable2 = new JPanel(new GridLayout(1, 10));
		userTable2.add(btn11 = new JButton());
		userTable2.add(btn12 = new JButton());
		userTable2.add(btn13 = new JButton());
		userTable2.add(btn14 = new JButton());
		userTable2.add(btn15 = new JButton());
		userTable2.add(btn16 = new JButton());
		userTable2.add(btn17 = new JButton());
		userTable2.add(btn18 = new JButton());
		userTable2.add(btn19 = new JButton());
		userTable2.add(btn20 = new JButton());

		JPanel jp = new JPanel(new GridLayout(3, 1));

		jp.add(userTable2);
		jp.add(mainTable);
		jp.add(userTable1);
		jp.setBounds(10, 10, 1500, 900);
		this.add(jp);
		for (int i = 0; i < aList.size(); i++) {
			aList.get(i).addActionListener(this);
			aList.get(i).setBorderPainted(false);
			aList.get(i).setFocusPainted(false);
			aList.get(i).setContentAreaFilled(false);
		}
		mainCard.addActionListener(this);

		setSize(2000, 900);
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
				if (AdminID.equals(selectedId)) {
					JOptionPane.showMessageDialog(this, "방장입니다", "메세지", JOptionPane.ERROR_MESSAGE);
				} else {
					isSelected = false;
				}
			}
		} else if (e.getSource() == btnClose) {
			this.dispose();
		}

		for (int i = 0; i < aList.size(); i++) {
			if (e.getSource() == aList.get(i)) {
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
		// TODO Auto-generated method stub

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

	public static int shapeChoice() {
		String[] buttons = { "하트", "클로버", "스페이드", "다이아" };
		int selectShape = JOptionPane.showOptionDialog(null, "원하는 모양을 선택하세요.", "모양 선택",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "두번째값");
		System.out.println(selectShape);
		clientThread.send("" + SELECT_SHAPE + SEPA + selectShape);
		return selectShape;
	}
}
