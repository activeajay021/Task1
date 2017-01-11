package ak.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletContoller extends HttpServlet {
protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 

{    response.setContentType("text/html;charset=UTF-8");
     PrintWriter out = response.getWriter();

     Object ob=request.getAttribute("qid1");//retrieving question ID from request object where it is set in Correction class   
     int qid = (Integer) ob;
     Object ob2=request.getAttribute("qno"); //retrieving question nuber from request object where it is set in Correction class     
     int qno = (Integer) ob2;
      
       try{
       Class.forName("oracle.jdbc.driver.OracleDriver");
       Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","user1","password");
       PreparedStatement ps=con.prepareStatement("select * from question where qid="+qid+"");//selecting question from database based on question ID 
       ResultSet rs=ps.executeQuery();
       rs.next();
       
       /* Based on Question ID and Number prnting Questions in redio button formet */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletContoller</title>");  
            out.println(" <link rel=\"stylesheet\" href=\"styles.css\">");
            out.println("<script language='javascript' type='text/javascript'src='functions.js'></script>\n");
            out.println("</head>");
            out.println("<body bgcolor='#E6E6FA'");
            out.println("<div id='div1'>");
            out.println("<form name='myForm' action='Correction' method='post'onclick='Function()'>");
            out.println("<h3 id='h3'>"+qno +") "+rs.getString("question")+"</h3>");  
            out.println("<input type=\"radio\" name=\"opt\" id='opt1' value="+rs.getString("opt1")+" />"+rs.getString("opt1")+"<br>");
            out.println("<input type=\"radio\" name=\"opt\" id='opt2' value="+rs.getString("opt2")+" />"+rs.getString("opt2")+"<br>");
            out.println("<input type=\"radio\" name=\"opt\" id='opt3' value="+rs.getString("opt3")+" />"+rs.getString("opt3")+"<br>");
            out.println("<input type=\"radio\" name=\"opt\" id='opt4' value="+rs.getString("opt4")+" />"+rs.getString("opt4")+"<br>");
            out.println("<input type='hidden' name='qid' value="+qid+"><br> ");/*sending question ID to Correction class to validate user 
                                                                   and set next question from out of 4 sets */
            out.println("<preventBack()>");
            out.println("<input type=submit name=bt id='sub' value=next ></input> ");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
            
       }
       catch(Exception e)
       {
           System.out.println(e);
       }

         
    }

  

}
