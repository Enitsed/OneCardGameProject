
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;

public class UIWaitRoom extends JFrame implements ActionListener, MouseListener {
	public JList UserList;
	public JList roomList;
	public JButton btnLogout; 
	public JButton btnJoin;
	public JButton btnCreateRoom;
	private JPanel contentPane;
	
	private boolean isSelected;
	private int roomNo;
	private String roomTitle;
	public ClientThread clientThread;

	public UIWaitRoom(ClientThread clientThread) {
		this.clientThread = clientThread;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\uD604\uC7AC \uC811\uC18D\uB41C \uC0AC\uC6A9\uC790", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(352, 10, 139, 234);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JList list = new JList();
		list.setBounds(105, 22, 0, 0);
		panel.add(list);
		
		UserList = new JList();
		UserList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		UserList.setBounds(12, 21, 115, 203);
		panel.add(UserList);
		
		btnLogout = new JButton("로그아웃");
		btnLogout.addActionListener(this);
		btnLogout.setFont(new Font("돋움", Font.BOLD, 14));
		btnLogout.setBounds(362, 254, 129, 36);
		contentPane.add(btnLogout);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\uB300\uD654\uBC29 \uBAA9\uB85D", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 10, 328, 240);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		roomList = new JList();
		
		RoomListCellRenderer renderer = new RoomListCellRenderer();
		renderer.setDefaultTab(50);
		renderer.setTabs(new int[]{50, 170, 210, 250, 270, 300});
		roomList.setCellRenderer(renderer);
		roomList.addMouseListener(this);

		roomList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		roomList.setBounds(12, 49, 293, 186);
		panel_1.add(roomList);
		
		JLabel lblNewLabel = new JLabel("\uBC88\uD638");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblNewLabel.setBounds(12, 21, 48, 19);
		panel_1.add(lblNewLabel);
		
		JLabel label = new JLabel("\uBC29 \uC81C\uBAA9");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		label.setBounds(58, 21, 117, 19);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("\uC778\uC6D0\uC218");
		label_1.setBounds(171, 21, 54, 19);
		panel_1.add(label_1);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		JLabel label_2 = new JLabel("\uACF5\uAC1C");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		label_2.setBounds(224, 21, 48, 19);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("\uBC29\uC7A5");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		label_3.setBounds(271, 21, 45, 19);
		panel_1.add(label_3);
		//\uB85C\uADF8\uC544\uC6C3"
		btnCreateRoom = new JButton("\uBC29\uB9CC\uB4E4\uAE30");
		btnCreateRoom.setFont(new Font("", Font.BOLD, 14));
		btnCreateRoom.setBounds(22, 254, 129, 36);
		btnCreateRoom.addActionListener(this);
		contentPane.add(btnCreateRoom);
		
		btnJoin = new JButton("방입장");
		btnJoin.setFont(new Font("�ü�", Font.BOLD, 14));
		btnJoin.setBounds(163, 254, 129, 36);
		btnJoin.addActionListener(this);
		contentPane.add(btnJoin);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnLogout){
			clientThread.logOut();
		}else if(e.getSource() == btnCreateRoom){
			UICreateRoom UiCreateRoom = new UICreateRoom(clientThread);
		}else if(e.getSource() == btnJoin){
			if(!isSelected){
				JOptionPane.showMessageDialog(this, "방을 선택하세여", "메세지", JOptionPane.ERROR_MESSAGE);
			}
			
			clientThread.JoinChattingRoom(roomNo, "0");
		}//else if(e.getActionCommand() == )
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		isSelected = true;
		String selectedRoomInfo = String.valueOf(((JList) e.getSource()).getSelectedValue());
		System.out.println("selectedRoomInfo : " + selectedRoomInfo);
		if(!selectedRoomInfo.equals(null)) {
			System.out.println("zz   " + selectedRoomInfo);
			StringTokenizer st = new StringTokenizer(selectedRoomInfo, ",");
			roomNo = Integer.parseInt(st.nextToken());
			roomTitle = st.nextToken();	
		}	
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
