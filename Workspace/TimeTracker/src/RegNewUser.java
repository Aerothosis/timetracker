
import javax.swing.*;

import com.mysql.jdbc.exceptions.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegNewUser extends JFrame implements ActionListener
{
	JPanel content = new JPanel();
	JScrollPane scroll = null;
	
	JLabel userL = new JLabel("Login");
	JTextField userIn = new JTextField();
	JLabel passL = new JLabel("Password");
	JTextField passIn = new JTextField();
	JLabel userNameL = new JLabel("First Name");
	JTextField userNameIn = new JTextField();
	JLabel userNameLL = new JLabel("Last Name");
	JTextField userNameLIn = new JTextField();
	JLabel emailL = new JLabel("Email");
	JTextField emailIn = new JTextField();
	JLabel usrVersion = new JLabel("Version");
	JTextField usrVersionIn = new JTextField("");
	JComboBox usrVsnLst;
	JButton addUser = new JButton("Add User");
	JButton activateUsr = new JButton("Activate");
	
	static JFrame frame = null;
	
	static String userAdmin = "";
	static String userUICAdmin = "";
	static String[] usrVsn = {"Select One","Student", "Professional"};
	static int usrVsnSel;
	static String newUsrPin;
	
	static Connection conn;
	static PreparedStatement pst;
	static PreparedStatement pstTwo;
	static PreparedStatement pstThree;
	
	RegNewUser()
	{
		this.setSize(370, 400);
		//this.setLocation(400, 200);
		this.setLocationRelativeTo(null);
		this.setTitle("New User Panel");
		this.setLayout(null);
		
		content.setLayout(null);
		content.setPreferredSize(new Dimension(400, 400));
		userL.setSize(100, 30);
		userL.setLocation(10, 10);
		this.add(userL);
		
		userIn.setSize(100, 30);
		userIn.setLocation(120, 10);
		this.add(userIn);
		
		passL.setSize(100, 30);
		passL.setLocation(10, 50);
		this.add(passL);
		
		passIn.setSize(100, 30);
		passIn.setLocation(120, 50);
		this.add(passIn);
		
		userNameL.setSize(100, 30);
		userNameL.setLocation(10, 90);
		this.add(userNameL);
		
		userNameIn.setSize(100, 30);
		userNameIn.setLocation(120, 90);
		this.add(userNameIn);
		
		userNameLL.setSize(100, 30);
		userNameLL.setLocation(10, 130);
		this.add(userNameLL);
		
		userNameLIn.setSize(100, 30);
		userNameLIn.setLocation(120, 130);
		this.add(userNameLIn);
		
		emailL.setSize(100, 30);
		emailL.setLocation(10, 170);
		this.add(emailL);
		
		emailIn.setSize(100, 30);
		emailIn.setLocation(120, 170);
		this.add(emailIn);
		
		usrVersion.setSize(50, 30);
		usrVersion.setLocation(10, 210);
		this.add(usrVersion);
		
		usrVsnLst = new JComboBox(usrVsn);
		usrVsnLst.setSize(100, 30);
		usrVsnLst.setLocation(120, 210);
		usrVsnLst.addItemListener(
			new ItemListener(){
				public void itemStateChanged(ItemEvent event){
					if(event.getStateChange() == ItemEvent.SELECTED){
						usrVsnSel = usrVsnLst.getSelectedIndex();
					}
				}
			}
			);
		this.add(usrVsnLst);
		
		addUser.setSize(100, 30);
		addUser.setLocation(10, 260);
		addUser.addActionListener(this);
		this.add(addUser);
		
		activateUsr.setSize(100, 30);
		activateUsr.setLocation(10, 300);
		activateUsr.addActionListener(this);
		this.add(activateUsr);
		
		//scroll = new JScrollPane(content);
		//scroll.setPreferredSize(new Dimension(400, 400));
		//this.add(scroll, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) 
	{
		CreateGUI();
	}
	
	public static void CreateGUI()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MysqlConn.conn() + MysqlConn.loginAero());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e + " Connection Error");
		}
		
		frame = new RegNewUser();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae){
		if(ae.getSource() == addUser){
			InsertUser();
			RegUsrActivate.CreateGUI();
		}else if(ae.getSource() == activateUsr){
			RegUsrActivate.CreateGUI();
		}
		
	}
	
	public void InsertUser(){
		try{			
			pst = conn.prepareStatement("Select * from user_login where username = ?");
			pst.setString(1, userIn.getText());
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				JOptionPane.showMessageDialog(null, "User with that login already exists." + '\n' + "Please try again.");
			}else{
				String regnuminput = CmnCode.RandomGen(16);
				String converted = CmnCode.HashBash(regnuminput);
				String userIdIn = CmnCode.UserIDRandom(9);
				String passCon = CmnCode.HashBash(passIn.getText());
				
				pst = conn.prepareStatement("INSERT INTO user_login(user_id,username,pass,name_first,name_last,email,account_status,version) VALUES(?,?,?,?,?,?,?,?)");
				pst.setString(1, userIdIn);
				pst.setString(2, userIn.getText());
				pst.setString(3, passCon);
				pst.setString(4, userNameIn.getText());
				pst.setString(5, userNameLIn.getText());
				pst.setString(6, emailIn.getText());// need email input here
				pst.setInt(7, 0); //account_status - 0 is default and means deactivated
				pst.setInt(8, usrVsnSel); //version - Student OR Professional
				//pst.setString(8, converted); //need to take this out, shift to post-email verification
				pst.execute();
				
				//pstTwo = conn.prepareStatement("INSERT INTO reg_users VALUES(?,?)");
				//pstTwo.setString(1, converted);
				//pstTwo.setString(2, userIn.getText());
				//pstTwo.execute();
				
				newUsrPin = CmnCode.RandomGen(10);
				pstThree = conn.prepareStatement("INSERT INTO user_new_reg VALUES(?,?)");
				pstThree.setString(1, userIdIn);
				pstThree.setString(2, newUsrPin);
				pstThree.execute();
				
				SendRegMail();
				
				JOptionPane.showMessageDialog(null, "Your account has been successfully created!" +
						"\n\nLogin: " + userIn.getText() +
						"\nUser Level: " + usrVsn[usrVsnSel] + 
						"\n\nPlease verify your email with the pin we sent you.");
			}
		}catch(MySQLIntegrityConstraintViolationException m){
			
			JOptionPane.showMessageDialog(null, "Duplicate Regnum or Login info. Please try again.");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void SendRegMail(){
		try{
		String from = "no-reply@mfurtado.com";
		String to = emailIn.getText();
		String subject = "Email Verification - TimeTracker";
		String message = "Thank you for using TimeTracker!\n\n"
				+ "Please use the code below to verify your email account\n"
				+ "and to activate your account with TimeTracker.\n\n"
				+ newUsrPin + '\n' + '\n' +
				"Thanks again,"
				+ "Mike - Lead Dev of TimeTracker";
		EmailVerify sendMail = new EmailVerify(from, to, subject, message);
		sendMail.send();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
