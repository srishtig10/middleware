import javax.servlet.http.HttpSession;


public class headerFooter {

	public boolean loggedin = false;
	String Email = null;

	public headerFooter(HttpSession session)
	{
		Email = (String) session.getAttribute("Email");
		if (!(Email == null))
			loggedin = true;
	}

	public String header()
	{
		String head = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" + 
				"<html>"+"<head>"+"<meta charset="+"ISO-8859-1"+">"+"<meta http-equiv="+"Content-Type" +"content="+"text/html; charset=ISO-8859-1"+">"+
				"<link rel="+"stylesheet"+" type="+"text/css"+" href="+"design.css"+">"+
				"<script src="+"https://code.jquery.com/jquery-1.12.1.min.js"+"></script>"+
				"<script type="+"text/javascript" +"src="+"jquery.tooltipster.js"+" ></script>"+
				"<script src="+"http://code.jquery.com/jquery-1.10.2.js"+"></script>"+
				"<script src="+"http://code.jquery.com/ui/1.11.4/jquery-ui.js"+"></script>"+
				"<script type="+"text/javascript"+" src="+"https://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"+"></script>"
				+"<script type=\"text/javascript\" src=\"bootstrap.js\"></script>"
				+ "<link rel = \"stylesheet\" type=\"text/css\" href=\"bootstrap.css\">"
+

				"<title> FABFLIX - HOMEPAGE </title>"+"<link rel= "+"stylesheet" +"link="+"text/css" +"href="+"tooltipster.css"+"><link rel="+"stylesheet"+" href="+"//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"+">"+
				"<body id="+"stylemain"+" link ="+"white"+">"+"<div align="+"center"+">"+"<table width="+"998"+" border="+"0"+" cellspacing="+"0"+" cellpadding="+"0"+">"+"<tr>"+"<td height="+"10"+">"+"&nbsp;</td>"+"</tr>"
				+"<tr>"+"<td height="+"140"+">"+"<div class="+"menu"+">"+"<form action=MovieList method='get'>" + 
				"<input type=\"hidden\" name=\"by\" value=\"search\">" + 
				"<label for="+"src_box"+"><button class=\"btn_srch\">Search</button></label>" + 
				"<div\"><input id=\"srch_box\" type=\"text\" name=\"arg\"   Click here to search a movie...\"  maxlength=\"64\"/></div>" + 
				"<input type=\"hidden\" name=\"order\" value=\"t_asc\"/>" + 
				"</form>"+"</div>"+"<div class="+"tinyspacer"+">&nbsp;"+"</div>"+"<div class="+"menutext"+"><a href="+"Main"+">Home</a>| <a href="+"advSearch"+">Advanced Search</a> | <a href="+"Cart?MovieID=0&qty=0&req=View"+">My Cart</a> | <a href="+"Testlogin"+">Logout</a></div>"+
				" </div></td>"+"</tr><tr>"+"<td height="+"10"+">&nbsp;</td>"+"</tr>"+"<tr><td><div align="+"center"+"><h1><strong><center><font color="+"white"+">FABFLIX - EXPLORE THE WORLD OF MOVIES</center></strong></h1></font>"+"&nbsp;<br/>";	;
				return head;
	}


	public String banner()
	{
		String banner="l";
		return banner;
	}

	public String footer()
	{
		String footer = "<div class="+"tinyspacer"+">&nbsp;<br />"+"</div>"+"<div align="+"center"+"><a href="+"Main"+">Home</a> | <a href="+"about.html"+">About Us</a> | <a href="+"Privacy.jsp"+">Privacy Policy</a> | <a href="+"CustInfo"+">Check Out</a>"+
				"</table>"+"<div class="+"tinyspacer"+">&nbsp;<br />"+"<center><font size= "+"2px"+">Copyright &copy; 2016 by Aishwarya, Amita, Srishti. All rights reserved.</font></div>"+" </div>"+
				"&nbsp;<br /></center></td>"+"</tr>"+"</table></body></html>";
		return footer;
	}

}
