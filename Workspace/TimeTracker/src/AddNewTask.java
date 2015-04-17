import javax.swing.*;

import java.awt.event.*;


public class AddNewTask extends JFrame implements ActionListener,ItemListener
{
	JMenuBar menuBar = new JMenuBar();
	JMenu menuFile, menuEdit, menuSystem;
	JMenuItem menuNewClient, menuNewProj, menuSaveAs, menuOpen, menuExit; //menuFile, in order top to bottom
	JMenuItem menuPreview, menuAddTask; //menuEdit, in order top to bottom
	JMenuItem menuLogout; // menuSystem, in order top to bottom
	
	JCheckBox newClient = new JCheckBox("New Client");
	JTextField newClientIn = new JTextField("");
	JCheckBox newProj = new JCheckBox("New Project");
	JTextField newProjIn = new JTextField("");
	
	
	static JFrame frame = null;
	boolean nc = false;
	boolean np = false;
	
	AddNewTask()
	{
		this.setSize(500, 300);
		this.setLocation(500, 400);
		this.setTitle("Add New Item");
		this.setLayout(null);
		
		
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
		AddNewTask menuBarTop = new AddNewTask();
		frame = new AddNewTask();
		frame.setJMenuBar(menuBarTop.createMenuBar());
		frame.setVisible(true);
	}

	public void itemStateChanged(ItemEvent ie) 
	{
		if(ie.getSource() == newClient)
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
		}
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
	}
}
