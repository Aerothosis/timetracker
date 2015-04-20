


import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;


public class SelClient extends JFrame implements ActionListener,ListSelectionListener
{
	JMenuBar menuBar = new JMenuBar();
	JMenu menuFile, menuEdit, menuSystem;
	JMenuItem menuNewClient, menuNewProj, menuSaveAs, menuOpen, menuExit; //menuFile, in order top to bottom
	JMenuItem menuPreview, menuAddTask; //menuEdit, in order top to bottom
	JMenuItem menuLogout; // menuSystem, in order top to bottom
	
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
		
		SelClient menuBarTop = new SelClient();
		frame = new SelClient();
		frame.setJMenuBar(menuBarTop.createMenuBar());
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) 
	{}
	
	public void valueChanged(ListSelectionEvent lse) 
	{}
}
