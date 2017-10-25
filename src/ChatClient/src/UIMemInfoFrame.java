import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class UIMemInfoFrame extends JFrame implements ActionListener {

	JLabel memidlb, memsexlb, memgradelb, memwinlb, memloselb, memvaluelb, gradelb, memRatelb;
	JButton okB;
	ClientThread ct;

	public UIMemInfoFrame(ClientThread ct) {
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

		// 계급
		gradelb = new JLabel("계급 : " + ct.searchRank);
		gradelb.setBounds(30, 100, 200, 50);
		add(gradelb);
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

		// 승률
		memRatelb = new JLabel("승률 : " + ct.searchRate + "%");
		memRatelb.setBounds(30, 230, 100, 30);
		add(memRatelb);

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