import javax.swing.*;
import java.awt.event.*;


public class AddNewProj extends JFrame implements ActionListener
{
	
	static JFrame frame = null;
	
	AddNewProj()
	{
		this.setSize(300, 200);
		this.setLocation(600, 300);
		this.setTitle("Add New Project");
	}
	
	public static void main(String[] args) 
	{
		CreateGUI();
	}
	
	public static void CreateGUI()
	{
		frame = new AddNewProj();
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) 
	{
		
	}

}
