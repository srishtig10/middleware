

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class Menu
 */
@WebServlet("/Menu")
public class Menu extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
	private Connection connection;
	private static String Email = null;
	private static String Pass = null;
	private static String Page = null;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void init() throws ServletException
	{
		try {
			// Get DataSource
			Context initContext  = new InitialContext();
			dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/middleware");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String message = request.getParameter("message");
		
			  
		
		
		out.println("<html>"+"<head>"+"<meta charset="+"ISO-8859-1"+">"+"<title> FABFLIX - EMPLOYEE MENU </title>"+"<link rel="+"stylesheet"+" type="+"text/css"+" href="+"design.css"+">"+
				  "<body id="+"stylemain"+" link ="+"white"+">"+"<div align="+"center"+">"+"<table width="+"998"+" border="+"0"+" cellspacing="+"0"+" cellpadding="+"0"+">"+"<tr>"+"<td height="+"10"+">"+"&nbsp;</td>"+"</tr>");
		out.println("<tr>"+"<td height="+"10"+">&nbsp;</td>"+"</tr>"+"<tr><td><div align="+"center"+"><h1><strong><center><font color="+"white"+">FABFLIX - EXPLORE THE WORLD OF MOVIES</center></strong></h1></font>"+"&nbsp;<br/><br><br><br>");
		
		out.println("&nbsp;<center><div class="+"menutext"+"><br><a href="+"AddStar"+">Add a star</a>&nbsp;<br><a href="+"Metadata"+">Get Metadata of database</a>&nbsp;<br><a href="+"AddMovie"+">Add a movie</a></center>");
		
		out.println("<div class="+"tinyspacer"+">&nbsp;<br />"+"</div>"+"<div align="+"center"+"><a href="+"Menu"+">Home</a> | <a href="+"about.html"+">About Us</a> | <a href="+"Privacy.jsp"+">Privacy Policy</a>"+
          		"</table>"+"<div class="+"tinyspacer"+">&nbsp;<br />"+"<center><font size= "+"2px"+">Copyright &copy; 2016 by Aishwarya, Amita, Srishti. All rights reserved.</font></div>"+" </div>"+
          		"&nbsp;<br /></center></td>"+"</tr>"+"</table></body></html>");
		
		try
		   {
			   connection = (Connection) dataSource.getConnection();
		    	Statement statement = connection.createStatement();
		
		
		   }
	
		 catch (SQLException ex) {
             while (ex != null) {
                   System.out.println ("SQL Exception:  " + ex.getMessage ());
                   ex = ex.getNextException ();
               }  // end while
           }  // end catch SQLException

		
		
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub  
		doPost(request,response);
	} 

}
