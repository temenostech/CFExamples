package com.temenos.services.ofsconnector.war;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.temenos.services.ofsconnector.data.response.ProcessOFSResponse;
import com.temenos.services.ofsconnector.ejb.OFSConnectorServiceBeanRemote;


public class FooServlet extends HttpServlet {
	private static final long serialVersionUID = 4028009567930253820L;

	private OFSConnectorServiceBeanRemote componentEJB;
	private String COMP_JNDI_NAME_REMOTE = "java:comp/env/ejb/OFSConnectorServiceBeanRemote";
	private Context ctx;
	
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
		
			// Just before the initial Context instantiation lets sort out the PRINCIPAL
			Principal p = request.getUserPrincipal();
			if (p == null) {
				out.println("<h2>WARNING: Did not receieve user &quot;PRINCIPAL&quot; for an EJB, continuing as &quot;anonymus&quot; user...</h2>");
			} else {
				out.println("<h2>User &quot;PRINCIPAL&quot; receieved as : " + p.getName() + ", calling EJB with the same context...</h2>");
			}
			// Check if we have server context
			if (ctx == null ) {
				try {
					ctx = new InitialContext(); // Context will be passed to an EJB container by an Application Server
				} catch (NamingException e) {
					throw new RuntimeException("Failed to instantiate InitailContext - " + e.getMessage());
				}
			}
			
			// Lookup component if null and Call Component operation Here
			if (componentEJB == null) {
				try {
					Object objRef = ctx.lookup(COMP_JNDI_NAME_REMOTE);
					componentEJB = (OFSConnectorServiceBeanRemote) objRef; 
				} catch (NamingException e) {
					throw new RuntimeException("Failed to lookup Component EJB - " + e.getMessage());
				}
			}
			
			ProcessOFSResponse processOFSResponse = componentEJB.processOFS(ofsRequest);
			out.println("<h1>OFSConnectorService method processOFS Responded</h1>");
			out.println("<h3>OFS Response : " 
					+ processOFSResponse.getOfsResponse() + "</h3>");
			out.println("<h3>Response Details : " 
					+ processOFSResponse.getResponseDetails() + "</h3>");
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