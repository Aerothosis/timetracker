


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
	
	JButton refresh = new JButton("Refresh Lists");
	JButton prev = new JButton("< Clients");
	JButton next = new JButton("Next >");
	
	static JFrame frame = null;
	
	static Connection conn = null;
	static PreparedStatement pst = null;
	static PreparedStatement pstTwo = null;
	static PreparedStatement pstThree = null;
	
	static boolean cliSel = false;
	static String selClient = "";
	
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
		
		/*menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(this);
		menuExit.setMnemonic(KeyEvent.VK_E);
		menuFile.add(menuExit);
		
		
		menuEdit = new JMenu("Edit");
		menuEdit.getAccessibleContext().setAccessibleDescription("Edit Menu");
		menuBar.add(menuEdit);
		
		
		menuSystem = new JMenu("System");
		menuSystem.getAccessibleContext().setAccessibleDescription("System Menu");
		menuBar.add(menuSystem);*/
		
		return menuBar;
	}
	
	public static void main(String[] args) 
	{
		CreateGUI("First Client");
	}
	
	public static void CreateGUI(String selCli)
	{
		NullUp();
		selClient = selCli;
		
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
		frame.setJMenuBar(menuBarTop.createMenuBar());
		frame.setVisible(true);
		
		//JOptionPane.showMessageDialog(null, selCli);
	}

	public void valueChanged(ListSelectionEvent lse) 
	{
		int projIndex = projList.getSelectedIndex();
		tempProj = projects.get(projIndex).toString();
		if(tempProj != selProj)
		{
			selProj = tempProj;
			//JOptionPane.showMessageDialog(null, "Selected " + selProj + " valueIsAdjusting thing.");
		}
	}

	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == next)
		{
			if(selProj.length() > 0)
			{
				frame.dispose();
				AddTask.CreateGUI(selProj, selClient);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please select a project first.");
			}
		}
		else if(ae.getSource() == prev)
		{
			frame.dispose();
			SelClient.CreateGUI();
		}
		else if(ae.getSource() == refresh)
		{
			frame.dispose();
			SelProj.CreateGUI(selClient);
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
