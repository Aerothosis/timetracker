

import javax.swing.*;

import java.text.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MainWindow extends JFrame implements ActionListener
{
	JMenuBar menuBar = new JMenuBar();
	JMenu menuFile, menuEdit, menuSystem;
	JMenuItem menuNew, menuSave, menuSaveAs, menuOpen, menuExit; //menuFile, in order top to bottom
	JMenuItem menuRmvTask, menuAddTask; //menuEdit, in order top to bottom
	JMenuItem menuLogout; // menuSystem, in order top to bottom
	
	JButton startTimer = new JButton("Start Timer");
	static JLabel startLbl = new JLabel("Start:");
	JButton endTimer = new JButton("End Timer");
	static JLabel endLbl = new JLabel("End:");
	static JLabel totalLbl = new JLabel("Total Time:");
	static JLabel runningTime = new JLabel("Running");
	
	static JComboBox boxOne;
	static JComboBox boxTwo;
	static JComboBox boxThree;
	
	static String[] cliLst;
	static ArrayList<String> clients = new ArrayList<String>();
	static String clID = "";
	
	static JList<String> projList = null;
	static String[] pjLst;
	static ArrayList<String> projects = new ArrayList<String>();
	static Object[] projectsTwo = null;
	static String projID = "";
	static ArrayList<String> tasks = new ArrayList<String>();
	static String[] taskLst;
	
	static boolean cliSel = false;
	static boolean projSel = false;
	static boolean taskSel = false;
	
	static JFrame frame = null;
	
	static String userID = "";
	static Connection conn = null;
	static PreparedStatement pst = null;
	static PreparedStatement pstTwo = null;
	static PreparedStatement pstThree = null;
	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	static Date startD;
	static Date stopD;
	static String startS;
	static String stopS;
	
	static long diff;
	static long diffSec;
	static long diffMin;
	static long diffHr;
	static int totalTime;
	static int diffInt;
	
	static Timer runTime;
	static int runTimeC;
	static String runTimeS;
	
	static Calendar cal;
	
	
	MainWindow(){
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Main");
		this.setLayout(null);
		
		boxOne.addItemListener(
			new ItemListener(){
				public void itemStateChanged(ItemEvent event){
					if(event.getStateChange() == ItemEvent.SELECTED){
						if(!cliSel){
							JOptionPane.showMessageDialog(null, cliLst[boxOne.getSelectedIndex()] + " IF");
							ProjPull(cliLst[boxOne.getSelectedIndex()], userID);
							//boxTwo.repaint();
							cliSel = true;
						}else{
							//JOptionPane.showMessageDialog(null, cliLst[boxOne.getSelectedIndex()] + " ELSE");
							cliSel = false;
						}
					}
				}
			}
		);
		boxOne.setSize(100, 30);
		boxOne.setLocation(10, 10);
		this.add(boxOne);
		
		boxTwo.addItemListener(
			new ItemListener(){
				public void itemStateChanged(ItemEvent event){
					if(event.getStateChange() == ItemEvent.SELECTED)
						if(!projSel){
							JOptionPane.showMessageDialog(null, pjLst[boxTwo.getSelectedIndex()] + " proj");
							TaskPull(cliLst[boxOne.getSelectedIndex()], pjLst[boxTwo.getSelectedIndex()], userID);
							projSel = true;
						}else{
							projSel = false;
						}
				}
			}
		);
		boxTwo.setSize(100, 30);
		boxTwo.setLocation(120, 10);
		this.add(boxTwo);
		
		boxThree.addItemListener(
			new ItemListener(){
				public void itemStateChanged(ItemEvent event){
					if(event.getStateChange() == ItemEvent.SELECTED)
						if(!taskSel){
							JOptionPane.showMessageDialog(null, taskLst[boxThree.getSelectedIndex()] + " task");
							diff = 0;
							diffSec = 0;
							diffMin = 0;
							diffHr = 0;
							totalTime = 0;
							taskSel = true;
							startLbl.setText("Start:");
							endLbl.setText("End:");
							TotalTimeSpent();
						}else{
							taskSel = false;
						}
				}
			}
		);
		boxThree.setSize(100, 30);
		boxThree.setLocation(230, 10);
		this.add(boxThree);
		
		startTimer.setSize(150, 30);
		startTimer.setLocation(10, 70);
		startTimer.addActionListener(this);
		this.add(startTimer);
		
		startLbl.setSize(150, 30);
		startLbl.setLocation(10, 110);
		this.add(startLbl);
		
		endTimer.setSize(150, 30);
		endTimer.setLocation(180, 70);
		endTimer.addActionListener(this);
		endTimer.setEnabled(false);
		this.add(endTimer);
		
		endLbl.setSize(150, 30);
		endLbl.setLocation(180, 110);
		this.add(endLbl);
		
		totalLbl.setSize(300, 30);
		totalLbl.setLocation(10, 150);
		this.add(totalLbl);
		
		runningTime.setSize(300, 100);
		runningTime.setLocation(10, 190);
		this.add(runningTime);
	}
	
	public JMenuBar createMenuBar(){
		menuFile = new JMenu("File");
		menuFile.getAccessibleContext().setAccessibleDescription("File Menu");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menuFile);
		
		menuNew = new JMenuItem("New Form");
		menuNew.addActionListener(this);
		//menuFile.add(menuNew);
		
		menuSave = new JMenuItem("Save Form");
		menuSave.addActionListener(this);
		//menuFile.add(menuSave);
		
		menuSaveAs = new JMenuItem("Save As");
		menuSaveAs.addActionListener(this);
		//menuFile.add(menuSaveAs);
		
		menuOpen = new JMenuItem("Save PDF");
		menuOpen.addActionListener(this);
		//menuFile.add(menuOpen);
		
		menuLogout = new JMenuItem("Log Out");
		menuLogout.addActionListener(this);
		menuFile.add(menuLogout);
		
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
		
		return menuBar;
	}
	
	public static void main(String[] args){
		CreateGUI("");
	}

	public static void CreateGUI(String user){
		userID = user;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connStr = MysqlConn.conn();
			conn = DriverManager.getConnection(connStr + MysqlConn.loginAero());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e + " connection");
		}
		
		ClientPull(user);
		boxOne = new JComboBox(cliLst);
		boxTwo = new JComboBox();
		ProjPull("All Clients", user);
		boxThree = new JComboBox();
		TaskPull("All Clients", "All Projects", user);
		
		MainWindow menuBarTop = new MainWindow();
		frame = new MainWindow();
		frame.setJMenuBar(menuBarTop.createMenuBar());
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == menuNew){
			
		}else if(ae.getSource() == menuLogout){
			frame.dispose();
			LoginWindow.CreateGUI();
		}else if(ae.getSource() == menuAddTask){
			//AddNewTask.CreateGUI();
			SelClient.CreateGUI(0);
		}else if(ae.getSource() == menuRmvTask){
			RemoveItem.CreateGUI();
		}else if(ae.getSource() == startTimer){
			startTimer.setEnabled(false);
			endTimer.setEnabled(true);
			TimerStart();
		}else if(ae.getSource() == endTimer){
			startTimer.setEnabled(true);
			endTimer.setEnabled(false);
			TimerEnd();
		}else if(ae.getSource() == menuExit){
			System.exit(0);
		}
	}
	
	public static void ClientPull(String usrID){
		clients = null;
		clients = new ArrayList<String>();
		clients.add(new String("All Clients"));
		try{
			pst = conn.prepareStatement("SELECT client_name FROM client WHERE user_id=?");
			pst.setString(1, usrID);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				String client = rs.getString(1);
				clients.add(new String(client));
			}
			cliLst = new String[clients.size()];
			cliLst = clients.toArray(cliLst);
			
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, e + " in client pull");
		}
	}
	
	public static void ProjPull(String selCl, String usrID)
	{
		cliSel = true;
		projList = null;
		//projScrl = null;
		pjLst = null;
		projects = null;
		projects = new ArrayList<String>();
		projects.add(new String("All Projects"));
		projectsTwo = null;
		String selectCli = selCl;
		
		boxTwo.removeAllItems();
		
				
		if(selCl.equalsIgnoreCase("All Clients")){
			try{
				pst = conn.prepareStatement("SELECT client_id FROM client WHERE user_id=?");
				pst.setString(1, usrID);
				
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					clID = rs.getString(1);
					pstTwo = conn.prepareStatement("SELECT proj_name FROM project WHERE client_id=?");
					pstTwo.setString(1, clID);
					ResultSet rsTwo = pstTwo.executeQuery();
					
					while(rsTwo.next()){
						String projIn = rsTwo.getString(1);
						projects.add(new String(projIn));
					}
				}
				
				pjLst = new String[projects.size()];
				pjLst = projects.toArray(pjLst);
			}catch (Exception e){
				JOptionPane.showMessageDialog(null, e + " PROJ - ALL clients");
			}
		}else{
			try{
				
				pst = conn.prepareStatement("SELECT client_id FROM client WHERE client_name=? AND user_id=?");
				pst.setString(1, selectCli);
				pst.setString(2, usrID);
				
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					clID = rs.getString(1);
					pstTwo = conn.prepareStatement("SELECT proj_name FROM project WHERE client_id=?");
					pstTwo.setString(1, clID);
					ResultSet rsTwo = pstTwo.executeQuery();
					
					while(rsTwo.next()){
						String projIn = rsTwo.getString(1);
						projects.add(new String(projIn));
					}
				}
				
				pjLst = new String[projects.size()];
				pjLst = projects.toArray(pjLst);
			}catch (Exception e){
				JOptionPane.showMessageDialog(null, e + " PROJ - w/ Client");
			}
		}
		for(int count = 0;count < pjLst.length;count++){
			boxTwo.addItem(pjLst[count]);
		}
		
	}
	
	public static void TaskPull(String selClient, String selPj, String usrID)
	{
		tasks = null;
		tasks = new ArrayList<String>();
		String selectProj = "";
		
		boxThree.removeAllItems();
		
		if(selClient.equalsIgnoreCase("All Clients") && selPj.equalsIgnoreCase("All Projects")){
			try{
				pstThree = conn.prepareStatement("SELECT client_id FROM client WHERE user_id=?");
				//pstThree.setString(1, selClient);
				pstThree.setString(1, usrID);
				
				ResultSet rsThree = pstThree.executeQuery();
				
				while(rsThree.next()){
					String slClID = rsThree.getString(1);
					pst = conn.prepareStatement("SELECT project_id FROM project WHERE client_id=?");
					//pst.setString(1, selPj);
					pst.setString(1, slClID);
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()){
						//projID = rs.getString(1);
						selectProj = rs.getString(1);
						pstTwo = conn.prepareStatement("SELECT ass_name FROM assignment WHERE proj_id=? AND user_id=?");
						pstTwo.setString(1, selectProj);
						pstTwo.setString(2, usrID);
						ResultSet rsTwo = pstTwo.executeQuery();
						
						while(rsTwo.next()){
							String taskIn = rsTwo.getString(1);
							tasks.add(new String(taskIn));
						}
					}
				}
				taskLst = new String[tasks.size()];
				taskLst = tasks.toArray(taskLst);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e + " Task");
			}
		}else if(selPj.equalsIgnoreCase("All Projects")){
			try{
				pstThree = conn.prepareStatement("SELECT client_id FROM client WHERE client_name=? AND user_id=?");
				pstThree.setString(1, selClient);
				pstThree.setString(2, usrID);
				
				ResultSet rsThree = pstThree.executeQuery();
				
				while(rsThree.next()){
					String slClID = rsThree.getString(1);
					pst = conn.prepareStatement("SELECT project_id FROM project WHERE client_id=?");
					//pst.setString(1, selPj);
					pst.setString(1, slClID);
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()){
						//projID = rs.getString(1);
						selectProj = rs.getString(1);
						pstTwo = conn.prepareStatement("SELECT ass_name FROM assignment WHERE proj_id=? AND user_id=?");
						pstTwo.setString(1, selectProj);
						pstTwo.setString(2, usrID);
						ResultSet rsTwo = pstTwo.executeQuery();
						
						while(rsTwo.next()){
							String taskIn = rsTwo.getString(1);
							tasks.add(new String(taskIn));
						}
					}
				}
				taskLst = new String[tasks.size()];
				taskLst = tasks.toArray(taskLst);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e + " Task");
			}
		}else{
			try{
				pst = conn.prepareStatement("SELECT project_id FROM project WHERE proj_name=? AND client_id=(SELECT client_id FROM client WHERE client_name=?)");
				pst.setString(1, selPj);
				pst.setString(2, selClient);
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					//projID = rs.getString(1);
					selectProj = rs.getString(1);
					pstTwo = conn.prepareStatement("SELECT ass_name FROM assignment WHERE proj_id=? AND user_id=?");
					pstTwo.setString(1, selectProj);
					pstTwo.setString(2, userID);
					ResultSet rsTwo = pstTwo.executeQuery();
					
					while(rsTwo.next()){
						String taskIn = rsTwo.getString(1);
						tasks.add(new String(taskIn));
					}
				}
				taskLst = new String[tasks.size()];
				taskLst = tasks.toArray(taskLst);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e + " Task");
			}
		}
		for(int count = 0;count < taskLst.length;count++){
			boxThree.addItem(taskLst[count]);
		}
	}
	
	public static void TimerStart(){
		runTimeC = 0;
		runTime = new Timer();
		runTime.schedule(new RunTimeThing(), 0, 1*1000);
		
		cal = null;
		cal = Calendar.getInstance();
		startS = cal.getTime().toString();
		startD = cal.getTime();
		JOptionPane.showMessageDialog(null, startS + " is the start");
		startLbl.setText("Start:   " + timeFormat.format(cal.getTime()));
		endLbl.setText("End: ");
	}
	
	public static void TimerEnd(){
		cal = null;
		cal = Calendar.getInstance();
		stopS = cal.getTime().toString();
		stopD = cal.getTime();
		diff = stopD.getTime() - startD.getTime();
		diffSec = diff / 1000 % 60;
		diffMin = diff / (60 * 1000) % 60;
		diffHr = diff / (60 * 60 * 1000);
		JOptionPane.showMessageDialog(null, stopS + " is the stop");
		JOptionPane.showMessageDialog(null, diffSec + " sec\n"
				+ diffMin + " min\n"
						+ diffHr + " hour");
		totalTime += diff;
		
		try{
			InsertTime();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "insert time error");
		}
		
		endLbl.setText("End:   " + timeFormat.format(cal.getTime()));
		TotalTimeSpent();
		runTime.cancel();
	}
	
	public static void InsertTime(){
		try {
			cal = null;
			cal = Calendar.getInstance();
			
			pst = conn.prepareStatement("SELECT assignment_id FROM assignment_time WHERE entry_date=? AND assignment_id=(SELECT ass_id FROM assignment WHERE ass_name=? AND user_id=?)");
			pst.setDate(1, new java.sql.Date(cal.getTimeInMillis()));
			pst.setString(2, taskLst[boxThree.getSelectedIndex()]);
			pst.setString(3, userID);
			
			ResultSet rs = pst.executeQuery();
			
			if(!rs.next()){
				pstTwo = conn.prepareStatement("INSERT INTO assignment_time (assignment_id,entry_date,days_time) VALUES(?, ?, ?)");
				pstTwo.setString(1, GetAssId() + "");
				pstTwo.setDate(2, new java.sql.Date(cal.getTimeInMillis()));
				//pstTwo.setString(3, diff + "");
				pstTwo.setInt(3, (int) diff);
				
				pstTwo.execute();
			}else{
				pstTwo = conn.prepareStatement("UPDATE assignment_time SET days_time= days_time + ? WHERE assignment_id=? AND entry_date=?");
				pstTwo.setInt(1, (int) diff);
				pstTwo.setString(2, GetAssId() + "");
				pstTwo.setDate(3, new java.sql.Date(cal.getTimeInMillis()));
				
				pstTwo.execute();
			}
			
			
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public static String GetAssId(){
		String assID = "";
		try{
			pst = conn.prepareStatement("SELECT ass_id FROM assignment WHERE ass_name=? AND user_id=?");
			pst.setString(1, taskLst[boxThree.getSelectedIndex()]);
			pst.setString(2, userID);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				assID = rs.getString(1);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "ass id pull");
		}
		
		return assID;
	}
	
	public static void TotalTimeSpent(){
		try{
			String selTaskId = GetAssId();
			
			pst = conn.prepareStatement("SELECT days_time FROM assignment_time WHERE assignment_id=?");
			pst.setString(1, selTaskId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				totalTime += rs.getInt(1);
			}
			
			int totalSec = totalTime / 1000 % 60;
			int totalMin = totalTime / (60 * 1000) % 60;
			int totalHr = totalTime / (60 * 60 * 1000) % 60;
			int totalDay = totalTime / (24 * 60 * 60 * 1000) % 24;
			
			totalLbl.setText("Total: " + totalDay + " Days, " + totalHr + " Hours, " + totalMin + " Minutes, " + totalSec + " Seconds");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

class RunTimeThing extends TimerTask{
	//int runTimer = MainWindow.runTimeC;
	
	@Override
	public void run() {
		MainWindow.runTimeC++;
		int totalTime = MainWindow.runTimeC;
		
		int totalSec = totalTime / 1000 % 60;
		int totalMin = totalTime / (60 * 1000) % 60;
		int totalHr = totalTime / (60 * 60 * 1000) % 60;
		int totalDay = totalTime / (24 * 60 * 60 * 1000) % 24;
		
		String runTimeS = "Run Time: "  + totalDay + " Days, " + totalHr + " Hours, " + totalMin + " Minutes, " + totalSec + " Seconds";
		MainWindow.runningTime.setText(runTimeS);
	}
}
	
