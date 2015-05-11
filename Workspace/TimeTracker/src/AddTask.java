


import javax.swing.*;

import java.awt.event.*;
import java.sql.*;
import java.util.*;


public class AddTask extends JFrame implements ActionListener
{

	static JTextField newClientIn = new JTextField("Client");
	static JTextField newProjIn = new JTextField("Proj");
	JLabel newTaskL = new JLabel("New Task");
	static JTextField newTaskIn = new JTextField();
	JButton addTask = new JButton("Add");
	//JButton refresh = new JButton("Refresh Lists");
	JButton cliFrame = new JButton("Clients");
	JButton projFrame = new JButton("Projects");
	
	static String selProj = "";
	static String projID = "";
	static String selClient = "";

	static JFrame frame = null;
	
	static Connection conn = null;
	static PreparedStatement pst = null;
	static PreparedStatement pstTwo = null;
	static PreparedStatement pstThree = null;
	
	AddTask()
	{
		this.setSize(400, 300);
		this.setLocation(400, 300);
		this.setTitle("Add Task");
		this.setLayout(null);
		
		newTaskL.setSize(100, 30);
		newTaskL.setLocation(10, 10);
		this.add(newTaskL);
		
		newTaskIn.setSize(100, 30);
		newTaskIn.setLocation(10, 40);
		this.add(newTaskIn);
		
		addTask.setSize(100, 30);
		addTask.setLocation(150, 40);
		addTask.addActionListener(this);
		this.add(addTask);
		
		cliFrame.setSize(100, 30);
		cliFrame.setLocation(150, 80);
		cliFrame.addActionListener(this);
		this.add(cliFrame);
		
		projFrame.setSize(100, 30);
		projFrame.setLocation(150, 120);
		projFrame.addActionListener(this);
		this.add(projFrame);
	}
	
	public static void main(String[] args) 
	{
		CreateGUI("","");
	}
	
	public static void CreateGUI(String proj, String cli)
	{
		NullUp();
		selProj = proj;
		selClient = cli;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MysqlConn.conn() + MysqlConn.loginAero());
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e);
		}
		
		frame = new AddTask();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == addTask)
		{
			InsertTask();
		}
		else if(ae.getSource() == cliFrame)
		{
			frame.dispose();
			SelClient.CreateGUI(0);
		}
		else if(ae.getSource() == projFrame)
		{
			frame.dispose();
			SelProj.CreateGUI(selClient, 0);
		}
	}
	
	public static void InsertTask()
	{
		try 
		{
			pst = conn.prepareStatement("SELECT project_id FROM project WHERE proj_name=? AND client_id=(SELECT client_id FROM client WHERE client_name=?)");
			pst.setString(1, selProj);
			pst.setString(2, selClient);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next())
			{
				projID = rs.getString(1);
				pstTwo = conn.prepareStatement("SELECT ass_id FROM assignment WHERE ass_name=? AND proj_id=?");
				pstTwo.setString(1, newTaskIn.getText());
				pstTwo.setString(2, projID);
				ResultSet rs2 = pstTwo.executeQuery();
				
				if(!rs2.next())
				{
					pstThree = conn.prepareStatement("INSERT INTO assignment (ass_id,ass_name,proj_id,user_id) VALUES (?, ?, ?, ?)");
					pstThree.setString(1, CmnCode.RandomGen(10));
					pstThree.setString(2, newTaskIn.getText());
					pstThree.setString(3, projID);
					pstThree.setString(4, MainWindow.userID);
					pstThree.execute();
					JOptionPane.showMessageDialog(null, "Assignment " + newTaskIn.getText() + " added.");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "You already have an assignment with\nthat name for this project.");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "FAILED");
			}
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void NullUp()
	{
		selProj = ""; 
		projID = "";
		 
		frame = null;
		conn = null;
		pst = null; 
		pstTwo = null; 
		pstThree = null;
	}
}
