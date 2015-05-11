


import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;
import java.sql.*;
import java.util.*;


public class SelProj extends JFrame implements ActionListener,ListSelectionListener 
{
	JMenuBar menuBar = new JMenuBar();
	JMenu menuFile, menuEdit, menuSystem;
	JMenuItem menuNewClient, menuNewProj, menuSaveAs, menuOpen, menuExit; //menuFile, in order top to bottom
	JMenuItem menuPreview, menuAddTask; //menuEdit, in order top to bottom
	JMenuItem menuLogout; // menuSystem, in order top to bottom

	JLabel projL = new JLabel("Projects");
	static JList<String> projList = null;
	static JScrollPane projScrl = null;
	static ArrayList<String> projects = new ArrayList<String>();
	static Object[] projectsTwo = null;
	static String selProj = "";
	static String tempProj = "";
	static String projID = "";
	static String clID = "";
	static ArrayList<String> tasks = new ArrayList<String>();
	
	JButton refresh = new JButton("Refresh Lists");
	static JButton prev = new JButton("< Clients");
	static JButton next = new JButton("Next >");
	
	static JFrame frame = null;
	
	static Connection conn = null;
	static PreparedStatement pst = null;
	static PreparedStatement pstTwo = null;
	static PreparedStatement pstThree = null;
	
	static boolean cliSel = false;
	static String selClient = "";
	
	static int delKey = 0;
	
	SelProj()
	{
		this.setSize(400, 300);
		this.setLocation(400, 300);
		this.setTitle("Select Project");
		this.setLayout(null);
		
		projL.setSize(100, 30);
		projL.setLocation(10, 10);
		this.add(projL);
		
		projList.addListSelectionListener(this);
		projScrl = new JScrollPane(projList);
		projScrl.setSize(100, 100);
		projScrl.setLocation(10, 40);
		this.add(projScrl);
		
		prev.setSize(100, 30);
		prev.setLocation(150, 100);
		prev.addActionListener(this);
		prev.setMnemonic(KeyEvent.VK_C);
		this.add(prev);
		
		next.setSize(100, 30);
		next.setLocation(150, 60);
		next.addActionListener(this);
		next.setMnemonic(KeyEvent.VK_N);
		this.add(next);
		
		refresh.setSize(150, 30);
		refresh.setLocation(10, 160);
		refresh.addActionListener(this);
		this.add(refresh);
	}
	
	public JMenuBar createMenuBar()
	{
		menuFile = new JMenu("New");
		menuFile.getAccessibleContext().setAccessibleDescription("File Menu");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menuFile);
		
		menuNewClient = new JMenuItem("Client");
		menuNewClient.addActionListener(this);
		menuFile.add(menuNewClient);
		
		menuNewProj = new JMenuItem("Project");
		menuNewProj.addActionListener(this);
		menuFile.add(menuNewProj);
		
		return menuBar;
	}
	
	public static void main(String[] args) 
	{
		CreateGUI("First Client", 0);
	}
	
	public static void CreateGUI(String selCli, int delete)
	{
		NullUp();
		selClient = selCli;
		delKey = delete;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MysqlConn.conn() + MysqlConn.loginAero());
			
			ProjPull(selCli);
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e);
		}
		
		SelProj menuBarTop = new SelProj();
		frame = new SelProj();
		
		if(delKey == 1)
		{
			next.setText("Delete");
		}
		else if(delKey == 0)
		{
			frame.setJMenuBar(menuBarTop.createMenuBar());
		}
		
		frame.setVisible(true);
	}

	public void valueChanged(ListSelectionEvent lse) 
	{
		int projIndex = projList.getSelectedIndex();
		tempProj = projects.get(projIndex).toString();
		if(tempProj != selProj)
		{
			selProj = tempProj;
		}
	}

	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == next)
		{
			if(selProj.length() > 0)
			{
				if(delKey == 1)
				{
					int confirmOne = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete " + selProj + "?");
					
					if(confirmOne == 0)
					{
						int confirmTwo = JOptionPane.showConfirmDialog(null, "This will delete ALL tasks associated with \n"
								+ "this project. Are you really sure?");
						
						if(confirmTwo == 0)
						{
							DeleteStuff(selProj, selClient);
							JOptionPane.showMessageDialog(null, selProj + " was successfully removed!");
						}
					}
				}
				else if(delKey == 2)
				{
					// TODO Link to TaskSelection
				}
				else
				{
					frame.dispose();
					AddTask.CreateGUI(selProj, selClient);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please select a project first.");
			}
		}
		else if(ae.getSource() == prev)
		{
			frame.dispose();
			SelClient.CreateGUI(delKey);
		}
		else if(ae.getSource() == refresh)
		{
			frame.dispose();
			SelProj.CreateGUI(selClient, delKey);
		}
	}
	
	public static void ProjPull(String selCl)
	{
		cliSel = true;
		projList = null;
		//projScrl = null;
		projects = null;
		projects = new ArrayList<String>();
		projectsTwo = null;
		String selectCli = selCl;
				
		
		try 
		{
			
			pst = conn.prepareStatement("SELECT client_id FROM client WHERE client_name=? AND user_id=?");
			pst.setString(1, selectCli);
			pst.setString(2, MainWindow.userID);
			//pst = conn.prepareStatement("SELECT client_id FROM client WHERE client_name=?");
			//pst.setString(1, selCl);
			
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
				
				projectsTwo = new Object[projects.size()];
				for(int counter = 0;counter<projectsTwo.length;counter++)
				{
					projectsTwo[counter] = projects.get(counter);
				}
				
			}
			
			projList = new JList(projectsTwo);
			/*projScrl = new JScrollPane(projList);
			projScrl.setSize(100, 100);
			projScrl.setLocation(120, 40);
			frame.add(projScrl);*/
			//projScrl.setVisible(true);
			//projList.updateUI();
			//projScrl.updateUI();
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e + " PROJ");
			//e.printStackTrace();
		}
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
	
	public static void DeleteStuff(String proj, String selClient)
	{
		try
		{
				TaskPull(proj, selClient);
				
				try
				{
					for(int count = 0; count < tasks.size(); count++)
					{
						
						String tempTask = tasks.get(count);
						pstThree = conn.prepareStatement("DELETE FROM assignment WHERE ass_name=? AND proj_id=?");
						pstThree.setString(1, tempTask);
						pstThree.setString(2, projID);
						pstThree.execute();
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e + " pst three");
				}
				
				pstTwo = conn.prepareStatement("DELETE FROM project WHERE proj_name=? AND client_id=(SELECT client_id FROM client WHERE client_name=?)");
				pstTwo.setString(1, proj);
				pstTwo.setString(2, selClient);
				pstTwo.execute();
			
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e + " delstuffs");
		}
	}

	public static void NullUp()
	{
		clID = "";
		
		
		projList = null;
		projScrl = null;
		projects = new ArrayList<String>();
		projectsTwo = null;
		selProj = "";
		tempProj = ""; 
		projID = "";
		 
		frame = null;
		cliSel = false;
		conn = null;
		pst = null; 
		pstTwo = null; 
		pstThree = null;
	}
}
