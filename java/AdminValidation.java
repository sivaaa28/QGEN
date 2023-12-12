import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminValidation")
public class AdminValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	response.setContentType("text/html");
	PrintWriter p=response.getWriter();	
	int flag=0;
	String username=request.getParameter("username");
	String password=request.getParameter("password");
		Connection c=DBUtil.createconnection();
		String query1="select username from adminvalidation";
	
		try {
			PreparedStatement s1 = c.prepareStatement(query1);
			ResultSet rs1=s1.executeQuery();
			
			while(rs1.next()) {
				if(username.equals(rs1.getString(1))){
					flag=1;
				}
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		if(flag==1) {
		Connection connection=DBUtil.createconnection();
		try {
			String query="select password from adminvalidation where username=?";
			
		    PreparedStatement s=connection.prepareStatement(query); 
		    s.setString(1,username);
		    ResultSet rs=s.executeQuery();
			String pswd="";
			
			while(rs.next()) {
				pswd=rs.getString(1);
			}
			System.out.println(pswd);
			System.out.println(username);
			System.out.println(password);
			if(pswd.equals(password))
			{
				RequestDispatcher r=request.getRequestDispatcher("success.html");
			    r.forward(request, response);
			}
			else {
				System.out.println("Not Same");
				p.println("<script>alert('password not match');location='adminlogin.html';</script>");
			}			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		}	
		else {
			p.println("<script>alert('username not match');location='adminlogin.html';</script>");
		}
		}
	

}
