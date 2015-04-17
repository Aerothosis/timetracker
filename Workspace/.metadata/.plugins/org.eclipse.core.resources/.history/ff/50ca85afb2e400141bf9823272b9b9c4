


import javax.swing.*;
import java.awt.event.*;


public class AdminWindow extends JFrame implements ActionListener
{
	JButton newUser = new JButton("New User");
	
	static JFrame frame = null;
	
	AdminWindow()
	{
		this.setSize(400, 400);
		this.setLocation(300, 200);
		this.setTitle("Admin Window");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		newUser.setSize(100, 30);
		newUser.setLocation(10,10);
		newUser.addActionListener(this);
		this.add(newUser);
	}

	public static void main(String[] args) 
	{
		CreateGUI();
	}
	
	public static void CreateGUI()
	{
		frame = new AdminWindow();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == newUser)
		{
			NewUserWindow.CreateGUI();
		}
	}

}
