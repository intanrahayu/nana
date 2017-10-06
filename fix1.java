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
	public static final String HTML_START="<!DOCTYPE html><html lang=\"en\"><head><title>EMS Log Parser</title><meta charset=\"utf-8\"><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\"><script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script></head><body><div class=\"container\"><h2>EMS Log Parser</h2><table class=\"table\"><tr><th>Time</th><th>Message</th><th>Comment</th><th>Status</th><th>cDate</th></tr>";
	public static final String HTML_END="</body></html>";
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
		myFile = "/var/log/ems/";
		myFile = myFile + request.getParameter("name")+".log";
		String result = readFile();
		String temp = "";
		temp = temp + result;
		result = temp;
		
		out.println(HTML_START + temp +HTML_END);
	}
	
	private String readFile() throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader(myFile));
		String sLine = "";
		String sResult = "";
		StringBuilder sb = new StringBuilder();
		while(in.hasNextLine()) {
			sLine = in.nextLine();
		//String ending = "</tbody></table></div>"
		if (sLine.contains(match)) {
		String time = sLine.substring(0,23);
		String comment = sLine.substring(36, sLine.length());
		String status = "";
		String cDate = "";
		sResult = "<tr class=\"success\"><td>"+time+"</td><td>"+match+"</td><td>"+comment+"</td><td>"+status+"</td><td>"+cDate+"</td></tr>";
		} 

		else if (sLine.contains(match2)) {
		String time = sLine.substring(0,23);
		String comment = sLine.substring(35, sLine.length());
		String status = "";
		String cDate = "";
		sResult = "<tr class=\"info\"><td>"+time+"</td><td>"+match2+"</td><td>"+comment+"</td><td>"+status+"</td><td>"+cDate+"</td></tr>";
		} 

		else if (sLine.contains(match3)) {
		String time = sLine.substring(0,23);
		String comment = sLine.substring((sLine.lastIndexOf("comment=")+1), sLine.lastIndexOf("}"));
		String status = sLine.substring((sLine.indexOf("=")+1), sLine.indexOf(","));
		String cDate = sLine.substring((sLine.indexOf("=", sLine.indexOf("=") + 1))+1,sLine.indexOf(",", sLine.indexOf(",") + 1));
		sResult = "<tr class=\"danger\"><td>"+time+"</td><td>"+match3+"</td><td>"+comment+"</td><td>"+status+"</td><td>"+cDate+"</td></tr>";
		} 

		else {
		String time = sLine.substring(0,23);
		String comment = sLine.substring(36, sLine.length());
		String status = "";
		String cDate = "";
		sResult = "<tr class=\"warning\"><td>"+time+"</td><td>"+match4+"</td><td>"+comment+"</td><td>"+status+"</td><td>"+cDate+"</td></tr>";   
		}
			sb.append(sResult);
			sb.append("<br>");
		}
		in.close();
	out.println("</table></div>");
	return sb.toString();
	}
	
	//protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}