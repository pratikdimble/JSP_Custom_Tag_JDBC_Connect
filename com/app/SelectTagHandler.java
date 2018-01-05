package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class SelectTagHandler extends TagSupport {
	
	//declare the resources
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	ResultSetMetaData rsmd=null;
	
	private String table;
	
	public String getTable() {
		return table;
	}


	public void setTable(String table) {
		this.table = table;
	}


	//define the default constructor
	public SelectTagHandler() {
		try {
			//load and register the driver
				Class.forName("oracle.jdbc.OracleDriver");
			//get the connection
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
			//create the statement object
				st=con.createStatement();
		}catch (Exception e) {
		e.printStackTrace();
		}
		
	}//constructor
	
	
	@Override
	public int doStartTag() throws JspException {
	
		try {
			//create the query
			rs=st.executeQuery("SELECT * FROM "+table);
				//get the number of columns
			rsmd=rs.getMetaData();
			int count=rsmd.getColumnCount();
			
			//get the JSP writer
			JspWriter out=pageContext.getOut();
			out.println(count);
			out.println("<html><body><table border='1'>");
				
	  		out.println("</tr>");
	  		for(int i=1;i<=count;i++) {
					out.println("<td>"+rsmd.getColumnName(i)+"</td>");
				}//for
				out.println("</tr>");
				
					while(rs.next()) {
						out.println("<tr>");
						for(int i=1;i<=count;i++) {
							out.println("<td>"+rs.getString(i)+"</td>");
						}
										out.println("</tr>");
						
					}//while
					
					out.println("</table></body></html>");
		}//try
		catch (Exception e) {
	e.printStackTrace();
		}
		return SKIP_BODY;
	}//dostartTag()
	
	
	
	
}//class
