


import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


public class AddNewClass extends JFrame implements ActionListener
{
	static JLabel clientL = new JLabel("Client's Name") ;
	static JTextField clientIn = new JTextField("");
	static JButton addClient = new JButton("Add");
	
	static JFrame frame = null;
	
	static Connection conn = null;
	static PreparedStatement pst = null;
	static PreparedStatement pstTwo = null;
	
	AddNewClass()
	{
		this.setSize(300, 200);
		this.setLocation(600, 300);
		this.setTitle("Add New Client");
		this.setLayout(null);
		
		clientL.setSize(100, 30);
		clientL.setLocation(10, 10);
		this.add(clientL);
		
		clientIn.setSize(100, 30);
		clientIn.setLocation(10, 45);
		this.add(clientIn);
		
		addClient.setSize(100, 30);
		addClient.setLocation(10, 80);
		addClient.addActionListener(this);
		this.add(addClient);
	}
	
	public static void main(String[] args) 
	{
		CreateGUI();
	}
	
	public static void CreateGUI()
	{
		frame = new AddNewClass();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) 
	{
		CodeExecute();
	}

	public static void CodeExecute()
	{
		String userID = MainWindow.userID;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connStr = MysqlConn.conn();
			conn = DriverManager.getConnection(connStr + MysqlConn.loginAero());
			pstTwo = conn.prepareStatement("SELECT * FROM client WHERE user_id = ? AND client_name = ?");
			pstTwo.setString(1, userID + "");
			pstTwo.setString(2, clientIn.getText() + "");
			ResultSet rs = pstTwo.executeQuery();
			
			if(!rs.next())
			{
				pst = conn.prepareStatement("INSERT INTO client (client_id,client_name,user_id) VALUES (?, ?, ?)");
				pst.setString(1, CmnCode.RandomGen(10) + "");
				pst.setString(2, clientIn.getText() + "");
				pst.setString(3, userID + "");
				//ResultSet rs = pst.executeQuery();
				pst.execute();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "You already have a client with that name.\nPlease enter a new name");
			}
		} 
		catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e)
		{
			CodeExecute();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
