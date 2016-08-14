package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import pojo.*;

/**
 * Servlet implementation class JSONServlet
 */
@WebServlet("/JSONServlet")
public class JSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONServlet() {
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
    	
    	System.out.println(carowner.getName());

		// 4. Set response type to JSON
		response.setContentType("application/json");		    
    	
//    	// 5. Add article to List<Article>
//		if(persons.size() > 20)
//			persons.remove(0);
//		
//		persons.add(person);

		// 6. Send List<Article> as JSON to client
    	mapper.writeValue(response.getOutputStream(), carowners);
    	System.out.println(carowners);
	}

}
