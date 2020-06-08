import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class ServerGUI {
	public static short port = 8080;
	private JFrame frServer;
	private JTextField txtIP;
	private static TextArea txtMessage;
	private JLabel lbStates;
	private static JLabel lbUserOnl;
	Server server;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".contentEquals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Nimbus is not available, error: " + e);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI serverWindow = new ServerGUI();
					serverWindow.frServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initialize() {
		// Create main frame
		frServer = new JFrame();
		frServer.getContentPane().setLayout(null);
		frServer.setForeground(UIManager.getColor("RadioButtonMenuItem.foreground"));
		frServer.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 15));
		frServer.getContentPane().setForeground(UIManager.getColor("RadioButtonMenuItem.acceleratorSelectionForeground"));
		frServer.setTitle("Chat Server");
		frServer.setResizable(false);
		frServer.setBounds(100, 100, 350, 540);
		frServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frServer.getContentPane().setBackground(new Color(253, 219, 191));
		
		// Create IP Address field
		JLabel lbIPaddr = new JLabel("IP ADDRESS");
		lbIPaddr.setForeground(Color.BLUE);
		lbIPaddr.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lbIPaddr.setBounds(26, 60, 89, 16);
		frServer.getContentPane().add(lbIPaddr);
		
		txtIP = new JTextField();
		txtIP.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtIP.setEditable(false);
		txtIP.setBounds(126, 55, 160, 30);		
		frServer.getContentPane().add(txtIP);
		txtIP.setColumns(12);
		try {
			txtIP.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		// Create STATUS field
		JLabel lbNewStates = new JLabel("STATUS");
		lbNewStates.setForeground(Color.BLUE);
		lbNewStates.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lbNewStates.setBounds(26, 100, 89, 16);
		frServer.getContentPane().add(lbNewStates);
		
		lbStates = new JLabel("Server States");
		lbStates.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lbStates.setBounds(126, 100, 98, 16);

		frServer.getContentPane().add(lbStates);
		lbStates.setText("<html><font color='red'>OFF</font></html>");
		
		// Create USER ONLINE number field
		JLabel lbOnliner = new JLabel("USER ON");
		lbOnliner.setForeground(Color.BLUE);
		lbOnliner.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lbOnliner.setBounds(26, 140, 89, 16);
		frServer.getContentPane().add(lbOnliner);
				
		lbUserOnl = new JLabel("0");
		lbUserOnl.setForeground(Color.BLUE);
		lbUserOnl.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lbUserOnl.setBounds(126, 140, 56, 16);
		frServer.getContentPane().add(lbUserOnl);
		
		// Create USER ONLINE name field
		txtMessage = new TextArea();					
		txtMessage.setBackground(Color.WHITE);
		txtMessage.setForeground(Color.BLUE);
		txtMessage.setFont(new Font("Consolas", Font.BOLD, 15));
		txtMessage.setEditable(false);
		txtMessage.setBounds(26, 180, 286, 237);		////// Vi tri textArea
		frServer.getContentPane().add(txtMessage);
		
		// Create STOP button
		JButton btnStop = new JButton("");
		btnStop.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		btnStop.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					server.stopserver();
					ServerGUI.updateMessage("STOP SERVER");
					lbStates.setText("<html><font color='red'>OFF</font></html>");
				} catch (Exception e) {
					e.printStackTrace();
					ServerGUI.updateMessage("STOP SERVER");
					lbStates.setText("<html><font color='red'>OFF</font></html>");
				}
			}
		});
		btnStop.setBounds(212, 437, 44, 44);
		btnStop.setIcon(new javax.swing.ImageIcon(ServerGUI.class.getResource("/images/stop.png")));
		frServer.getContentPane().add(btnStop);
				
		// Create START button
		JButton btnStart = new JButton("");
		btnStart.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		btnStart.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					server = new Server(8080);
					
					ServerGUI.updateMessage("START SERVER");
					lbStates.setText("<html><font color='green'>ON</font></html>");
				} catch (Exception e) {
					ServerGUI.updateMessage("START ERROR");
					e.printStackTrace();
				}
			}
		});

		btnStart.setBounds(94, 437, 44, 44);			/////// Vi tri button START
		btnStart.setIcon(new javax.swing.ImageIcon(ServerGUI.class.getResource("/images/start.png")));
		frServer.getContentPane().add(btnStart);
	}
	
	public ServerGUI() {
		initialize();
	}
	
	public static String getLabelUserOnl() {
		return lbUserOnl.getText();
	}
	
	public static void incrNumOfClients() {
		int num = Integer.parseInt(lbUserOnl.getText());
		lbUserOnl.setText(Integer.toString(num + 1));
	}
	
	public static void decrNumOfClients() {
		int num = Integer.parseInt(lbUserOnl.getText());
		lbUserOnl.setText(Integer.toString(num - 1));
	}
	
	public static void updateMessage(String msg) {
		txtMessage.append(msg + "\n");
	}

}
