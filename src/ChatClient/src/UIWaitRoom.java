import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;

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

	ImageIcon icon;
	public UIWaitRoom(ClientThread clientThread) {
		this.clientThread = clientThread;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 370);
		icon = new ImageIcon("src/img/background.png");
		contentPane = new JPanel(){
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, 537, 370, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "현재 접속된 사용자", TitledBorder.LEADING, TitledBorder.TOP, null, Color.YELLOW));
		panel.setBounds(352, 10, 139, 234);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JList list = new JList();
		list.setBounds(105, 22, 0, 0);
		panel.add(list);
		
		UserList = new JList();
		UserList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		UserList.setBounds(12, 21, 115, 203);
		panel.setBackground(new Color(255,0,0,0));
		panel.add(UserList);
		
		UserList.addMouseListener(this);
		
		btnLogout = new JButton(new ImageIcon("src/buttonImg/logout.png"));
		btnLogout.addActionListener(this);
		btnLogout.setBounds(362, 254, 129, 36);
		btnLogout.setBorderPainted(false);
		btnLogout.setContentAreaFilled(false);
		contentPane.add(btnLogout);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "대화방 목록", TitledBorder.LEADING, TitledBorder.TOP, null, Color.yellow));
		panel_1.setBounds(12, 10, 328, 240);
		panel_1.setBackground(new Color(255,0,0,0));
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
		
		JLabel lblNewLabel = new JLabel("번호");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblNewLabel.setBounds(12, 21, 48, 19);
		lblNewLabel.setBackground(new Color(255,0,0,0));
		lblNewLabel.setForeground(Color.YELLOW);
		panel_1.add(lblNewLabel);
		
		JLabel label = new JLabel("방제목");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		label.setBounds(58, 21, 117, 19);
		label.setBackground(new Color(255,0,0,0));
		label.setForeground(Color.YELLOW);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("인원수");
		label_1.setBounds(171, 21, 54, 19);
		panel_1.add(label_1);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		label_1.setBackground(new Color(255,0,0,0));
		label_1.setForeground(Color.YELLOW);
		
		JLabel label_2 = new JLabel("공개");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		label_2.setBounds(224, 21, 48, 19);
		label_2.setBackground(new Color(255,0,0,0));
		label_2.setForeground(Color.YELLOW);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("방장");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		label_3.setBounds(271, 21, 45, 19);
		label_3.setBackground(new Color(255,0,0,0));
		label_3.setForeground(Color.YELLOW);
		panel_1.add(label_3);
		//\uB85C\uADF8\uC544\uC6C3"
		btnCreateRoom = new JButton(new ImageIcon("src/buttonImg/createRoom.png"));
		btnCreateRoom.setBounds(22, 254, 129, 36);
		btnCreateRoom.addActionListener(this);
		btnCreateRoom.setBorderPainted(false);
		btnCreateRoom.setContentAreaFilled(false);
		contentPane.add(btnCreateRoom);
		
		btnJoin = new JButton(new ImageIcon("src/buttonImg/join.png"));
		btnJoin.setBounds(163, 254, 129, 36);
		btnJoin.addActionListener(this);
		btnJoin.setBorderPainted(false);
		btnJoin.setContentAreaFilled(false);
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
		Object obj = e.getSource();
		isSelected = true;
		String selectedData = String.valueOf(((JList) e.getSource()).getSelectedValue());
		System.out.println("selectedRoomInfo : " + selectedData);
		if(!selectedData.equals("")) {
			System.out.println("zz   " + selectedData);
			StringTokenizer st = new StringTokenizer(selectedData, ",");
			roomNo = Integer.parseInt(st.nextToken());
			roomTitle = st.nextToken();	
		}
		
		if(obj==roomList && e.getClickCount()==2) {
			System.out.println("zz   " + selectedData);
			StringTokenizer st = new StringTokenizer(selectedData, ",");
			roomNo = Integer.parseInt(st.nextToken());
			roomTitle = st.nextToken();	
			clientThread.JoinChattingRoom(roomNo, "0");
		}else if(obj == UserList && e.getClickCount()==2) {
			clientThread.mempro(selectedData);
			//new UIMemInfoFrame(clientThread);
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
