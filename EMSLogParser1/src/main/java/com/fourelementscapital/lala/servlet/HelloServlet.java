package com.fourelementscapital.lala.servlet;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;


//@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String HTML_START="<!DOCTYPE html><html lang=\"en\"><head><script src=\"//code.jquery.com/jquery-1.11.3.min.js\"></script><script>$(document).ready(function(){$('.col-sm-7').click(function(){var text = $(this).parent('.row').find('font').text();$('#cc').text(text);})});</script><script>function toggle(thisname) {var baris=document.getElementsByClassName(\"row\");for (i=0;i<baris.length;i++){if (baris[i].getAttribute(thisname)){if (baris[i].style.display==\"none\"){baris[i].style.display = \"\";}else {baris[i].style.display = \"none\";}}}}</script><title>EMS Log Parser</title><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><link rel=\"stylesheet\" type=\"text/css\" href=\"css/custom.css\"><style>#match{background-color: #006666;} #match2{background-color: #4d7380;} #match3{background-color: #007acc;} #match4{background-color: #6b8eae;} #match:hover{background-color: #ff9900;} #match2:hover{background-color: #ff9900;} #match3:hover{background-color: #ff9900;} #match4:hover{background-color: #ff9900;}</style></head><body><style>div{word-wrap: break-word;}</style><div class=\"container-fluid\"><div class=\"row\"><div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\" style=\"background-color:#004d39;\"><font color=white><h3><b>EMS Log Parser</b></h3><p>Input your Trade ID</p><form action = \"HelloServlet\" method = \"GET\"><div class=\"form-group\"><label for=\"tradeID\"><b>Trade ID:</b></label><input type=\"text\" name=\"name\" class=\"form-control\" id=\"tradeID\"></div></form><input type=\"submit\" name=\"process\" value=\"Process\" class=\"btn btn-info\"> <button type=\"button\" class=\"btn btn-warning\" onclick=\"goBack()\"><b>Clear</b></button><br><br><script>function goBack() {window.history.back();} </script></font></div></div><br><br><br><form><div class=\"checkbox\" onclick=\"toggle('nameit');\"><label><input type=\"checkbox\" value=\" \"><b>Skip New Comment</b></label></div></form><div class=\"row \"><div class=\"col-lg-3 col-md-3 col-sm-3 col-xs-3\"><font color=\"white\"><b><center>Time</center></b></font></div><div class=\"col-lg-2 col-md-2 col-sm-2 col-xs-2\"><font color=\"white\"><b><center>Message</center></b></font></div><div class=\"col-lg-5 col-md-5 col-sm-5 col-xs-5\"><b><center>Comment</center></b></div><div class=\"col-lg-2 col-md-2 col-sm-2 col-xs-2\"><font color=\"white\"><b><center>Status</center></b></font></div></div>";
	public static final String HTML_END="</table></body></html>";
	public String detail = "";

	public String match = "NewComment";
	public String match2 = "NewStatus";
	public String match3 = "NewUpdateOrder";
	public String match4 = "AddFill";
	public String myFile = "";
	
       
    public HelloServlet() {
        super();
    }
	
	
	public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
     doGet(request, response);
  }
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try{
		myFile = "/var/log/ems/";
		myFile = myFile + request.getParameter("name")+".log";
		String result = readFile();
		String temp = "";
		temp = temp + result;
		result = temp;
		out.println(HTML_START + temp +HTML_END+detail);
		} catch (FileNotFoundException e)

		{
		out.println("<html><body><script>window.alert(\"Trade ID was not found\");</script></body></html>");
		}
		
	}
	
	public String readFile() throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader(myFile));
		String sLine = "";
		String sResult = "";
		int countermatch = 0;
		String time = "";
		String comment = "";
		String status = "";
		//String cDate = "";
		String detail = "<br /><div class=\"row\"><div class=\"col-sm-2\" style=\"background-color:lavender;\">Time</div><div class=\"col-sm-2\" style=\"background-color:lavenderblush;\">Message</div><div class=\"col-sm-4\" style=\"background-color:lavender;\">Comment</div><div id=\"cc\" class=\"col-sm-2\" style=\"background-color:lavender;\">.col-sm-4</div></div>";
		int countermatch2 = 0;
		int countermatch3 = 0;
		int countermatch4 = 0;
		StringBuilder sb = new StringBuilder();
		while(in.hasNextLine()) {
			sLine = in.nextLine();
			if (sLine.contains(match)) {
			time = sLine.substring(0,23);
			comment = sLine.substring(36, sLine.length());
			status = "";
			//cDate = "";
			sResult = "<div class=\"row\" id=\"match\" nameit=\"hide\"><div class=\"col-lg-3 col-md-3 col-sm-3 col-xs-3\"><font color=\"white\">"+time+"</font></div><div class=\"col-lg-2 col-md-2 col-sm-2 col-xs-2\"><font color=\"white\"><span class=\"badge badge-primary\" style=\"background-color:grey\"><center>"+match+"</center></span>"+"</font></div><div class=\"col-lg-7 col-md-7 col-sm-7 col-xs-7\"><font color=\"white\">"+comment+"</font></div></div>";
			//detail = "<table class=\"table\"><thead><tr><th>Time</th><th>Message</th><th>Comment</th><th>Status</th><th>cDate</th></tr></thead><tr div id=\"demo"+countermatch+"\" class=\"collapse\"><td>"+time+"</td><td>"+match+"</td><td>"+comment+"</td><td>"+status+"</td><td>"+cDate+"</td></tr></table>";
			//countermatch = countermatch+1;
			}

			else if (sLine.contains(match2)) {
			time = sLine.substring(0,23);
			status = sLine.substring(35, sLine.length());
			comment = "";
			//cDate = "";
			sResult = "<div class=\"row\" id=\"match2\"><div class=\"col-lg-3 col-md-3 col-sm-3 col-xs-3\"><font color=\"white\">"+time+"</font></div><div class=\"col-lg-2 col-md-2 col-sm-2 col-xs-2\"><font color=\"white\"><span class=\"badge badge-warning\" style=\"background-color:#cca300\"><center>"+match2+"</center></span>"+"</font></div><div class=\"col-lg-5 col-md-5 col-sm-5 col-xs-5\"><font color=\"white\" size=\"1\">"+comment+"</font></div><div class=\"col-lg-2 col-md-2 col-sm-2 col-xs-2\"><font color=\"white\"><center>"+status+"</center></font></div></div>";
			//detail = "<table class=\"table\"><thead><tr><th>Time</th><th>Message</th><th>Comment</th><th>Status</th><th>cDate</th></tr></thead><tr div id=\"demo"+countermatch2+"\" class=\"collapse\"><td class= \"small\">"+time+"</td><td class= \"small\">"+match+"</td><td class= \"small\">"+comment+"</td><td class= \"small\">"+status+"</td><td class= \"small\">"+cDate+"</td></tr></table>";
			//countermatch2 = countermatch2+1;
			} 

			else if (sLine.contains(match3)) {
			time = sLine.substring(0,23);
			comment = sLine.substring((sLine.lastIndexOf("="))+1, sLine.lastIndexOf("}"));
			status = sLine.substring((sLine.indexOf("=")+1), sLine.indexOf(","));
			//cDate = sLine.substring((sLine.indexOf("=", sLine.indexOf("=") + 1))+1,sLine.indexOf(",", sLine.indexOf(",") + 1));
			sResult = "<div class=\"row\" id=\"match3\"><div class=\"col-lg-3 col-md-3 col-sm-3 col-xs-3\"><font color=\"white\">"+time+"</font></div><div class=\"col-lg-2 col-md-2 col-sm-2 col-xs-2\"><font color=\"white\" size=\"1\"><span class=\"badge badge-danger\" style=\"background-color:#800000\"><center>"+match3+"</center></span>"+"</font></div><div class=\"col-lg-5 col-md-5 col-sm-5 col-xs-5\"><font color=\"white\">"+comment+"</font></div><div class=\"col-lg-2 col-md-2 col-sm-2 col-xs-2\"><font color=\"white\"><center>"+status+"</center></font></div></div>";
			//detail = "<table class=\"table\"><thead><tr><th>Time</th><th>Message</th><th>Comment</th><th>Status</th><th>cDate</th></tr></thead><tr div id=\"demo"+countermatch3+"\" class=\"collapse\"><td>"+time+"</td><td>"+match+"</td><td>"+comment+"</td><td>"+status+"</td><td>"+cDate+"</td></tr></table>";
			//countermatch3 = countermatch3+1;
			} 

			else {
			time = sLine.substring(0,23);
			comment = sLine.substring(36, sLine.length());
			status = "";
			//cDate = "";
			sResult = "<div class=\"row\" id=\"match4\"><div class=\"col-lg-3 col-md-3 col-sm-3 col-xs-3\"><font color=\"white\">"+time+"</font></div><div class=\"col-lg-2 col-md-2 col-sm-2 col-xs-2\"><font color=\"white\"><span class=\"badge badge-success\" style=\"background-color:#00cc66\"><center>"+match4+"</center></span>"+"</font></div><div class=\"col-lg-5 col-md-5 col-sm-5 col-xs-5\"><font color=\"white\">"+comment+"</font></div><div class=\"col-lg-2 col-md-2 col-sm-2 col-xs-2\"><font color=\"white\"><center>"+status+"</center></font></div></div>";
			//detail = "<table class=\"table\"><thead><tr><th>Time</th><th>Message</th><th>Comment</th><th>Status</th><th>cDate</th></tr></thead><tr div id=\"demo"+countermatch4+"\" class=\"collapse\"><td>"+time+"</td><td>"+match+"</td><td>"+comment+"</td><td>"+status+"</td><td>"+cDate+"</td></tr></table>";
			//countermatch4 = countermatch4+1;
			}
				sb.append(sResult);
			
		}
		in.close();
		return sb.toString();
	}
	
	}
