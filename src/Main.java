import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DataSource dataSource;
    Connection connection;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init(ServletConfig config) throws ServletException 
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        headerFooter base = new headerFooter(request.getSession());
		 PrintWriter out = response.getWriter();
		 out.println(base.header());
		 out.println("<TITLE>" +"Main Page" +
                "</TITLE></HEAD>");
		 out.println(base.banner());
		 out.println("<div>" + 
		 		
		 		"<div id=\"pop_title\"><span><h2>Trending</h2></span></div>" + 
		 		"<table border id=\"popular\">" + 
		 		"<tr align=\"center\"><td style=\"border:none;\">" + 
		 		"<table  id=\"movie\"><tr><td><div id=\"mov_list\">" + 
		 		"<img style=\"position:absolute;z-index:1;\"src=\"http://gateway.hopto.org:9000/fabflix/images/cache/4e45ae9aeeb850893d6898fe5ceeeb69.png\" height=\"270\" width=\"255\">" + 
		 		"<img style=\"z-index:2;\"src=\"http://gateway.hopto.org:9000/fabflix/images/short-case.png\" height=\"250\" width=\"255\"></div>" + 
		 		"<div align=\"center\" id=\"mov_det\">" + 
		 		"<span style=\"font-weight: bold;\">Terminator 2: Judgment Day (1991)</span><br>" + 
		 		"<span style=\"font-style: italic;\">Director: James Cameron</span><br>" + 
		 		"<span ><a href=\"Movie?MovieID=755010\">More Info<a>: [$15.99]</span><br><br>" + 
		 		"</div></td></tr>" + 
		 		"<tr><td class=\"cart\"><img style=\"float:left;padding-left:30%;\"src=\"http://goo.gl/xuA1xS?gdriveurl\" height=\"24\" width=\"24\">" + 
		 		"<a class=\"links\" href='Cart?MovieID=755010&qty=1&req=Add'>&nbspAdd to My Cart</a></td></tr></table></td>" + 
		 		"<td style=\"border:none;\"><td style=\"border:none;\"><table  id=\"movie\"><tr><td><div id=\"mov_list\">" + 
		 		"<img style=\"position:absolute;z-index:1;\"src=\"http://ecx.images-amazon.com/images/I/51c%2B-1qChTL._SX200_QL80_.jpg\" height=\"250\" width=\"200\">" + 
		 		"<img style=\"z-index:2;\"src=\"http://gateway.hopto.org:9000/fabflix/images/short-case.png\" height=\"250\" width=\"200\"></div>" + 
		 		"<div align=\"center\" id=\"mov_det\">" + 
		 		"<span style=\"font-weight: bold;\">Insomnia (2002)</span><br>" + 
		 		"<span style=\"font-style: italic;\">Director: Christopher Nolan</span><br>" + 
		 		"<span><a href=\"Movie?MovieID=926\">More Info<a> [$15.99]</span><br><br>" + 
		 		"</div></td></tr>" + 
		 		"<tr><td class=\"cart\"><img style=\"float:left;padding-left:30%;\"src=\"http://goo.gl/xuA1xS?gdriveurl\" height=\"24\" width=\"24\">" + 
		 		"<a class=\"links\" href='Cart?MovieID=926&qty=1&req=Add'>&nbspAdd to My Cart</a></td></tr></table></td>" + 
		 		"<td style=\"border:none;\"><td style=\"border:none;\"><table  id=\"movie\"><tr><td><div id=\"mov_list\">" + 
		 		"<img style=\"position:absolute;z-index:1;\"src=\"http://i.jeded.com/i/catch-me-if-you-can.12390.jpg\" height=\"250\" width=\"150\">" + 
		 		"<img style=\"z-index:2;\"src=\"http://gateway.hopto.org:9000/fabflix/images/short-case.png\" height=\"250\" width=\"150\"></div>" + 
		 		"<div align=\"center\" id=\"mov_det\">" + 
		 		"<span style=\"font-weight: bold;\">Catch Me If You Can (2009)</span><br>" + 
		 		"<span style=\"font-style: italic;\">Director: Steven Spielberg</span><br>" + 
		 		"<span><a href=\"Movie?MovieID=658008\">More Info<a> [$15.99]</span><br><br>" + 
		 		"</div></td></tr>" + 
		 		"<tr><td class=\"cart\"><img style=\"float:left;padding-left:30%;\"src=\"http://goo.gl/xuA1xS?gdriveurl\" height=\"24\" width=\"24\">" + 
		 		"<a class=\"links\" href='Cart?MovieID=658008&qty=1&req=Add'>&nbspAdd to My Cart</a></td></tr></table></td></tr>" + 
		 		"</table>" + 
		 		"</div>");
	   try
	   {
		   connection = (Connection) dataSource.getConnection();
	    	Statement statement = connection.createStatement();
		  
	    	String genre_query="Select distinct name,id from middleware.genres join genres_in_movies on genres_in_movies.genre_id = middleware.genres.id order by name asc";
	 	   ResultSet rs = statement.executeQuery(genre_query);
	 	   out.println("<br><br><br><br><br><br>\r\n" + 
	 	   		"<hr id=\"line\">" + 
	 	   		"<div>" + 
	 	   		"<h2 style=\"margin-left:2%;\">Guided Search:</h4>"
	 	   		+ "<div id=\"mov_srch\">"
	 	   		+ "<p style=\"margin-left:3%;margin-top:5px;font-size:18px;\">Browse Movie by Genre: </p>");
	 	   while(rs.next()){
	 		   
	 		   String genre=rs.getString("name");
	 		   int id = rs.getInt("id");
	 		   out.println("<a class=\"gnr_srch\" href=MovieList?by=genre&arg="+id+">"+genre+"</a>");
	 	   }
	 	  out.println("&nbsp;</div>" + 
	 	  		"<div id=\"genre_srch\">"
	 	  		+ "<p style=\"margin-left:3%;margin-top:5px;font-size:18px;\">Browse Movie by Title: </p>");
	  
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=A>A</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=B>B</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=C>C</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=D>D</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=E>E</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=F>F</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=G>G</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=H>H</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=I>I</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=J>J</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=K>K</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=L>L</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=N>N</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=M>M</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=O>O</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=P>P</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=Q>Q</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=R>R</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=S>S</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=T>T</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=U>U</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=V>V</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=W>W</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=X>X</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=Y>Y</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=Z>Z</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=1>1</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=2>2</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=3>3</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=4>4</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=5>5</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=6>6</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=7>7</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=8>8</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=9>9</a>");
	   out.println("<a class=\"title_srch\" href=MovieList?by=title&arg=0>0</a>");
	  
	   
	   
	   out.println("</div></div>");
	   out.println("<BR><br><br><br>");
	   }
		  catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
	   out.println(base.footer());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
