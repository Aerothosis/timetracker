
/*
Goal with this file is to make a window that will transition to new frames for client and project. The lists will not be displayed at
the same time and will, instead, shift once one is selected. Shift will take place after the user presses a button, NOT after
the user selects an item in the list.
 */

import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;


public class RemoveClient extends JFrame implements ActionListener,ListSelectionListener
{
	JLabel clientL = new JLabel("Clients");
	static JList<String> clientLst = null;//new JList<String>();
	static JScrollPane clientScrl = null;
	static ArrayList<String> clients = new ArrayList<String>();
	static Object[] clientTwo = null;
	static String selClient = "";
	static String tempClient = "";
	static String clID = "";
	static boolean cliSel = false;
	
	static ArrayList<String> projects = new ArrayList<String>();
	static String projID = "";
	
	static ArrayList<String> tasks = new ArrayList<String>();
	static String taskID = "";
	
	JButton next = new JButton("Delete");
	JButton refresh = new JButton("Refresh List");
	
	static JFrame frame = null;
	
	static Connection conn = null;
	static PreparedStatement pst = null;
	static PreparedStatement pstTwo = null;
	static PreparedStatement pstThree = null;
	
	RemoveClient()
	{
		this.setSize(400, 300);
		this.setLocation(400, 300);
		this.setTitle("Remove Client");
		this.setLayout(null);
		
		clientL.setSize(100, 30);
		clientL.setLocation(10, 10);
		this.add(clientL);
		
		clientLst.addListSelectionListener(this);
		clientScrl = new JScrollPane(clientLst);
		clientScrl.setSize(100, 100);
		clientScrl.setLocation(10, 40);
		this.add(clientScrl);
		
		next.setSize(100, 30);
		next.setLocation(150, 60);
		next.addActionListener(this);
		next.setMnemonic(KeyEvent.VK_D);
		this.add(next);
		
		refresh.setSize(150, 30);
		refresh.setLocation(10, 160);
		refresh.addActionListener(this);
		this.add(refresh);
	}
	
	public static void main(String[] args) 
	{
		CreateGUI();
	}

	public static void CreateGUI()
	{
		NullUp();
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MysqlConn.conn() + MysqlConn.loginAero());
			
			ClientPull();
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e);
		}
		
		frame = new RemoveClient();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == next)
		{
			if(selClient.length() > 0)
			{
				int confirmOne = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete " + selClient + "?");
				
				if(confirmOne == 0)
				{
					int confirmTwo = JOptionPane.showConfirmDialog(null, "This will delete ALL projects and tasks associated with \n"
							+ "this client. Are you really sure?");
					
					if(confirmTwo == 0)
					{
						ProjPull(selClient);
						DeleteStuff();
						JOptionPane.showMessageDialog(null, selClient + " was successfully removed!");
					}
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please select a client first.");
			}
		}
		else if(ae.getSource() == refresh)
		{
			frame.dispose();
			RemoveClient.CreateGUI();
		}
	}
	
	public void valueChanged(ListSelectionEvent lse) 
	{
		int index = clientLst.getSelectedIndex();
		tempClient = clients.get(index).toString();
		
		if(tempClient != selClient)
		{
			selClient = tempClient;
		}
	}
	
	public static void ClientPull()
	{
		try 
		{
			pst = conn.prepareStatement("SELECT client_name FROM client WHERE user_id=?");
			pst.setString(1, MainWindow.userID);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				String client = rs.getString(1);
				clients.add(new String(client));
			}
			
			clientTwo = new Object[clients.size()];
			for(int counter = 0;counter<clientTwo.length;counter++)
			{
				clientTwo[counter] = clients.get(counter);
			}
			clientLst = new JList(clientTwo);
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public static void ProjPull(String selCl)
	{
		cliSel = true;
		projects = null;
		projects = new ArrayList<String>();
		String selectCli = selCl;
				
		
		try 
		{
			
			pst = conn.prepareStatement("SELECT client_id FROM client WHERE client_name=? AND user_id=?");
			pst.setString(1, selectCli);
			pst.setString(2, MainWindow.userID);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				clID = rs.getString(1);
				pstTwo = conn.prepareStatement("SELECT proj_name FROM project WHERE client_id=?");
				pstTwo.setString(1, clID);
				ResultSet rsTwo = pstTwo.executeQuery();
				
				while(rsTwo.next())
				{
					String projIn = rsTwo.getString(1);
					projects.add(new String(projIn));
				}				
			}
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e + " PROJ");
		}
	}
	
	public static void TaskPull(String selPj)
	{
		tasks = null;
		tasks = new ArrayList<String>();
		String selectProj = selPj;
		
		try
		{
			pst = conn.prepareStatement("SELECT project_id FROM project WHERE proj_name=? AND client_id=?");
			pst.setString(1, selectProj);
			pst.setString(2, clID);
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
	
	public static void DeleteStuff()
	{
		
		try
		{
			
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);		
			}
		
		try
		{	
			for(int counter = 0; counter < projects.size(); counter++)
			{
				String tempProj = projects.get(counter);
				TaskPull(tempProj);
				
				try{
				for(int count = 0; count < tasks.size(); count++)
				{
					
					String tempTask = tasks.get(count);
					pstThree = conn.prepareStatement("DELETE FROM assignment WHERE ass_name=? AND proj_id=?");
					pstThree.setString(1, tempTask);
					pstThree.setString(2, projID);
					pstThree.execute();
				}}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e + " pst three");
				}
				
				pstTwo = conn.prepareStatement("DELETE FROM project WHERE proj_name=? AND client_id=?");
				pstTwo.setString(1, tempProj);
				pstTwo.setString(2, clID);
				pstTwo.execute();
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		}
		
		try
		{
			pst = conn.prepareStatement("DELETE FROM client WHERE client_name=?");
			pst.setString(1, selClient);
			
			//ResultSet rs = pst.executeQuery();
			pst.execute();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	public static void NullUp()
	{
		
		clientLst = null;//new JList<String>();
		clientScrl = null;
		clients = new ArrayList<String>();
		clientTwo = null;
		selClient = "";
		tempClient = ""; 
		clID = "";
		 
		//frame = null;
		conn = null;
		pst = null; 
		pstTwo = null; 
		pstThree = null;
	}
}
