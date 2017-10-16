package ChatClient.src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.PasswordAuthentication;

public class UICreateRoom extends JFrame implements ActionListener, ItemListener {

	private JPanel contentPane;
	private JTextField tftitle;
	private JTextField tfPassword;

	private JRadioButton rbUserCnt2;
	private JRadioButton rbUserCnt3;
	private JRadioButton rbUserCnt4;
	private JRadioButton rbUserCnt5;

	private JRadioButton rbUnRock;
	private JRadioButton rbRock;

	private JButton btnOk;
	private JButton btnCancel;

	private int MaxUser, isRock;
	private String title, password;

	private ClientThread clientThread;

	public UICreateRoom(ClientThread clientThread) {
		this.clientThread = clientThread;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 236, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("\uBC29\uC81C\uBAA9");
		lblNewLabel.setBounds(12, 10, 57, 15);
		contentPane.add(lblNewLabel);

		tftitle = new JTextField();
		tftitle.setBounds(12, 35, 116, 21);
		contentPane.add(tftitle);
		tftitle.setColumns(10);

		JLabel label = new JLabel("\uBC29 \uC778\uC6D0\uC218");
		label.setBounds(12, 66, 57, 15);
		contentPane.add(label);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(12, 91, 196, 36);
		contentPane.add(panel);
		panel.setLayout(null);

		rbUserCnt2 = new JRadioButton("2\uBA85");
		rbUserCnt2.addItemListener(this);
		rbUserCnt2.setBounds(8, 6, 43, 23);
		panel.add(rbUserCnt2);

		rbUserCnt3 = new JRadioButton("3\uBA85");
		rbUserCnt3.setBounds(47, 6, 43, 23);
		rbUserCnt3.addItemListener(this);
		panel.add(rbUserCnt3);

		rbUserCnt4 = new JRadioButton("4\uBA85");
		rbUserCnt4.addItemListener(this);
		rbUserCnt4.setBounds(94, 6, 43, 23);
		panel.add(rbUserCnt4);

		rbUserCnt5 = new JRadioButton("5\uBA85");
		rbUserCnt5.addItemListener(this);
		rbUserCnt5.setBounds(145, 6, 43, 23);
		panel.add(rbUserCnt5);

		JLabel label_1 = new JLabel("\uACF5\uAC1C \uC5EC\uBD80");
		label_1.setBounds(12, 137, 57, 15);
		contentPane.add(label_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(12, 162, 196, 36);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		rbUnRock = new JRadioButton("\uACF5\uAC1C");
		rbUnRock.addItemListener(this);
		rbUnRock.setBounds(8, 6, 49, 23);
		panel_1.add(rbUnRock);

		rbRock = new JRadioButton("\uBE44\uACF5\uAC1C");
		rbRock.addItemListener(this);
		rbRock.setBounds(75, 6, 61, 23);
		panel_1.add(rbRock);

		JLabel lblNewLabel_1 = new JLabel("\uBE44\uBC00\uBC88\uD638");
		lblNewLabel_1.setBounds(12, 208, 57, 15);
		contentPane.add(lblNewLabel_1);

		tfPassword = new JTextField();
		tfPassword.setBounds(12, 231, 196, 21);
		contentPane.add(tfPassword);
		tfPassword.setColumns(10);

		btnOk = new JButton("\uD655\uC778");
		btnOk.addActionListener(this);
		btnOk.setFont(new Font("돋움", Font.BOLD, 16));
		btnOk.setBounds(55, 262, 73, 23);
		contentPane.add(btnOk);

		btnCancel = new JButton("\uCDE8\uC18C");
		btnCancel.addActionListener(this);
		btnCancel.setFont(new Font("돋움", Font.BOLD, 16));
		btnCancel.setBounds(135, 262, 73, 23);
		contentPane.add(btnCancel);

		Dimension dim = getToolkit().getScreenSize();
		setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);

		show();

		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				tftitle.requestFocusInWindow();
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnOk) {
			if (tftitle.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "�������� �Է��ϼ���", "��ȭ�� �����", JOptionPane.ERROR_MESSAGE);
			} else {
				title = tftitle.getText();
				if (isRock == 1) {
					password = tfPassword.getText();
				}

				if (isRock == 1 && password.equals("")) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է��ϼ���", "��ȭ�� �����", JOptionPane.ERROR_MESSAGE);
				} else {
					clientThread.CreateRoom(title, MaxUser, isRock, password);

					dispose();
				}
			}
		} else
			dispose();
	}

	public void itemStateChanged(ItemEvent arg0) {
		if (arg0.getSource() == rbUnRock) {
			isRock = 0;
			password = "0";
			tfPassword.setText("");
			tfPassword.setEditable(false);
		} else if (arg0.getSource() == rbRock) {
			isRock = 1;
			tfPassword.setEditable(false);
		} else if (arg0.getSource() == rbUserCnt2) {
			MaxUser = 2;
		} else if (arg0.getSource() == rbUserCnt3) {
			MaxUser = 3;
		} else if (arg0.getSource() == rbUserCnt4) {
			MaxUser = 4;
		} else if (arg0.getSource() == rbUserCnt5) {
			MaxUser = 5;
		}
	}
}
