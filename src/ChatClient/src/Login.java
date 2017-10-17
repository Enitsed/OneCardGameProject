package ChatClient.src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import CharServer.src.MemberDTO;

class Login extends JFrame implements ActionListener {
	JTextField idtf;
	JPasswordField pwtf;
	JButton logB, regB;
	JLabel idlb, pwlb;
	ClientThread ct;

	public Login(ClientThread ct) {
		this.ct = ct;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == logB) {
			MemberDTO dto = new MemberDTO();
			ct.login(dto);
		} else if (obj == regB) {
			new RegisterFrame(ct);
		}

	}
}

class RegisterFrame extends JFrame implements ActionListener {
	JTextField idtf2, namef, agef, emailf;
	JPasswordField pwtf2;
	JLabel idlb2, pwlb2, namelb, agelb, emaillb, sexlb, loclb;
	JRadioButton manR, womanR;
	DefaultComboBoxModel<Object> locModel;
	JComboBox<Object> locC;
	JButton okB, cancelB;
	Object[] locStr = { "경기", "경상도", "충청도", "전라도", "강원도" };
	ClientThread ct;

	public RegisterFrame(ClientThread ct) {
		this.ct = ct;

		namelb = new JLabel("이름 : ");
		idlb2 = new JLabel("ID : ");
		pwlb2 = new JLabel("PW : ");
		agelb = new JLabel("나이 : ");
		emaillb = new JLabel("이메일 : ");
		loclb = new JLabel("지역 : ");
		sexlb = new JLabel("성별 : ");

		namef = new JTextField(10);
		idtf2 = new JTextField(10);
		agef = new JTextField(10);
		emailf = new JTextField(10);
		pwtf2 = new JPasswordField(10);
		pwtf2.setEchoChar('*');

		manR = new JRadioButton("남", true);
		womanR = new JRadioButton("여");
		ButtonGroup genderRadio = new ButtonGroup();
		genderRadio.add(manR);
		genderRadio.add(womanR);

		locModel = new DefaultComboBoxModel<Object>(locStr);
		locC = new JComboBox<Object>(locModel);

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

		JPanel ageP = new JPanel();
		ageP.add(agelb);
		ageP.add(agef);

		JPanel sexP = new JPanel();
		sexP.add(sexlb);
		sexP.add(manR);
		sexP.add(womanR);

		JPanel emailP = new JPanel();
		emailP.add(emaillb);
		emailP.add(emailf);

		JPanel locP = new JPanel();
		locP.add(loclb);
		locP.add(locC);

		JPanel btnP = new JPanel();
		btnP.add(okB);
		btnP.add(cancelB);

		JPanel top = new JPanel();

		top.add(idP);
		top.add(pwP);
		top.add(nameP);
		top.add(ageP);
		top.add(sexP);
		top.add(emailP);
		top.add(locP);
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
			// 회원가입 메소드
			MemberDTO dto = new MemberDTO();
			dto.setMemberId(idtf2.getText());
			dto.setMemberName(namef.getText());
			dto.setMemberGender(manR.isSelected() ? "남" : "여");
			dto.setMemberAge(Integer.valueOf(agef.getText()));
			dto.setMemberEmail(emailf.getText());
			dto.setMemberLocation(locC.getSelectedItem().toString());
			dto.setMemberPassword(pwtf2.getPassword().toString());

			System.out.printf("%s, %s, %s, %d, %s, %s, %s", dto.getMemberId(), dto.getMemberName(),
					dto.getMemberGender(), dto.getMemberAge(), dto.getMemberEmail(), dto.getMemberLocation(),
					dto.getMemberPassword());
			ct.register(dto);
		} else if (obj == cancelB) {
			this.dispose();
		}
	}

	private void clean() {
		idtf2.setText("");
		namef.setText("");
		pwtf2.setText("");
		idtf2.requestFocus();
	} // 입력창 비우기
}