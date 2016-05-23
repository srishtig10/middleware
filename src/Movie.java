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
@WebServlet("/Movie")
public class Movie extends HttpServlet {
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
			print("Inception", response, request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void print(String title, HttpServletResponse response, HttpServletRequest request) throws SQLException, IOException
	{
		connection = (Connection) dataSource.getConnection();
	    headerFooter base = new headerFooter(request.getSession());
		int movie_id = Integer.parseInt(request.getParameter("MovieID"));
		String query = "Select * from movies where id like '" + movie_id + "'";
		PreparedStatement ps_movies = (PreparedStatement) connection.prepareStatement(query);
		ResultSet movies = ps_movies.executeQuery();
		PrintWriter out = response.getWriter();
		
		out.println(base.header());
		out.println("<HEAD><TITLE>Movie Info</TITLE></HEAD>");
		out.println(base.banner());
		out.println("<a href=\"javascript:history.go(-1)\">Go back to previous page</a>");
		String message = request.getParameter("message");
		if (movies.next())
		{
			do
			{
				String star_query = "Select distinct(a.first_name), a.last_name, a.id from stars a "
						+ "where a.id in (select distinct(b.star_id) from stars_in_movies b "
						+ "where b.movie_id in (select distinct(c.id)  from movies c where c.title = '" + movies.getString("title") +"'));";
				PreparedStatement ps_stars = (PreparedStatement) connection.prepareStatement(star_query);
				ResultSet stars = ps_stars.executeQuery();
				
				String genre_query = "Select distinct(a.name) from genres a "
						+ "where a.id in (select distinct(b.genre_id) from genres_in_movies b where b.movie_id in "
						+ "(select distinct(c.id)  from movies c where c.title = '" + movies.getString("title") +"'));";
				PreparedStatement ps_genres = (PreparedStatement) connection.prepareStatement(genre_query);
				ResultSet genres = ps_genres.executeQuery();
				
				
				out.println("<tr><td><br><br>" + 
						"<div>" + 
						"<table  id=\"movie_search\" style='margin-left:20%;margin-right:20%;'><tr><td width=\"20%;\"><div id=\"mov_list\">" + 
						"<img  style=\"position:absolute;z-index:1;margin-top:30px;margin-left:75px;\" src=\"" + movies.getString("banner_url") + "\"  alt=\"" + movies.getString("title") + " DVD Cover\" height='188' width='120'>" + 
						"<img style=\"z-index:2;\" src=\"http://gateway.hopto.org:9000/fabflix/images/short-case.png\" height='250' width='255'></div></td>" + 
						"<td width=\"40%;\"><div id=\"mov_det\">" + 
						"<span style=\"font-weight: bold;\">Movie : " + movies.getString("title") + "</span><br>" +
						"<br><span>Year: " + movies.getString("year") + "</span><br>" + 
						"<br><span>Director: " + movies.getString("director") + "</span><br>"); 
				out.println("<br><span style=\"font-style: italic;\">Genre: ");
				String genre_list = "";
				while (genres.next())
				{
					genre_list += (genres.getString("name") + ", ");
				}
				genre_list = genre_list.substring(0, genre_list.length()-2);
				out.println(genre_list + "</span><br><br>");
	         out.println("<span>Price: [$15.99]</span><br>"
						+ "<br><div style=\"float:left;width:10%;\"><span style=\"font-style: italic;\">Actors: </span></div><div style=\"float:right;width:90%;\"><span>");
				String star_name = "Dummy Name";
				while (stars.next())
				{
					if (!star_name.equals(stars.getString("first_name") + stars.getString("last_name")))
						out.println("<a class=\"ag_links\" href=\"Star?StarID=" + stars.getString("id") + "\">"
							+ stars.getString("first_name") + " " + stars.getString("last_name") + "</a>");
					star_name = stars.getString("first_name") + stars.getString("last_name");
				} 
	         out.println("</span></div></div></td></tr>" + 
						"<tr><td><center><div class='cart_movie'><img src=\"http://goo.gl/xuA1xS?gdriveurl\" height=\"20\" width=\"20\">" + 
						"<a class=\"links\" href='Cart?MovieID=" + movies.getString("id") + "&qty=1&req=Add'\">&nbspAdd to My Cart</a></div></center></td>" + 
						"<td><center><div class='cart_movie' style='width:40%'><img src=\"http://goo.gl/rXhBkP?gdriveurl\" height=\"22\" width=\"22\">" + 
						"<a class=\"links\" href='" + movies.getString("trailer_url") + "'\">&nbspWatch Trailer</a></div></center></td>" + 
						"</tr></table>" + 
						"</div>" + 
						"<br></td></tr>");
			} while (movies.next());
		}
		
		out.println("<br><br><br>" + base.footer());
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
