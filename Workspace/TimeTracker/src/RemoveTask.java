import java.sql.*;
import java.util.*;
import java.awt.event.*;

import javax.swing.event.*;
import javax.swing.*;


public class RemoveTask extends JFrame implements ActionListener,ListSelectionListener
{
	
	static String projID = "";
	static ArrayList<String> tasks = new ArrayList<String>();
	
	static JFrame frame = null;
	
	static Connection conn = null;
	static PreparedStatement pst = null;
	static PreparedStatement pstTwo = null;
	static PreparedStatement pstThree = null;
	
	RemoveTask()
	{
		this.setSize(400, 300);
		this.setLocation(400, 300);
		this.setTitle("Select Task");
		this.setLayout(null);
	}
	
	public static void main(String[] args) 
	{
		CreateGUI("", "");
	}

	public static void CreateGUI(String selPj, String selClient)
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MysqlConn.conn() + MysqlConn.loginAero());
			
			TaskPull(selPj, selClient);
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e);
		}
		
		frame = new RemoveTask();
		frame.setVisible(true);
	}

	public void valueChanged(ListSelectionEvent lse) 
	{
		
	}

	public void actionPerformed(ActionEvent ae) 
	{
		
	}
	
	public static void TaskPull(String selPj, String selClient)
	{
		tasks = null;
		tasks = new ArrayList<String>();
		String selectProj = selPj;
		
		try
		{
			pst = conn.prepareStatement("SELECT project_id FROM project WHERE proj_name=? AND client_id=(SELECT client_id FROM client WHERE client_name=?)");
			pst.setString(1, selectProj);
			pst.setString(2, selClient);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				projID = rs.getString(1);
				pstTwo = conn.prepareStatement("SELECT ass_name FROM assignment WHERE proj_id=? AND user_id=?");
				pstTwo.setString(1,  projID);
				pstTwo.setString(2, MainWindow.userID);
				ResultSet rsTwo = pstTwo.executeQuery();
				
				while(rsTwo.next())
				{
					String taskIn = rsTwo.getString(1);
					tasks.add(new String(taskIn));
				}
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e + " Task");
		}
	}
}
