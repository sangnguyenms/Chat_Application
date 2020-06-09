package register;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

import clients.ClientsChatGUI;
import AppData.Peer;
import tags.Decode;
import tags.Encode;
import tags.Tags;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;



public class Register {
 protected static final String String_ = null;
 protected static final Socket Socket_ = null;
 private static String NAME_FAILED = "THIS NAME CONTAINS INVALID CHARACTER. PLEASE TRY AGAIN";
 private static String NAME_EXIST = "THIS NAME HAS BEEN EXIST. PLEASE TRY REPLACE NEW NAME AGAIN";
 private static String SERVER_NOT_START = "TURN ON SERVER BEFORE START";

 private Pattern checkName = Pattern.compile("[_a-zA-Z][_a-zA-Z0-9]*");////

 JFrame frameLoginForm;
 private JLabel ErrorLb;
 private String name = "", IP = "",password=""; 
 private JTextField txtIP;	
 private JTextField txtUsername;
 private JButton registerButton;
 public static void main(String[] args) {
	 try {
			for (javax.swing.UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
         {
             if ("Nimbus".equals(info.getName()))
             {
                 javax.swing.UIManager.setLookAndFeel(info.getClassName());
                 break;
             }
         }
		} catch(Exception e) {
			  System.out.println("Error setting native LAF: " + e);
		}
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     Register window = new Register();
     window.frameLoginForm.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }
 public Register(String IP) {
	  EventQueue.invokeLater(new Runnable() {
	   public void run() {
	    try {
	     Register window=new Register();
	     window.frameLoginForm.setVisible(true);
	    } catch (Exception e) {
	     e.printStackTrace(); 
	    }
	   }
	  });
}
 public Register() {
  initialize();
 }

 private void initialize() {
  frameLoginForm = new JFrame();
  frameLoginForm.setTitle("Register Form");
  frameLoginForm.setResizable(false);
  frameLoginForm.setBounds(600, 100, 550, 390);
  frameLoginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frameLoginForm.getContentPane().setLayout(null);

  JLabel HostServerLb = new JLabel("IP Server");
  HostServerLb.setFont(new Font("Segoe UI", Font.BOLD, 13));
  HostServerLb.setBounds(40, 70, 86, 20);
  frameLoginForm.getContentPane().add(HostServerLb);

  JLabel UserNameLb = new JLabel("User Name");
  UserNameLb.setFont(new Font("Segoe UI", Font.BOLD, 13));
  UserNameLb.setBounds(40, 134, 106, 38);
  frameLoginForm.getContentPane().add(UserNameLb);
  
  ErrorLb = new JLabel("");
  ErrorLb.setBounds(66, 287, 399, 20);
  frameLoginForm.getContentPane().add(ErrorLb);

  txtIP = new JTextField();
  txtIP.setBounds(128, 70, 185, 28);
  frameLoginForm.getContentPane().add(txtIP);
  txtIP.setColumns(20);

  registerButton = new JButton("REGISTER");
  registerButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
  registerButton.addActionListener(new 	ActionListener() {

   @SuppressWarnings("deprecation")
public void actionPerformed(ActionEvent arg0) {
    name = txtUsername.getText();
    //password=passwordField.getText();
    ErrorLb.setVisible(false);
    IP = txtIP.getText();


    //must edit here
    if (checkName.matcher(name).matches() && !IP.equals("")) {
     try {
      Random rd = new Random();
      int portPeer = 10000 + rd.nextInt() % 1000; 
      InetAddress ipServer = InetAddress.getByName(IP);
      int portServer = Integer.parseInt("8080"); 
      Socket socketClient = new Socket( ipServer, portServer);
      String msg = Encode.Login(name, password, Integer.toString(portPeer));
      ObjectOutputStream serverOutputStream = new ObjectOutputStream(socketClient.getOutputStream());
      serverOutputStream.writeObject(msg);
      serverOutputStream.flush();
      
      ObjectInputStream serverInputStream = new ObjectInputStream(socketClient.getInputStream());
      msg = (String) serverInputStream.readObject(); 
      socketClient.close();
      if (msg.equals(Tags.SESSION_DENY_TAG)) {
       ErrorLb.setText(NAME_EXIST);
       ErrorLb.setVisible(true); 
       return;
      }
     new ClientsChatGUI(name, guest, socketClient, portPeer);
      //new menuGUI(IP, portPeer, "toan", msg);
      frameLoginForm.dispose();
     } catch (Exception e) {
      ErrorLb.setText(SERVER_NOT_START);
      ErrorLb.setVisible(true);
      e.printStackTrace();
     }
    }
    else {
     ErrorLb.setText(NAME_FAILED);
     ErrorLb.setVisible(true);
     ErrorLb.setText(NAME_FAILED);
    }
   }
  });
  
  registerButton.setBounds(128,210, 169, 63);
  frameLoginForm.getContentPane().add(registerButton);
  
  txtUsername = new JTextField();
  txtUsername.setFont(new Font("Segoe UI", Font.BOLD, 13));
  txtUsername.setColumns(10); 
  txtUsername.setBounds(128, 138, 366, 34);
  frameLoginForm.getContentPane().add(txtUsername);
  ErrorLb.setVisible(false);
 
  
 }
}