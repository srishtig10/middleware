
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
 * Servlet implementation class Metadata
 */
@WebServlet("/Metadata")
public class Metadata extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	private Connection connection;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public void init() throws ServletException {
		try {
			// Get DataSource
			Context initContext = new InitialContext();
			dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/middleware");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().print("Test");
		PrintWriter out = response.getWriter();
		String message = request.getParameter("message");
		HttpSession session = request.getSession();

		// if (message != null)
		synchronized (session) {
			session.setAttribute("Email", null);

		}

		out.println("<html>" + "<head>" + "<meta charset=" + "ISO-8859-1" + ">" + "<title> FABFLIX - ADD STAR </title>"
				+ "<link rel=" + "stylesheet" + " type=" + "text/css" + " href=" + "design.css" + ">" + "<body id="
				+ "stylemain" + " link =" + "white" + ">" + "<div align=" + "center" + ">" + "<table width=" + "998"
				+ " border=" + "0" + " cellspacing=" + "0" + " cellpadding=" + "0" + ">" + "<tr>" + "<td height=" + "10"
				+ ">" + "&nbsp;</td>" + "</tr>");
		out.println("<tr>" + "<td height=" + "10" + ">&nbsp;</td>" + "</tr>" + "<tr><td><div align=" + "center"
				+ "><h1><strong><center><font color=" + "white"
				+ ">FABTUBE - WORLD OF MOVIES</center></strong></h1></font>" + "&nbsp;<br/><br><br>");

		try {

			connection = (Connection) dataSource.getConnection();
			Statement select = connection.createStatement();

			ResultSet rs = select.executeQuery("Show Tables");

			while (rs.next()) {

				out.println("<br><br><center>" + "Table " + rs.getString(1) + " ");
				Statement select1 = connection.createStatement();
				ResultSet result = (ResultSet) select1.executeQuery("Select * from " + rs.getString(1) + " ");
				ResultSetMetaData metadata = result.getMetaData();

				int h = metadata.getColumnCount();

				out.println("<br>There are " + h + " columns");

				for (int j = 1; j <= h; j++) {
					out.println("<br>" + "Field " + metadata.getColumnName(j) + " has type "
							+ metadata.getColumnTypeName(j) + "");

				}

			}
			out.println("</body></html>");

		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
