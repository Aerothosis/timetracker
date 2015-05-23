import javax.swing.*;

import java.sql.*;
import java.awt.event.*;


public class ResetPassOne extends JFrame implements ActionListener{

	JLabel label;
	
	static JFrame frame;
	
	static Connection conn;
	PreparedStatement pst;
	PreparedStatement pstTwo;
	
	ResetPassOne(){
		this.setSize(300, 300);
		this.setLocationRelativeTo(null);
		this.setTitle("Reset Password");
		this.setLayout(null);
	}
	
	public static void main(String[] args){
		CreateGUI();
	}
	
	public static void CreateGUI(){	
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MysqlConn.conn() + MysqlConn.loginAero());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e + " Connection Error");
		}
	
		frame = new ResetPassOne();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		
	}
}
