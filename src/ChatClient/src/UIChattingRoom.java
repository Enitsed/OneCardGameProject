package ChatClient.src;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.management.ThreadMXBean;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class UIChattingRoom extends JFrame implements ActionListener, KeyListener, ListSelectionListener{

	private JPanel contentPane;
	private JTextField tfMsg;
	public static JTextArea taChatting;
	public JButton btnClose;
	public JButton btnOut;
	public JList listMember;
	private ClientThread clientThread;
	private boolean isSelected;
	private String selectedId;
	
	public String AdminID;
	public int roomNo;
	
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
		panel.setBorder(new TitledBorder(null, "\uCC44\uD305\uCC3D", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 35, 337, 186);
		contentPane.add(panel);
		panel.setLayout(null);
		
		taChatting = new JTextArea();
		taChatting.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		taChatting.setBounds(12, 21, 313, 155);
		panel.add(taChatting);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uCC38\uC5EC\uC790 \uB9AC\uC2A4\uD2B8", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e){
				tfMsg.requestFocusInWindow();
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnOut){
			if(!isSelected){
				JOptionPane.showMessageDialog(this, "Id�� ���� �ϼ���", "���� ����", JOptionPane.ERROR_MESSAGE);
			}else{
				if(AdminID.equals(selectedId)){
					JOptionPane.showMessageDialog(this, "������ ���� ���� �ɼ� �����ϴ�.", "���� ����", JOptionPane.ERROR_MESSAGE);
				}else{
					isSelected = false;
				}
			}
		}else if(e.getSource() == btnClose){
			
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		isSelected = true;
		selectedId = String.valueOf(((JList)e.getSource()).getSelectedValue());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ENTER){
			clientThread.SendWord(tfMsg.getText());
			tfMsg.setText("");
			tfMsg.requestFocusInWindow();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void ClearData(){
		tfMsg.setText("");
	}
}
