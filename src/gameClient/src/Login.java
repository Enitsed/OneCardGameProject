package gameClient.src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import gameServer.src.MemberDTO;

class Login extends JFrame implements ActionListener {
	JTextField idtf;
	JPasswordField pwtf;
	JButton logB, regB;
	JLabel idlb, pwlb;
	ClientThread ct;
	RegisterFrame registerFrame;

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
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == logB) {
			String id = idtf.getText();
			char[] password = pwtf.getPassword();
			ct.login(id, password);
		} else if (obj == regB) {
			registerFrame = new RegisterFrame(ct);
		}

	}
}

class RegisterFrame extends JFrame implements ActionListener {
	JTextField idtf2, namef, agef, emailf;
	JPasswordField pwtf2, pwchktf;
	JLabel idlb2, pwlb2, namelb, agelb, emaillb, sexlb, loclb, pwchklb;
	JRadioButton manR, womanR;
	DefaultComboBoxModel<Object> locModel;
	JComboBox<Object> locC;
	JButton okB, cancelB, chkB;
	Object[] locStr = { "서울", "경기도", "강원도", "경상도", "충청도", "전라도" };
	ClientThread ct;

	public RegisterFrame(ClientThread ct) {
		this.ct = ct;

		JPanel idP = new JPanel();

		// 아이디
		idlb2 = new JLabel("아이디 : ");
		idlb2.setBounds(30, 20, 100, 30);
		add(idlb2);

		idtf2 = new JTextField();
		idtf2.setBounds(130, 20, 120, 25);
		add(idtf2);

		// 중복확인버튼
		chkB = new JButton("중복확인");
		chkB.setBounds(260, 20, 90, 30);
		add(chkB);

		// 비밀번호
		pwlb2 = new JLabel("비밀번호 : ");
		pwlb2.setBounds(30, 60, 100, 30);
		add(pwlb2);

		pwtf2 = new JPasswordField();
		pwtf2.setEchoChar('*');
		pwtf2.setBounds(130, 60, 120, 25);
		add(pwtf2);

		// 비밀번호 확인
		pwchklb = new JLabel("비밀번호 확인 : ");
		pwchklb.setBounds(30, 100, 100, 30);
		add(pwchklb);

		pwchktf = new JPasswordField();
		pwchktf.setEchoChar('*');
		pwchktf.setBounds(130, 100, 120, 25);
		add(pwchktf);

		// 이름
		namelb = new JLabel("이름 : ");
		namelb.setBounds(30, 140, 100, 30);
		add(namelb);

		namef = new JTextField();
		namef.setBounds(130, 140, 120, 25);
		add(namef);

		// 나이
		agelb = new JLabel("나이 : ");
		agelb.setBounds(30, 180, 100, 30);
		add(agelb);

		agef = new JTextField();
		agef.setBounds(130, 180, 120, 30);
		add(agef);

		// 성별
		sexlb = new JLabel("성별 : ");
		sexlb.setBounds(30, 210, 100, 30);
		add(sexlb);

		// 성별 - 라디오 버튼
		manR = new JRadioButton("남", true);
		womanR = new JRadioButton("여");
		ButtonGroup genderRadio = new ButtonGroup();
		genderRadio.add(manR); // 라디오그룹에 붙이기
		genderRadio.add(womanR);

		manR.setBounds(80, 210, 50, 30);
		womanR.setBounds(130, 210, 50, 30);
		add(manR);
		add(womanR);

		// 이메일
		emaillb = new JLabel("이메일 : ");
		emaillb.setBounds(30, 250, 100, 30);
		add(emaillb);

		emailf = new JTextField();
		emailf.setBounds(130, 250, 120, 25);
		add(emailf);

		// 지역
		loclb = new JLabel("지역 : ");
		loclb.setBounds(30, 290, 100, 30);
		add(loclb);

		// 지역-라디오버튼
		locModel = new DefaultComboBoxModel<Object>(locStr);
		locC = new JComboBox<Object>(locModel);
		locC.setBounds(130, 290, 120, 30);
		add(locC);

		// 확인, 취소 버튼
		okB = new JButton("확인");
		okB.setBounds(130, 330, 60, 30);
		add(okB);
		cancelB = new JButton("취소");
		cancelB.setBounds(200, 330, 60, 30);
		add(cancelB);

		add(idP);// 붙이기

		// 버튼 리스너
		okB.addActionListener(this);
		cancelB.addActionListener(this);

		this.setTitle("회원가입");
		setSize(500, 500);
		setVisible(true);
		setLocationRelativeTo(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == okB) {
			// 회원가입 메소드
			if (chkField()) {
				JOptionPane.showMessageDialog(null, "모든 정보를 입력해 주세요.");
				return;
			} else if (!pwchk()) { // 비밀번호 확인
				JOptionPane.showMessageDialog(null, "비밀번호를 확인해 주세요");
				return;
			} else if (!numchk()) {
				JOptionPane.showMessageDialog(null, "나이는 숫자로 입력해주세요");
				return;
			} else if (chkEmail(emailf.getText())) {
				JOptionPane.showMessageDialog(null, "이메일을 정확히 입력하세요.");
				return;
			} else {
				ct.register(setMember());
				clean();
			}
		} else if (obj == cancelB) {
			this.dispose();
		}
	}

	private boolean numchk() {
		for (int index = 0; index < agef.getText().length(); index++) {
			if (agef.getText().charAt(index) < '0' || agef.getText().charAt(index) > '9') {
				return false;
			}
		}
		return true;
	}

	private boolean pwchk() {
		if (String.valueOf(pwtf2.getPassword()).equals(String.valueOf(pwchktf.getPassword()))) {
			return true;
		}
		return false;
	}

	private boolean chkField() {
		if (idtf2.getText().isEmpty() || namef.getText().isEmpty() || agef.getText().isEmpty()
				|| emailf.getText().isEmpty() || pwtf2.getPassword().length == 0) {
			return true;
		}
		return false;
	}

	private MemberDTO setMember() {
		MemberDTO dto = new MemberDTO();
		dto.setMemberId(idtf2.getText());
		dto.setMemberName(namef.getText());
		dto.setMemberGender(manR.isSelected() ? "남" : "여");
		dto.setMemberAge(Integer.valueOf(agef.getText()));
		dto.setMemberEmail(emailf.getText());
		dto.setMemberLocation(locC.getSelectedItem().toString());
		dto.setMemberPassword(String.valueOf(pwtf2.getPassword()));
		return dto;
	}

	// 이메일 형식 확인
	public boolean chkEmail(String email) {
		String reg = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
		email = emailf.getText();
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(email);
		System.out.println("입력한 이메일값 : " + email);
		System.out.println(m.matches());
		return m.matches();
	}

	private void clean() {
		idtf2.setText("");
		namef.setText("");
		pwtf2.setText("");
		emailf.setText("");
		agef.setText("");
		manR.setSelected(true);
		locC.setSelectedIndex(0);
		idtf2.requestFocus();
	} // 입력창 비우기
}