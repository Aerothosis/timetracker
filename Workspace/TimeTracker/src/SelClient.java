
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


public class SelClient extends JFrame implements ActionListener,ListSelectionListener
{
	JMenuBar menuBar = new JMenuBar();
	JMenu menuFile, menuEdit, menuSystem;
	JMenuItem menuNewClient, menuNewProj, menuSaveAs, menuOpen, menuExit; //menuFile, in order top to bottom
	JMenuItem menuPreview, menuAddTask; //menuEdit, in order top to bottom
	JMenuItem menuLogout; // menuSystem, in order top to bottom
	
	JLabel clientL = new JLabel("Clients");
	static JList<String> clientLst = null;//new JList<String>();
	static JScrollPane clientScrl = null;
	static ArrayList<String> clients = new ArrayList<String>();
	static Object[] clientTwo = null;
	static String selClient = "";
	static String tempClient = "";
	static String clID = "";
	
	JButton next = new JButton("Next >");
	JButton refresh = new JButton("Refresh List");
	
	static JFrame frame = null;
	
	static Connection conn = null;
	static PreparedStatement pst = null;
	static PreparedStatement pstTwo = null;
	static PreparedStatement pstThree = null;
	
	SelClient()
	{
		this.setSize(400, 300);
		this.setLocation(400, 300);
		this.setTitle("Select Client");
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
		next.setMnemonic(KeyEvent.VK_N);
		//menuFile.setMnemonic(KeyEvent.VK_F);
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
		
		SelClient menuBarTop = new SelClient();
		frame = new SelClient();
		frame.setJMenuBar(menuBarTop.createMenuBar());
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == menuNewClient)
		{
			AddNewClass.CreateGUI();
			
		}
		else if(ae.getSource() == menuNewProj)
		{
			AddNewProj.CreateGUI();
		}
		else if(ae.getSource() == next)
		{
			if(selClient.length() > 0)
			{
				frame.dispose();
				SelProj.CreateGUI(selClient);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please select a client first.");
			}
		}
		else if(ae.getSource() == refresh)
		{
			frame.dispose();
			SelClient.CreateGUI();
		}
	}
	
	public void valueChanged(ListSelectionEvent lse) 
	{
		int index = clientLst.getSelectedIndex();
		tempClient = clients.get(index).toString();
		
		if(tempClient != selClient)
		{
			selClient = tempClient;
			//ProjPull(selClient);
			//projScrl.updateUI();
			//projList.updateUI();
			//projList.repaint();
			//JOptionPane.showMessageDialog(null, "Selected " + selClient);
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
