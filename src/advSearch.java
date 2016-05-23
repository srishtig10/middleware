import java.sql.*;
import java.util.Stack;
import java.io.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Movie
 */
@WebServlet("/advSearch")
public class advSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
    private Connection connection;
       
    /**
     * @param connection 
     * @see HttpServlet#HttpServlet()
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		// TODO Auto-generated method stub;
		try {
			print(response, request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void print(HttpServletResponse response, HttpServletRequest request) throws SQLException, IOException
	{
		connection = (Connection) dataSource.getConnection();
	//	int movie_id = Integer.parseInt(request.getParameter("MovieID"));
		//String query = "Select * from movies where id like '" + movie_id + "'";
		//PreparedStatement ps_movies = (PreparedStatement) connection.prepareStatement(query);
		//ResultSet movies = ps_movies.executeQuery();
		PrintWriter out = response.getWriter();
	    headerFooter base = new headerFooter(request.getSession());
		 out.println(base.header());
			out.println("<HEAD><TITLE>Login Page</TITLE></HEAD>");
			out.println(base.banner());
		
		String s = "<div align=\"center\">"+
        "<fieldset style=\"width: 500px\" class=\"field\">"+
          "<legend>Advanced Search Options:</legend>"+
          "<form action=\"advSearchRes\" method=\"get\" name=\"advanced_search_form\" id=\"advanced_search\">"+
            "<table width=\"400\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"font-size: small\">"+
              "<tr>"+
                "<td width=\"15\">&nbsp;</td>"+
                "<td>&nbsp;</td>"+
                "<td>&nbsp;</td>"+
              "</tr>"+
              "<tr>"+
                "<td width=\"15\">&nbsp;</td><input name=\"by\" type=\"hidden\" value=\"advsearch\" /></td>"+
                "<td width=\"145\"><div align=\"left\">Title:</div></td>"+
                "<td><input name=\"title\" type=\"text\" size=\"42\" maxlength=\"100\" /></td>"+
              "</tr>"+
              "<tr>"+
                "<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
                "<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
                "<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
              "</tr>"+
              "<tr>"+
                "<td width=\"15\">&nbsp;</td>"+
                "<td width=\"145\"><div align=\"left\">Year:</div></td>"+
                "<td><input name=\"year\" type=\"text\" size=\"42\" maxlength=\"4\" /></td>"+
              "</tr>"+
              "<tr>"+
                "<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
                "<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
                "<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
              "</tr>"+
              "<tr>"+
                "<td width=\"15\">&nbsp;</td>"+
                "<td width=\"145\"><div align=\"left\">Director:</div></td>"+
                "<td><input name=\"director\" type=\"text\" size=\"42\" maxlength=\"64\" /></td>"+
              "</tr>"+
              "<tr>"+
                "<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
                "<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
                "<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
              "</tr>"+
              "<tr>"+
                "<td width=\"15\">&nbsp;</td>"+
                "<td width=\"145\"><div align=\"left\">Star's First Name:</div></td>"+
                "<td><input name=\"s_first\" type=\"text\" size=\"42\" maxlength=\"32\" /></td>"+
              "</tr>"+
              "<tr>"+
                "<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
                "<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
                "<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
              "</tr>"+
              "<tr>"+
                "<td width=\"15\">&nbsp;</td>"+
                "<td width=\"145\"><div align=\"left\">Star's Last Name:</div></td>"+
                "<td><input name=\"s_last\" type=\"text\" size=\"42\" maxlength=\"32\" /></td>"+
              "</tr>"+
              "<tr>"+
                "<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>";
              
	String s2 = "<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
            "<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
          "</tr>"+
          "<tr>"+
            "<td width=\"15\">&nbsp;</td>"+
            "<td width=\"145\"><div align=\"left\">Fuzzy Search:</div></td>"+
            "<td><div class=\"menutext\" style=\"vertical-align:bottom;color:#999\">"+
                "<input type=\"checkbox\" name=\"fuzzy\" value=\"1\" />"+
                "&nbsp;&nbsp;&nbsp;*will match spelling errors to some extent</div></td>"+
          "</tr>"+
          "<tr>"+
            "<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
            "<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
            "<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
          "</tr>"+
          "<tr>"+
            "<td width=\"15\">&nbsp;</td>"+
            "<td width=\"145\"><div align=\"left\">Match Substring:</div></td>"+
            "<td><div class=\"menutext\" style=\"vertical-align:bottom;color:#999\">"+
                "<input type=\"checkbox\" name=\"substr\" value=\"1\" />"+
                "&nbsp;&nbsp;&nbsp;*will match portions of a larger string</div></td>"+
          "</tr>"+
          "<tr>"+
            "<td width=\"15\">&nbsp;</td>"+
            "<td>&nbsp;</td>"+
            "<td>&nbsp;</td>"+
          "</tr>"+
        "</table>"+
        "<input type=\"hidden\" name=\"order\" value=\"t_asc\" />"+
        "<input type=\"hidden\" name=\"page_id\" value=\"1\" />"+
        "<input type=\"hidden\" name=\"ipp\" value=\"5\" />"+
        "<input value=\"Search\" name=\"btn_param\" type=\"submit\" />"+
        "<input value=\"Reset Form\" name=\"reset\" type=\"reset\" />"+
      "</form>"+
      "&nbsp;"+
       "</fieldset>"+
        "&nbsp;<br />"+
        "</div>";	          
out.print(s);
out.println(s2);
out.println(base.footer());
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
