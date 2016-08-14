package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.DBOperation;

import org.codehaus.jackson.map.ObjectMapper;

import pojo.CarOwner;

/**
 * Servlet implementation class AddServlet
 */
@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    List<CarOwner> carowners = new LinkedList<CarOwner>();

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
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "", result="";
		if(br != null){
			System.out.println("br NOT NULL");
			json = br.readLine();
			System.out.println("JSON STRING:"+json);
		}
		System.out.println("Working1");
		// 2. initiate jackson mapper
    	ObjectMapper mapper = new ObjectMapper();
    	
    	// 3. Convert received JSON to Article
    	CarOwner carowner = mapper.readValue(json, CarOwner.class);
    	
    	System.out.println(carowner.getCarno());
    	try {
			boolean isAvaialble = DBOperation.checkAvil(carowner.getCarno());
			System.out.println("isAvaialble :"+isAvaialble);
			
			if(isAvaialble){
				
				response.getWriter().print(isAvaialble);
				DBOperation.update(carowner);
				result="updated";
				System.out.println("User Already Exist!!");
			}
			else{
				
				DBOperation.insert(carowner);
				result="inserted";
				System.out.println("Inserted Succesfully!!");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 4. Set response type to JSON
		response.setContentType("string");		    
    	
//    	// 5. Add article to List<Article>
//		if(persons.size() > 20)
//			persons.remove(0);
//		
//		persons.add(person);

		// 6. Send List<Article> as JSON to client
    	//mapper.writeValue(response.getOutputStream(), carowners);
		PrintWriter pw = response.getWriter();
		pw.print(result);
    	System.out.println(carowners);
	
	}

}
