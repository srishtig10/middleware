

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Connectionpooling
 */
@WebServlet("/reports/connection_pooling")
public class Connectionpooling extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		headerFooter base = new headerFooter(request.getSession());
		 PrintWriter out = response.getWriter();
		 out.println(base.header());
		 out.println("<TITLE>" +"Connection-Pooling" +
              "</TITLE></HEAD>");
		 
		 out.println("<p> We have implemented connection pooling in our project. In our login page i.e. 'TestLogin' where we accept the email id and password and establish connection with the database, there, using session it is saved and passed to all the rest of the pages.We used the following code for the same in TestLogin: Used connection.(datasource) to establish connection with our middleware database and session.setAttribute to set users emailid and password. </p>");
		 out.println(base.banner());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
