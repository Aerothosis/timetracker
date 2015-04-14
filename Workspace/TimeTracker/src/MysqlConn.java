/*
This is the database connection file. Edit this file to change database connection ONLY.
 */

public class MysqlConn 
{
	public static String conn()
	{
		String connection = "jdbc:mysql://localhost/mfurtado_timetracker?";
		return connection;
	}
	
	public static String loginAero()
	{
		String login = "user=aerothosis&password=halorvb1";
		return login;
	}
	
	public static String loginUsr()
	{
		String login = "user=userinput&password=HALOrvb12#$!@34";
		return login;
	}
}
