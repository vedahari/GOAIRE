package OnAireServlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class OnAireServer
 */
@WebServlet("/OnAireServer")
public class OnAireServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    private RequestManager reqProcessor = null;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OnAireServer() {
        super();
        System.out.println("\n Constructor entered!!!"); 
        
    }
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("\n Initializer entered!!!");        
        reqProcessor = new RequestManager();
        reqProcessor.initialize();
      }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    	
    	//response.getWriter().append("Served at: ").append(request.getContextPath());    
    	//response.setContentType("text/html");
    	//PrintWriter output = response.getWriter();
    	//output.println("<html><body><h3>Hello Servlets</h3></body></html>");	
    	IdlingTimeResponse res = new IdlingTimeResponse();
    	System.out.println("\n Entered doGet method!");
    	if (request!=null){
    		//System.out.println("\n Attr names are "+request.getAttributeNames().toString());
    		String strLat = request.getParameter("Latitude");
    		String strLon = request.getParameter("Longitude");
    		String strB = request.getParameter("B");

    		if (strLat!=null && strLon!=null){		
    			double reqLat = Double.parseDouble(strLat);
    			double reqLon = Double.parseDouble(strLon);
    			int B = 27;
    			if (strB!=null){
    				B = Integer.parseInt(strB);
    			}    			
    			System.out.println("Requested ("+reqLat+","+reqLon+") for a vehicle with B as "+B);
    			IdlingTimeResponse res2 = reqProcessor.getAdvisedIdlingTime(reqLat,reqLon,B);
    			
    			if (res2!=null){
    				res = res2;
    				System.out.println("Sending "+res.toString());
    			}    			
    		}
    	}
    	response.addHeader("Cost", Double.toString(res.getCost()));
    	response.setContentType("String");
    	response.getWriter().write(Integer.toString(res.getIdlingTime()));    	
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doGet(request, response);
	}

}
