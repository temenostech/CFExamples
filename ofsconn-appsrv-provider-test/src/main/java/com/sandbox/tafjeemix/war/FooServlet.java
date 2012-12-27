package com.sandbox.tafjeemix.war;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.temenos.services.ofsconnector.OFSConnectorServiceProviderAPI;
import com.temenos.services.ofsconnector.OFSConnectorServiceSpringContext;
import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.soa.services.T24UserContextCallBackImpl;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.T24UserDetails;


public class FooServlet extends HttpServlet {
	private static final long serialVersionUID = 4028009567930253820L;

	private OFSConnectorServiceProviderAPI service = null;
	
	public void init()
			throws javax.servlet.ServletException {
		// We will instantiate our Spring context here	
		try {
			OFSConnectorServiceSpringContext.loadServiceContext(this.getClass().getClassLoader());
		} catch (Exception e) {
			throw new ServletException("Failed to load spring context, see logs for error");
		}
	}
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Servlet FooServlet</title>");
		out.println("</head>");
		out.println("<body>");
		String ofsRequest = request.getParameter("ofsRequest");
		if (ofsRequest == null) {
			out.println("<h1>Please provide &quot;ofsRequest&quot;</h1>");			
		} else {
			
			// Check if service is instantiated, otherwise load it
			if (service == null) {
				try {
					service = (OFSConnectorServiceProviderAPI) OFSConnectorServiceSpringContext.getContext().getBean("OFSConnectorServiceProvider");
					service.setUserContextCallBack(new T24UserContextCallBackImpl(new T24UserDetails("INPUTT", "123456", "")));
				} catch (Exception e) {
					throw new ServletException("Failed to load OFSConnectorServiceProvider bean");
				}
				
			}
			OFSConnResponse ofsResponse = new OFSConnResponse();
			ResponseDetails serviceResponse = new ResponseDetails();
			service.processOFS(ofsRequest, ofsResponse, serviceResponse);
			out.println("<h1>Calling OFSConnector.processOFSSimple</h1><br /><h2>" +
					"Response : " + ofsResponse.getOfsResponse() + "</h2><br /><h2>" +
					serviceResponse + "</h2>");
		}
		out.println("</body>");
		out.println("</html>");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}