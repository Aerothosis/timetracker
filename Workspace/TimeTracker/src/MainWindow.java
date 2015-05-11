

import javax.swing.*;
import java.awt.event.*;


public class MainWindow extends JFrame implements ActionListener
{
	JMenuBar menuBar = new JMenuBar();
	JMenu menuFile, menuEdit, menuSystem;
	JMenuItem menuNew, menuSave, menuSaveAs, menuOpen, menuExit; //menuFile, in order top to bottom
	JMenuItem menuRmvTask, menuAddTask; //menuEdit, in order top to bottom
	JMenuItem menuLogout; // menuSystem, in order top to bottom
	
	static JFrame frame = null;
	
	static String userID = "";
	
	MainWindow()
	{
		this.setSize(600, 400);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Main");
		this.setLayout(null);
		
	}
	
	public JMenuBar createMenuBar()
	{
		menuFile = new JMenu("File");
		menuFile.getAccessibleContext().setAccessibleDescription("File Menu");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menuFile);
		
		menuNew = new JMenuItem("New Form");
		menuNew.addActionListener(this);
		menuFile.add(menuNew);
		
		menuSave = new JMenuItem("Save Form");
		menuSave.addActionListener(this);
		menuFile.add(menuSave);
		
		menuSaveAs = new JMenuItem("Save As");
		menuSaveAs.addActionListener(this);
		menuFile.add(menuSaveAs);
		
		menuOpen = new JMenuItem("Save PDF");
		menuOpen.addActionListener(this);
		menuFile.add(menuOpen);
		
		menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(this);
		menuExit.setMnemonic(KeyEvent.VK_E);
		menuFile.add(menuExit);
		
		
		menuEdit = new JMenu("Edit");
		menuEdit.getAccessibleContext().setAccessibleDescription("Edit Menu");
		menuBar.add(menuEdit);
		
		menuAddTask = new JMenuItem("Add Task");
		menuAddTask.addActionListener(this);
		menuEdit.add(menuAddTask);
		
		menuRmvTask = new JMenuItem("Remove Items");
		menuRmvTask.addActionListener(this);
		menuEdit.add(menuRmvTask);
		
		
		menuSystem = new JMenu("System");
		menuSystem.getAccessibleContext().setAccessibleDescription("System Menu");
		menuBar.add(menuSystem);
		
		menuLogout = new JMenuItem("Log Out");
		menuLogout.addActionListener(this);
		menuSystem.add(menuLogout);
		
		return menuBar;
	}
	
	public static void main(String[] args) 
	{
		CreateGUI("");
	}

	public static void CreateGUI(String user)
	{
		userID = user;
		
		MainWindow menuBarTop = new MainWindow();
		frame = new MainWindow();
		frame.setJMenuBar(menuBarTop.createMenuBar());
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == menuNew)
		{}
		else if(ae.getSource() == menuAddTask)
		{
			//AddNewTask.CreateGUI();
			SelClient.CreateGUI(false);
		}
		else if(ae.getSource() == menuRmvTask)
		{
			RemoveItem.CreateGUI();
		}
	}
}
