package ak.com;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class Correction extends HttpServlet {
    
int qno=1;

protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
{
    
response.setContentType("text/html;charset=UTF-8");
PrintWriter out = response.getWriter();

 try
    {

 Class.forName("oracle.jdbc.driver.OracleDriver");
 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","user1","password");
         
 if(request.getParameter("1st")!=null)//For 1st request
  {
    
    request.setAttribute("qid1",1);
    request.setAttribute("qno",1);
    RequestDispatcher rd=request.getRequestDispatcher("/ServletContoller");//To communicate with ServeletController class
    rd.include(request, response);// Sending Request and Responce Object from Correction servlet class to  ServeletController class
            
    
    /* create new table in database at the time of 1st request*/
    PreparedStatement ps1=con.prepareStatement("create table log(qno varchar(10),QUESTION varchar(20),answered varchar(20),SETNO varchar(20))");
    ps1.executeUpdate();

    
 }
 else//For second request onwards 
 {
   
        String ans=request.getParameter("opt");//geting user option data from ServletControler class
        String qid=request.getParameter("qid");/*geting question ID what ever user selected
                                                 from ServletControler class*/
        int x=Integer.parseInt(qid);//type casting question id from String to integer form 
         
       
        PreparedStatement ps=con.prepareStatement("select * from question where qid="+x+"");/*selecting record from database 
                                                                                             based on question ID */
        ResultSet rs=ps.executeQuery();
        
        
        
        rs.next();
      
        String dbans=rs.getString("ans"); 
        String setno=rs.getString("setno");
        String dq=rs.getString("QUESTION");
        
 
        PreparedStatement ps1=con.prepareStatement("insert into log values("+qno+",'"+dq+"',"+ans+","+setno+")");/*inserting test user record into log table*/
        int j=ps1.executeUpdate();

        if(dbans.equals(ans))//chaecking user answer with data base answer
         {
            x++;//if user select correct answer the question id will be increament 1
         }
        
        else
         {
            
             int setno1=Integer.parseInt(setno);//converting setno String form to integer form to perform increament operations 
             
             if(setno1==1)//if set1 question is selected worng then quastion ID will be increament 10 i.e went to next set as set2
             {
                       x=11;
             }
             
             else if(setno1==2)//if set2 question is selected worng then quastion ID will be increament 10 i.e went to next set as set3
             {
                        x=21;
             }
             
             else //if set3 question is selected worng then quastion ID will be increament 10 i.e went to next set as set4
             {
                        x=31;
             }
             
             
               
         }
        qno++;//after validation question number will be increment to 1 
             
        if(qno<=8)//if question number is less then 8 then will go for next question 
            {
             
            request.setAttribute("qid1",x);//setting next Question ID in request object to retrive it ServeletController class
            request.setAttribute("qno",qno);//setting next Question number in request object to retrive it ServeletController class
            RequestDispatcher rd=request.getRequestDispatcher("/ServletContoller");//To communicate with ServeletController class
            rd.include(request, response);// Sending Request and Responce Object from Correction servlet class to  ServeletController class
            
            }
        

        
        
else//if question number is is exieded to 8 then have to print user selected data from database table(log) to html page
    {
 
                PreparedStatement ps2=con.prepareStatement("select * from log");//to select data from log table
                ResultSet rs1=ps2.executeQuery();
                
               out.println("<body bgcolor='#E6E6FA'");
                out.println(" <link rel='stylesheet' href='styles.css'>");
                out.print("<table width=50% border=1>");
	        out.print("<caption>Result:</caption>");
                ResultSetMetaData rsmd=rs1.getMetaData();//To select MetaData to print column Information inhtml page
	        int total=rsmd.getColumnCount();
                out.print("<div>");
                
                out.print("<tr>");
			for(int i=1;i<=total;i++)
			{
				out.print("<th>"+rsmd.getColumnName(i)+"</th>");// Printing log table column data
				
			}
	        
                
                out.print("<tr>");
                
                out.print("</div>");
                
                while(rs1.next())
                  {
                      
                       
                        String qno=rs1.getString("qno"); 
                        String q=rs1.getString("QUESTION"); 
                        String a=rs1.getString("answered"); 
                        String s=rs1.getString("setno"); 
                        
                        out.print("<tr><td>"+qno+"</td><td>"+q+"</td><td>"+a+"</td><td>"+s+"</td></tr>");//Printng log table description of every record 
       
                  }
                out.println("</body>");
                PreparedStatement ps4=con.prepareStatement("drop table log");
                ps4.executeUpdate();
              
    }
        
            
    }
   
}   
 
  catch(Exception e)
    {
        System.out.println(e);
    }

}
         
}
