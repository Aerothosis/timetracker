
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
	JLabel userUICL = new JLabel("Last Name");
	JTextField userUICIn = new JTextField();
	JLabel emailL = new JLabel("Email");
	JTextField emailIn = new JTextField();
	JLabel usrVersion = new JLabel("Version");
	JTextField usrVersionIn = new JTextField("");
	JComboBox usrVsnLst;
	JButton addUser = new JButton("Add User");
	JButton newForm = new JButton("New Form Window");
	
	static JFrame frame = null;
	
	static String userAdmin = "";
	static String userUICAdmin = "";
	static String[] usrVsn = {"Select One","Student", "Professional"};
	static int usrVsnSel;
	
	static Connection conn;
	static PreparedStatement pst;
	static PreparedStatement pstTwo;
	static PreparedStatement pstThree;
	
	RegNewUser()
	{
		this.setSize(370, 290);
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
		
		usrVersion.setSize(50, 30);
		usrVersion.setLocation(230, 10);
		this.add(usrVersion);
		
		usrVsnLst = new JComboBox(usrVsn);
		usrVsnLst.setSize(100, 30);
		usrVsnLst.setLocation(230, 40);
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
		
		userUICL.setSize(100, 30);
		userUICL.setLocation(10, 130);
		this.add(userUICL);
		
		userUICIn.setSize(100, 30);
		userUICIn.setLocation(120, 130);
		this.add(userUICIn);
		
		emailL.setSize(100, 30);
		emailL.setLocation(10, 170);
		this.add(emailL);
		
		emailIn.setSize(100, 30);
		emailIn.setLocation(120, 170);
		this.add(emailIn);
		
		addUser.setSize(100, 30);
		addUser.setLocation(50, 210);
		addUser.addActionListener(this);
		this.add(addUser);
		
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

	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == addUser)
		{
			InsertUser();
		}
		else if(ae.getSource() == newForm)
		{
			frame.setVisible(false);
			//NewForm.CreateGUI(userAdmin, userUICAdmin);
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
				
				pst = conn.prepareStatement("INSERT INTO user_login VALUES(?,?,?,?,?,?,?,?)");
				pst.setString(1, userIdIn);
				pst.setString(2, userIn.getText());
				pst.setString(3, passCon);
				pst.setString(4, userNameIn.getText());
				pst.setString(5, userUICIn.getText());
				pst.setInt(6, 3);
				pst.setInt(7, usrVsnSel);
				pst.setString(8, converted);
				pst.execute();
				
				pstTwo = conn.prepareStatement("INSERT INTO reg_users VALUES(?,?)");
				pstTwo.setString(1, converted);
				pstTwo.setString(2, userIn.getText());
				pstTwo.execute();
				
				JOptionPane.showMessageDialog(null, "Your account has been successfully created!" +
						"Login: " + userIn.getText() + 
						"User Level: " + usrVsn[usrVsnSel]);
			}
		}catch(MySQLIntegrityConstraintViolationException m){
			
			JOptionPane.showMessageDialog(null, "Duplicate Regnum or Login info. Please try again.");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
