package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.DBOperation;

/**
 * Servlet implementation class AddCarServlet
 */
@WebServlet("/AddCarServlet")
public class AddCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/*boolean isAvaialble = DBOperation.checkAvil(carowner.getCarno());
		System.out.println("isAvaialble :"+isAvaialble);
		
		if(isAvaialble){
			
			response.getWriter().print(isAvaialble);
			System.out.println("User Already Exist!!");
		}
		else{
			
			DBOperation.insert(carowner);
			System.out.println("Inserted Succesfully!!");
			
		}*/
		int res = DBOperation.carin();
		System.out.println(res);
		PrintWriter pw = response.getWriter();
		pw.print(res);
		System.out.println("Car entered Successfully!!");
	}

}
