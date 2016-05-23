import java.sql.*;
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
 * Servlet implementation class MovieList
 */
@WebServlet("/MovieList")
public class MovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
	private Connection connection;
	String sort_by = null;
	int ipp = 0;

	/**
	 * @param connection 
	 * @see HttpServlet#HttpServlet()
	 */ 
	public void init() throws ServletException
	{
		try {
			// Get DataSource
			Context initContext  = new InitialContext();
			dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/moviedb");
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
		PrintWriter out = response.getWriter();
		headerFooter base = new headerFooter(request.getSession());
		sort_by = request.getParameter("by");
		String query=null;
		String query_count="0";
		String spage_id= request.getParameter("page_id");
		String sipp= request.getParameter("ipp");
		if(spage_id == null)
			spage_id ="1";
		String orderby= request.getParameter("order");
		String order_accord="title";
		String ordered_state ="t_asc";
		if(orderby==null)
			orderby ="ASC";
		else{
			ordered_state=orderby;
			switch(orderby){
			case "t_asc":
				orderby ="ASC";
				break;
			case "t_desc":
				orderby ="DESC";
				break;
			case "d_asc":
				orderby ="ASC";
				order_accord="year";
				break;
			case "d_desc":
				orderby ="DESC";
				order_accord="year";
				break;
			}
		}
		if(sipp==null)
			sipp="5";
		int page_id=Integer.parseInt(spage_id);
		ipp=Integer.parseInt(sipp);

		switch(sort_by){

		case "genre" :
			String genre_id=request.getParameter("arg");
			query="select * from movies "+
					"join  genres_in_movies on movies.id=genres_in_movies.movie_id "+
					"where genres_in_movies.genre_id="+genre_id+" order by title "+orderby+" LIMIT "+ipp+" OFFSET "+ipp*page_id; 
			query_count="select count(*) from movies "+
					"join  genres_in_movies on movies.id=genres_in_movies.movie_id "+
					"where genres_in_movies.genre_id="+genre_id;  

			break;
		case "title" :
			String title=request.getParameter("arg");
			query="Select * from movies where title like '"+title+"%'"+" order by "+order_accord+" "+orderby+" LIMIT "+ipp+" OFFSET "+ipp*(page_id-1) ;
			query_count="Select count(*) from movies where title like '"+title+"%'";

			break;

		case "search" :
			String search_term=request.getParameter("arg");
			query="Select * from movies where title like '%"+search_term.replace("'", "''")+"%'"+" order by "+order_accord+" "+orderby+" LIMIT "+ipp+" OFFSET "+ipp*(page_id-1);
			query_count="Select count(*) from movies where title like '% "+search_term.replace("'", "''")+"%'";
			//out.println(query);
			break;

		case "advsearch" :
			//String search_term=request.getParameter("arg");
			query="select * from movies join stars_in_movies on stars_in_movies.movie_id=movies.id join stars on stars.id=stars_in_movies.star_id ";
			//query_count="Select count(*) from movies where title like '% "+search_term.replace("'", "''")+"%'";
			//out.println(query);
			title = request.getParameter("title");
			String year = request.getParameter("year");
			String director = request.getParameter("director");
			String s_first = request.getParameter("s_first");
			String s_last = request.getParameter("s_last");
			if(!title.equals("")){
				query=query+" where movies.title like '% "+title.replaceAll("'", "''")+"%'";
			}

			if(!year.equals("")){
				try{
					int year_int=Integer.parseInt(year);
					query=query+" and where movies.year="+year_int;
				}
				catch(NumberFormatException i){
				}
			}
			if(!director.equals("")){
				query=query+" and where director="+director.replaceAll("'", "''");
			}
			if(!s_first.equals("")){
				query=query+" and where stars.first_name="+s_first.replaceAll("'", "''");
			}
			if(!s_last.equals("")){
				query=query+" and where stars.last_name="+s_last.replaceAll("'", "''");
			}
			query_count=query.replace("*", "count(*)");

			break;	

		default:
			break;
		}

		out.println(base.header());
		//out.println("<HEAD><TITLE>Movie Search</TITLE>");
		//out.println("</HEAD>");


		out.println(base.banner());

		try {
			connection = (Connection) dataSource.getConnection();
			PreparedStatement ps_movies = (PreparedStatement) connection.prepareStatement(query);
			ResultSet movies = ps_movies.executeQuery();
			print(movies, response, request);
			PreparedStatement ps_movies_count = (PreparedStatement) connection.prepareStatement(query_count);
			ResultSet countrs=ps_movies_count.executeQuery();
			countrs.next();
			Integer count = countrs.getInt(1);
			int i=page_id-2;
			if(i<=0)i=1;
			int displayed_count=0;
			out.println("<tr style=background-color:#00CCFF;padding:5px;'><td align=\"center\">");
			movies.first();
			if (movies.next())
			{
				for(;i<=page_id+3;i++) // for pagenation
					if(displayed_count<=count)
					{
						displayed_count=displayed_count+ipp;
						if (i == page_id)
							out.println("<a class='ft_links' style='float:left;background-color:#00CC00;' href=MovieList?by="+sort_by+"&arg="+request.getParameter("arg")+"&page_id="+(i)+"&ipp="+ipp+"&order="+ordered_state+" >"+i+"</a>");
						else
							out.println("<a class='ft_links' style='float:left;' href=MovieList?by="+sort_by+"&arg="+request.getParameter("arg")+"&page_id="+(i)+"&ipp="+ipp+"&order="+ordered_state+" >"+i+"</a>");
					}
				out.println("<span style=\"float:left;color:white;margin-top:10px;font-weight: bold;\">&nbsp&nbsp&nbsp<--Page Number</span>");
				if(page_id-1>=1)
					out.println("<a class='ft_links' style='margin-top:10px;' href=MovieList?by="+sort_by+"&arg="+request.getParameter("arg")+"&page_id="+(page_id-1)+"&ipp="+ipp+"&order="+ordered_state+" >Previous page</a>");
				if(page_id*ipp+1<=count)
					out.println("<a class='ft_links' style='margin-top:10px;' href=MovieList?by="+sort_by+"&arg="+request.getParameter("arg")+"&page_id="+(page_id+1)+"&ipp="+ipp+"&order="+ordered_state+" >Next page</a>");

				for(int j=5;j<=25;j=j+5)  // to set movies per page
				{
					if (j == ipp)
						out.println("<a class='ft_links' style='float:right;background-color:#00CC00;' href=MovieList?by="+sort_by+"&arg="+request.getParameter("arg")+"&page_id="+(1)+"&ipp="+j+"&order="+ordered_state+">"+j+"</a>");	
					else
						out.println("<a class='ft_links' style='float:right;' href=MovieList?by="+sort_by+"&arg="+request.getParameter("arg")+"&page_id="+(1)+"&ipp="+j+"&order="+ordered_state+">"+j+"</a>");
				}
				out.println("<span style=\"float:right;color:white;margin-top:10px;font-weight: bold;\">Items per page-->&nbsp&nbsp&nbsp</span>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("</td></tr></table>");
		out.println(base.footer());
	}

	public void print(ResultSet result, HttpServletResponse response, HttpServletRequest request) throws SQLException, IOException
	{
		ResultSet movies = result;
		PrintWriter out = response.getWriter();
		if (movies.next())
		{
			out.println("<table id=\"srch_res\"><tr style=\"background-color:#00CCFF;\"><th>");
			out.println("<h2 style=\"margin-left:10%;\">Search Results:</h2></th></tr>"
					+ "<tr style=\"background-color:#00CCFF;\"><td align=\"center\" >");
			out.println("<div align='center'><a class='sort_links' href=MovieList?by="+sort_by+"&arg="+request.getParameter("arg")+"&page_id="+(1)+"&ipp="+ipp+"&order=t_asc>"
					+ "<img src=\"http://goo.gl/QklvbJ?gdriveurl\" height='24' width='24'>Title</a>");
			out.println("<a class='sort_links' href=MovieList?by="+sort_by+"&arg="+request.getParameter("arg")+"&page_id="+(1)+"&ipp="+ipp+"&order=t_desc>"
					+ "<img src=\"http://goo.gl/MD348b?gdriveurl\" height='24' width='24'>Title</a>");
			out.println("<a class='sort_links' href=MovieList?by="+sort_by+"&arg="+request.getParameter("arg")+"&page_id="+(1)+"&ipp="+ipp+"&order=d_asc>"
					+ "<img src=\"http://goo.gl/QklvbJ?gdriveurl\" height='24' width='24'>Year</a>");
			out.println("<a class='sort_links' href=MovieList?by="+sort_by+"&arg="+request.getParameter("arg")+"&page_id="+(1)+"&ipp="+ipp+"&order=d_desc>"
					+ "<img src=\"http://goo.gl/MD348b?gdriveurl\" height='24' width='24'>Year</a>"
					+ "</div><br></td></tr>");

			String movie = "Dummy Movie";
			do
			{
				if (!movie.equals(movies.getString("title") + movies.getString("year")))
				{
					String star_query = "Select distinct(a.first_name), a.last_name, a.id from stars a "
							+ "where a.id in (select distinct(b.star_id) from stars_in_movies b "
							+ "where b.movie_id in (select distinct(c.id)  from movies c where c.title = '" + movies.getString("title").replace("'", "''") +"')) order by a.first_name;";
					PreparedStatement ps_stars = (PreparedStatement) connection.prepareStatement(star_query);
					ResultSet stars = ps_stars.executeQuery();

					String genre_query = "Select distinct(a.name) from genres a "
							+ "where a.id in (select distinct(b.genre_id) from genres_in_movies b where b.movie_id in "
							+ "(select distinct(c.id)  from movies c where c.title = '" + movies.getString("title").replace("'", "''") +"')) order by a.name;";
					PreparedStatement ps_genres = (PreparedStatement) connection.prepareStatement(genre_query);
					ResultSet genres = ps_genres.executeQuery();

					String disp = "Movie details:"
							+ "Year: "+ movies.getString("year") +" ";


					out.println("<tr><td><br><br>" + 
							"<div>" + 
							"<table  id=\"movie_search\"><tr><td width=\"20%;\"><div id=\"mov_list\">" + 
							"<img  style=\"position:absolute;z-index:1;margin-top:30px;margin-left:75px;\" src=\"" + movies.getString("banner_url") + "\"  alt=\"" + movies.getString("title") + " DVD Cover\" height='188' width='120'>" + 
							"<img style=\"z-index:2;\" src=\"http://gateway.hopto.org:9000/fabflix/images/short-case.png\" height='250' width='255'></div></td>" + 
							"<td width=\"40%;\"><div id=\"mov_det\">" + 
							"<div style=\"float:left;width:10%;\"><span style=\"font-weight: bold;\">Movie: </span></div>"+
							"<div style=\"float:right;width:90%;\"><a class=\"ag_links\" style=\"font-size:18px;\" href=\"Movie?MovieID=" + movies.getString("id") + "\">"+"<h4 class="+"tooltip"+" title="+"Title:"+movies.getString("title")+"Director:"+movies.getString("director")+"Year:"+movies.getString("year")+">"+"                "+ movies.getString("title") + "</h4></a></a>"
							+"<span>Year: " + movies.getString("year") + "</span><br>" + 
							"<br><span>Director: " + movies.getString("director") + "</span><br>"); 
					out.println("<br><span style=\"font-style: italic;\">Genre: ");
					String movie1= movies.getString("title");
					String dir=  movies.getString("director");
					String year = movies.getString("year");
					String[] gen = new String [5];
					String genre_list = "";
					while (genres.next())
					{
						genre_list += (genres.getString("name") + ", ");
						int i=0;
						
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
									+ "                 " + stars.getString("first_name") + " " + stars.getString("last_name") + "</a>");
						star_name = stars.getString("first_name") + stars.getString("last_name");
					} 
					
					out.println("</span></div></div></div></div></td></tr>"+
							"<tr><td><center><div class='cart_movie'><img src=\"http://goo.gl/xuA1xS?gdriveurl\" height=\"20\" width=\"20\">" + 
							"<a class=\"links\" href='Cart?MovieID=" + movies.getString("id") + "&qty=1&req=Add'\">&nbspAdd to My Cart</a></div></center></td>" + 
							"<td><center><div class='cart_movie' style='width:40%'><img src=\"http://goo.gl/rXhBkP?gdriveurl\" height=\"22\" width=\"22\">" + 
							"<a class=\"links\" href='" + movies.getString("trailer_url") + "'\">&nbspWatch Trailer</a></div></center></td>" + 
							"</tr></table>" + 
							"</div>" + 
							"<br></td></tr>"); 
				}
				movie = movies.getString("title") + movies.getString("year");
			} while (movies.next());


		}
		else
		{
			out.println("</div><div id='cart_title' style='border-radius: 0px 0px 0px 0px;'>"
					+ "<center><span>Sorry! No Results Found. Please Search again!</span>"
					+ "</center></div><br><br><br><br>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}