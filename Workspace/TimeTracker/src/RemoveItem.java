import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;


public class RemoveItem extends JFrame implements ActionListener
{
	
	JButton rmvClient = new JButton("Remove Client");
	JButton rmvProj = new JButton("Remove Project");
	JButton rmvTask = new JButton("Remove Task");
	
	static JFrame frame = null;
	
	RemoveItem()
	{
		this.setSize(400, 300);
		this.setLocation(400, 300);
		this.setTitle("Remove Items");
		this.setLayout(null);
		
		rmvClient.setSize(150, 30);
		rmvClient.setLocation(10, 10);
		rmvClient.addActionListener(this);
		this.add(rmvClient);
		
		rmvProj.setSize(150, 30);
		rmvProj.setLocation(10, 50);
		rmvProj.addActionListener(this);
		this.add(rmvProj);
		
		rmvTask.setSize(150, 30);
		rmvTask.setLocation(10, 90);
		rmvTask.addActionListener(this);
		this.add(rmvTask);
	}

	public static void main(String[] args) 
	{
		CreateGUI();
	}

	public static void CreateGUI()
	{
		frame = new RemoveItem();
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == rmvClient)
		{
			RemoveClient.CreateGUI();
		}
		else if(ae.getSource() == rmvProj)
		{
			SelClient.CreateGUI(1);
		}
		else if(ae.getSource() == rmvTask)
		{}
	}
	
}
