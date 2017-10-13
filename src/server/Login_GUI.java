package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.MemberDAO;
import db.MemberDTO;

public class Login_GUI extends JFrame implements ActionListener {
	JTextField idtf, pwtf;
	JButton logB, regB;
	JLabel idlb, pwlb;

	public Login_GUI() {
		idlb = new JLabel("ID : ");
		pwlb = new JLabel("PW : ");

		idtf = new JTextField(10);
		pwtf = new JTextField(10);

		logB = new JButton("로그인");
		regB = new JButton("회원가입");

		JPanel idP = new JPanel();
		idP.add(idlb);
		idP.add(idtf);

		JPanel pwP = new JPanel();
		pwP.add(pwlb);
		pwP.add(pwtf);

		JPanel btnP = new JPanel();
		btnP.add(logB);
		btnP.add(regB);

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

	public class RegisterFrame extends JFrame implements ActionListener {
		JTextField idtf2, pwtf2, namef;
		JLabel idlb2, pwlb2, namelb;
		JButton okB, cancelB;

		public RegisterFrame() {
			namelb = new JLabel("이름 : ");
			idlb2 = new JLabel("ID : ");
			pwlb2 = new JLabel("PW : ");

			namef = new JTextField(10);
			idtf2 = new JTextField(10);
			pwtf2 = new JTextField(10);

			okB = new JButton("확인");
			cancelB = new JButton("취소");

			JPanel nameP = new JPanel();
			nameP.add(namelb);
			nameP.add(namef);

			JPanel idP = new JPanel();
			idP.add(idlb2);
			idP.add(idtf2);

			JPanel pwP = new JPanel();
			pwP.add(pwlb2);
			pwP.add(pwtf2);

			JPanel btnP = new JPanel();
			btnP.add(okB);
			btnP.add(cancelB);

			JPanel top = new JPanel();
			top.add(nameP);
			top.add(idP);
			top.add(pwP);
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

			}

		}

		private void memberJoin() {
			MemberDTO dto = new MemberDTO();
			MemberDAO dao = MemberDAO.getInstance();

			dto.setMemberId(idtf2.getText());
			dto.setMemberName(namef.getText());
			dto.setMemberPassword(Integer.valueOf(pwtf2.getText()));

			if (dao.memberExist(dto)) {
				JOptionPane.showMessageDialog(this, "이미 존재하는 회원입니다.");
				return;
			}

			dao.insertMember(dto);
			JOptionPane.showMessageDialog(this, "회원가입에 성공하였습니다.");
		}
	}

	public static void main(String[] args) {
		new Login_GUI();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == logB) {

		} else if (obj == regB) {
			new RegisterFrame();
		}

	}

}