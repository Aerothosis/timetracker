
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;


public class RegUsrActivate extends JFrame implements ActionListener{

	JLabel actvL = new JLabel("Activation Code");
	static JTextField actvIn = new JTextField("");
	JButton submit = new JButton ("Submit");
	
	static JFrame frame;
	
	static Connection conn;
	PreparedStatement pst;
	PreparedStatement pstTwo;
	PreparedStatement pstThree;
	PreparedStatement pstFour;
	
	RegUsrActivate(){
		this.setSize(190, 170);
		this.setLocationRelativeTo(null);
		this.setTitle("Activate Account");
		this.setLayout(null);
		
		actvL.setSize(150, 30);
		actvL.setLocation(37, 10);
		this.add(actvL);
		
		actvIn.setSize(150, 30);
		actvIn.setLocation(10, 50);
		actvIn.addActionListener(this);
		this.add(actvIn);
		
		submit.setSize(100, 30);
		submit.setLocation(35, 90);
		submit.addActionListener(this);
		this.add(submit);
	}
	
	public static void main(String[] args){
		CreateGUI();
	}
	
	static void CreateGUI(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MysqlConn.conn() + MysqlConn.loginAero());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		frame = new RegUsrActivate();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		ActivateUser();
		frame.dispose();
	}
	
	public void ActivateUser(){
		try {
			pst = conn.prepareStatement("SELECT user_id FROM user_new_reg WHERE new_user_pin=?");
			pst.setString(1, actvIn.getText());
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()){
				String userID = rs.getString(1);
				String regnuminput = CmnCode.RandomGen(16);
				String converted = CmnCode.HashBash(regnuminput);
				pstTwo = conn.prepareStatement("INSERT INTO reg_users VALUES(?,(SELECT username FROM user_login WHERE user_id=?))");
				pstTwo.setString(1, converted);
				pstTwo.setString(2, userID);
				pstTwo.execute();
				
				pstThree = conn.prepareStatement("UPDATE user_login SET regnum=?,account_status=?  WHERE user_id=?");
				pstThree.setString(1, converted);
				pstThree.setInt(2, 1);
				pstThree.setString(3, userID);
				pstThree.execute();
				
				pstFour = conn.prepareStatement("DELETE FROM user_new_reg WHERE user_id=?");
				pstFour.setString(1, userID);
				pstFour.execute();
				
				JOptionPane.showMessageDialog(null, "You have successfully activated your account!");
			}else{
				JOptionPane.showMessageDialog(null, "There is no activation code that matches your\n"
						+ "input. Please verify your input matches the code emailed\n"
						+ "to you and try again.");
			}
			
			
			
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
}
