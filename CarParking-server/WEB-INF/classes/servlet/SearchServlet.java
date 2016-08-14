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
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
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
		String json = "";
		CarOwner carownerreturn = null;
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
    	
    	System.out.println("CAR NO:"+carowner.getCarno());
    	
    	try {
			boolean isAvaialble = DBOperation.checkAvil(carowner.getCarno());
			System.out.println("isAvaialble :"+isAvaialble);
			
			if(isAvaialble){
				
				//response.getWriter().print(isAvaialble);
				System.out.println("CAR NO 2:"+carowner.getCarno());
				carownerreturn = DBOperation.searchByCarNo(carowner.getCarno());
				System.out.println("NAME:"+carownerreturn.getName());
				//System.out.println("User Already Exist!!");
			}
			else{
				
				System.out.println("Car doesnot exists!!");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 4. Set response type to JSON
		response.setContentType("application/json");		    
    	
//    	// 5. Add article to List<Article>
//		if(persons.size() > 20)
//			persons.remove(0);
//		
//		persons.add(person);

		// 6. Send List<Article> as JSON to client
		//mapper.writeValue(out, value);
		/*PrintWriter out = response.getWriter();
		mapper.writeValue(out, carownerreturn);*/
    	mapper.writeValue(response.getOutputStream(), carownerreturn);
    	mapper.writeValue(System.out, carownerreturn);
    	System.out.println("CAR OWNER RETURN VALUE:"+carownerreturn);
	}

}
