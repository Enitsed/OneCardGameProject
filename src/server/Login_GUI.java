package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.MemberDAO;
import db.MemberDTO;

public class Login_GUI extends JFrame implements ActionListener {
	JTextField idtf;
	JPasswordField pwtf;
	JButton logB, regB;
	JLabel idlb, pwlb;

	public Login_GUI() {
		idlb = new JLabel("ID : ");
		pwlb = new JLabel("PW : ");

		idtf = new JTextField(10);
		pwtf = new JPasswordField(10);

		logB = new JButton("로그인");
		regB = new JButton("회원가입");

		JPanel pwP = new JPanel();
		pwP.add(pwlb);
		pwP.add(pwtf);

		JPanel btnP = new JPanel();
		btnP.add(logB);
		btnP.add(regB);

		JPanel idP = new JPanel();
		idP.add(idlb);
		idP.add(idtf);

		JPanel top = new JPanel();
		top.add(idP);
		top.add(pwP);
		top.add(btnP);

		add(top);

		logB.addActionListener(this);
		regB.addActionListener(this);

		setTitle("로그인");
		setSize(200, 200);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void memberLogin() {
		MemberDTO dto = new MemberDTO();
		MemberDAO dao = MemberDAO.getInstance();

		dto.setMemberId(idtf.getText());
		dto.setMemberPassword(Integer.valueOf(pwtf.getPassword().toString()));

		if (dao.memberExist(dto)) {

			if (dao.checkPassword(dto)) {
				JOptionPane.showMessageDialog(this, "로그인 성공.");
			} else {
				JOptionPane.showMessageDialog(this, "비밀번호 오류.");
				return;
			}

		} else {
			JOptionPane.showMessageDialog(this, "회원 가입 하세요.");
			return;
		}

	} // 로그인 체크

	public class RegisterFrame extends JFrame implements ActionListener {
		JTextField idtf2, namef;
		JPasswordField pwtf2;
		JLabel idlb2, pwlb2, namelb;
		JButton okB, cancelB;

		public RegisterFrame() {
			namelb = new JLabel("이름 : ");
			idlb2 = new JLabel("ID : ");
			pwlb2 = new JLabel("PW : ");

			namef = new JTextField(10);
			idtf2 = new JTextField(10);
			pwtf2 = new JPasswordField(10);
			pwtf2.setEchoChar('*');

			okB = new JButton("확인");
			cancelB = new JButton("취소");

			JPanel idP = new JPanel();
			idP.add(idlb2);
			idP.add(idtf2);

			JPanel pwP = new JPanel();
			pwP.add(pwlb2);
			pwP.add(pwtf2);

			JPanel nameP = new JPanel();
			nameP.add(namelb);
			nameP.add(namef);

			JPanel btnP = new JPanel();
			btnP.add(okB);
			btnP.add(cancelB);

			JPanel top = new JPanel();

			top.add(idP);
			top.add(pwP);
			top.add(nameP);
			top.add(btnP);

			add(top);

			okB.addActionListener(this);
			cancelB.addActionListener(this);

			setTitle("회원가입");
			setSize(300, 300);
			setVisible(true);
			setLocationRelativeTo(null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj == okB) {

				memberJoin();

			} else if (obj == cancelB) {
				this.dispose();
			}

		}

		private void memberJoin() {
			MemberDTO dto = new MemberDTO();
			MemberDAO dao = MemberDAO.getInstance();

			if (idtf2.getText() == null || namef.getText() == null || Integer.valueOf(pwtf2.getEchoChar()) == null) {
				JOptionPane.showMessageDialog(this, "정보를 입력해주세요.");
				return;
			}
			dto.setMemberId(idtf2.getText());
			dto.setMemberName(namef.getText());
			dto.setMemberPassword(Integer.valueOf(pwtf2.getEchoChar()));

			if (dao.memberExist(dto)) {
				JOptionPane.showMessageDialog(this, "이미 존재하는 회원입니다.");
				clean();
			} else {
				dao.insertMember(dto);
				JOptionPane.showMessageDialog(this, "회원가입에 성공하였습니다.");
				clean();
			}
		} // 회원가입 메소드

		private void clean() {
			idtf2.setText("");
			namef.setText("");
			pwtf2.setText("");
			idtf2.requestFocus();
		} // 입력창 비우기

	}

	public static void main(String[] args) {
		new Login_GUI();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == logB) {
			memberLogin();
		} else if (obj == regB) {
			new RegisterFrame();
		}

	}

}