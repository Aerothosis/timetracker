import javax.swing.*;

import java.sql.*;
import java.awt.event.*;


public class AddNewTask extends JFrame implements ActionListener,ItemListener
{
	JMenuBar menuBar = new JMenuBar();
	JMenu menuFile, menuEdit, menuSystem;
	JMenuItem menuNewClient, menuNewProj, menuSaveAs, menuOpen, menuExit; //menuFile, in order top to bottom
	JMenuItem menuPreview, menuAddTask; //menuEdit, in order top to bottom
	JMenuItem menuLogout; // menuSystem, in order top to bottom
	
	//JCheckBox newClient = new JCheckBox("New Client");
	static JTextField newClientIn = new JTextField("Client");
	//JCheckBox newProj = new JCheckBox("New Project");
	static JTextField newProjIn = new JTextField("Proj");
	JLabel newTaskL = new JLabel("New Task");
	static JTextField newTaskIn = new JTextField();
	JButton addTask = new JButton("Add");
	
	
	
	static JFrame frame = null;
	boolean nc = false;
	boolean np = false;
	static Connection conn = null;
	static PreparedStatement pst = null;
	static PreparedStatement pstTwo = null;
	
	AddNewTask()
	{
		this.setSize(500, 300);
		this.setLocation(500, 400);
		this.setTitle("Add New Item");
		this.setLayout(null);
		
		newClientIn.setSize(100, 30);
		newClientIn.setLocation(10, 40);
		this.add(newClientIn);
		
		newProjIn.setSize(100, 30);
		newProjIn.setLocation(120, 40);
		this.add(newProjIn);
		
		newTaskL.setSize(100, 30);
		newTaskL.setLocation(230, 10);
		this.add(newTaskL);
		
		newTaskIn.setSize(100, 30);
		newTaskIn.setLocation(230, 40);
		this.add(newTaskIn);
		
		addTask.setSize(100, 30);
		addTask.setLocation(340, 40);
		addTask.addActionListener(this);
		this.add(addTask);
	}
	
	public JMenuBar createMenuBar()
	{
		menuFile = new JMenu("File");
		menuFile.getAccessibleContext().setAccessibleDescription("File Menu");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menuFile);
		
		menuNewClient = new JMenuItem("New Client");
		menuNewClient.addActionListener(this);
		menuFile.add(menuNewClient);
		
		menuNewProj = new JMenuItem("New Project");
		menuNewProj.addActionListener(this);
		menuFile.add(menuNewProj);
		
		menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(this);
		menuExit.setMnemonic(KeyEvent.VK_E);
		menuFile.add(menuExit);
		
		
		menuEdit = new JMenu("Edit");
		menuEdit.getAccessibleContext().setAccessibleDescription("Edit Menu");
		menuBar.add(menuEdit);
		
		
		menuSystem = new JMenu("System");
		menuSystem.getAccessibleContext().setAccessibleDescription("System Menu");
		menuBar.add(menuSystem);
		
		return menuBar;
	}
	
	public static void main(String[] args) 
	{
		CreateGUI();
	}

	public static void CreateGUI()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MysqlConn.conn() + MysqlConn.loginAero());
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e);
		}
		
		AddNewTask menuBarTop = new AddNewTask();
		frame = new AddNewTask();
		frame.setJMenuBar(menuBarTop.createMenuBar());
		frame.setVisible(true);
	}

	public void itemStateChanged(ItemEvent ie) 
	{
		/*if(ie.getSource() == newClient)
		{
			if(!nc)
			{
				newClientIn.setEditable(true);
				nc = true;
			}
			else
			{
				newClientIn.setEditable(false);
				nc = false;
			}
		}
		
		else if(ie.getSource() == newProj)
		{
			if(!np)
			{
				newProjIn.setEditable(true);
				np = true;
			}
			else
			{
				newProjIn.setEditable(false);
				np = false;
			}
		}*/
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
		else if(ae.getSource() == addTask)
		{
			InsertTask();
		}
	}
	
	public static void InsertTask()
	{
		
	}
}
