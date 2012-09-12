package com.temenos.services.ofsconnector.tafc;

// Java imports
import java.util.logging.Logger;

// Complex Data and Converter Classes
import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.services.ofsconnector.data.converter.OFSConnResponseConverter;

// T24 JCA Classes
import com.jbase.jremote.JConnection;
import com.jbase.jremote.JDynArray;
import com.temenos.soa.utils.JDynUtils;
import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JSubroutineNotFoundException;
import com.jbase.jremote.JSubroutineParameters;

// Soaframework classes
import com.temenos.soa.services.T24ServiceCallBack;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.Response;
import com.temenos.soa.services.data.converter.ResponseDetailsConverter;
import com.temenos.soa.services.data.UserDetails;
import com.temenos.soa.services.data.T24UserDetails;
import com.temenos.soa.services.data.SSOUserDetails;
import com.temenos.soa.services.tafc.JCAHelper;

// Exception classes
import com.temenos.soa.exceptions.*;
import javax.naming.NamingException;

// Sevrice and Provider INTERFACE
import com.temenos.services.ofsconnector.OFSConnectorServiceProviderAPI;

public class OFSConnectorServiceProviderImplTAFC implements OFSConnectorServiceProviderAPI {

	private static final Logger logger = Logger.getLogger(OFSConnectorServiceProviderImplTAFC.class.getName());
	private static final String SERVER_LOAD_USER_SMS = "JF.VALIDATE.SIGN.ON";
	private JCAHelper jcaHelper = new JCAHelper();
	private T24ServiceCallBack serviceCallBack = null;
	
    @Override
    public void processOFS(String ofsRequest, String ofsSourceId, OFSConnResponse ofsResponse, ResponseDetails responseDetails) {

	    JConnection ivConnection = null;
	    try { 
			JSubroutineParameters params = new JSubroutineParameters();
            
            JDynUtils jdUtils = new JDynUtils();
		    params.add(new JDynArray(ofsRequest.toString()));	// Input parameter - ofsRequest
		    params.add(new JDynArray(ofsSourceId.toString()));	// Input parameter - ofsSourceId
            params.add(new JDynArray());                         // Output parameter - ofsResponse
		    params.add(new JDynArray());					     // Output parameter - responseDetails
			
			// At this point we are ready to execute the request, so get the connection from JCA pool
			ivConnection = getConnection();
			
			// Set/Switch the user context for this connection
			loadUserSMS(ivConnection, responseDetails);
			if ( responseDetails.getReturnCode().equals(ResponseDetails.RETURN_CODE_FAILURE) ) {
				return ;
			}
			
			// Call the JBC operation
		    params = processRequest("T24OFSConnectorServiceImpl.processOFS", params, ivConnection );					// Process the request

            JDynArray res;
		    res = (JDynArray) params.get(2);	// Output parameter - ofsResponse
 		    OFSConnResponseConverter ofsResponseConverterObj = new OFSConnResponseConverter();
		    ofsResponseConverterObj.fromJDyn(ofsResponse, res);
		    JDynArray resDetails = (JDynArray) params.get(3);	// Output parameter - responseDetails
		    ResponseDetailsConverter respConverter = new ResponseDetailsConverter();
		    respConverter.fromJDyn(responseDetails, resDetails);
        
        } catch (ConnectivityException e){
			String responseDetailsResponseText = "Failed to connect to remote T24 instance - ConnectivityException : " + e.getMessage();
			logger.severe (responseDetailsResponseText);
            responseDetails.addError(new Response("EB-CONNECTION.ERROR", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} catch (SubroutineCalledFailedException e) {
			String responseDetailsResponseText = "Request operation for the service is not available - SubroutineCalledFailedException : " + e.getMessage();
			logger.severe (responseDetailsResponseText);
            responseDetails.addError(new Response("EB-OPERATION.MISSING", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} catch (InvalidNestLevelException e) {
			String responseDetailsResponseText = "Failed to convert Input/Output parameters for T24 - InvalidNestLevelException : " + e.getMessage();
			logger.severe (responseDetailsResponseText);
            responseDetails.addError(new Response("EB-INVALID.RESPONSE", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} finally {
			try {
				if (ivConnection != null){
					closeConnection(ivConnection);
				}
			} catch (ConnectivityException e) {
				String responseDetailsResponseText = "Failed to close connection with T24 - ConnectivityException : " + e.getMessage();
				logger.severe (responseDetailsResponseText);
				responseDetails.addError(new Response("EB-CONNECTION.ERROR", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
			}
		}
		
		return;
		
	}

        @Override
    public void processOFSSimple(String ofsRequest, OFSConnResponse ofsResponse, ResponseDetails responseDetails) {

	    JConnection ivConnection = null;
	    try { 
			JSubroutineParameters params = new JSubroutineParameters();
            
            JDynUtils jdUtils = new JDynUtils();
		    params.add(new JDynArray(ofsRequest.toString()));	// Input parameter - ofsRequest
            params.add(new JDynArray());                         // Output parameter - ofsResponse
		    params.add(new JDynArray());					     // Output parameter - responseDetails
			
			// At this point we are ready to execute the request, so get the connection from JCA pool
			ivConnection = getConnection();
			
			// Set/Switch the user context for this connection
			loadUserSMS(ivConnection, responseDetails);
			if ( responseDetails.getReturnCode().equals(ResponseDetails.RETURN_CODE_FAILURE) ) {
				return ;
			}
			
			// Call the JBC operation
		    params = processRequest("T24OFSConnectorServiceImpl.processOFSSimple", params, ivConnection );					// Process the request

            JDynArray res;
		    res = (JDynArray) params.get(1);	// Output parameter - ofsResponse
 		    OFSConnResponseConverter ofsResponseConverterObj = new OFSConnResponseConverter();
		    ofsResponseConverterObj.fromJDyn(ofsResponse, res);
		    JDynArray resDetails = (JDynArray) params.get(2);	// Output parameter - responseDetails
		    ResponseDetailsConverter respConverter = new ResponseDetailsConverter();
		    respConverter.fromJDyn(responseDetails, resDetails);
        
        } catch (ConnectivityException e){
			String responseDetailsResponseText = "Failed to connect to remote T24 instance - ConnectivityException : " + e.getMessage();
			logger.severe (responseDetailsResponseText);
            responseDetails.addError(new Response("EB-CONNECTION.ERROR", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} catch (SubroutineCalledFailedException e) {
			String responseDetailsResponseText = "Request operation for the service is not available - SubroutineCalledFailedException : " + e.getMessage();
			logger.severe (responseDetailsResponseText);
            responseDetails.addError(new Response("EB-OPERATION.MISSING", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} catch (InvalidNestLevelException e) {
			String responseDetailsResponseText = "Failed to convert Input/Output parameters for T24 - InvalidNestLevelException : " + e.getMessage();
			logger.severe (responseDetailsResponseText);
            responseDetails.addError(new Response("EB-INVALID.RESPONSE", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} finally {
			try {
				if (ivConnection != null){
					closeConnection(ivConnection);
				}
			} catch (ConnectivityException e) {
				String responseDetailsResponseText = "Failed to close connection with T24 - ConnectivityException : " + e.getMessage();
				logger.severe (responseDetailsResponseText);
				responseDetails.addError(new Response("EB-CONNECTION.ERROR", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
			}
		}
		
		return;
		
	}

    
	/** Get Connection to the T24 TAFC Instance
	 * 
	 * @return JConnection	- Connection from JCA pool
	 */
	private JConnection getConnection() throws ConnectivityException {
		try{
			return (jcaHelper.getConnection());
		} catch (NamingException e) {
			logger.severe("Configuration error - NamingException : " + e.getMessage());
			throw new ConnectivityException("Configuration error - NamingException : " + e.getMessage());
		} catch (JRemoteException e) {
			logger.severe("Failed to connect to remote jAgent instance - JRemoteException : " + e.getMessage());
			throw new ConnectivityException("Failed to connect to remote jAgent instance - JRemoteException : " + e.getMessage());
		}
	}
	
	/** Following method will load the user SMS settings to get correct data. At this stage there is no way
	 *  in T24 to load User SMS without calling VALIDATE.SIGN.ON routine. Once we have functionality available
	 *  we will replace it with correct routine.
	 * 
	 * @param ivConnection		- Actual JConnection for which we have to load User SMS
	 * @param responseDetails	- Populate the object if anything goes wrong 
	 * 
	 */
	private void loadUserSMS(JConnection ivConnection, ResponseDetails responseDetails) throws SubroutineCalledFailedException, ConnectivityException {
		String scheme,context,password,response; 
		try {
			if ( getServiceCallBack().getUserDetails() instanceof SSOUserDetails) {
				logMessage("Authentication scheme is use : SSO");
				// Only set the Context and T24 handle the rest by verifying the user profile
				context = ((SSOUserDetails)getServiceCallBack().getUserDetails()).getPrinciple();
				password = "";
				logMessage("T24 principle : " + context);
			} else {
				logMessage("Authentication scheme is use : SSO");
				// This means we are using normal authentication, set username and password
				context = ((T24UserDetails)getServiceCallBack().getUserDetails()).getUser();
				password = ((T24UserDetails)getServiceCallBack().getUserDetails()).getPassword();
				logMessage("T24 SIGN.ON.NAME/Password : " + context + "/******");
			}
		} catch (NullPointerException e) {
			response = "T24 User Details are not available. Unable to switch context.";
			responseDetails.addError(new Response("EB-USER.DETAILS.MISSING", Response.RESPONSE_TYPE_FATAL_ERROR, response, null));
			return;
		}
		
		// Now prepare and call the routine to load User SMS
		JSubroutineParameters params = new JSubroutineParameters();
		params.add(new JDynArray(context));
		params.add(new JDynArray(password));
		params.add(new JDynArray());
		
		params = processRequest(SERVER_LOAD_USER_SMS, params, ivConnection);
		
		JDynArray responsejDyn = (JDynArray) params.get(2);
		response = responsejDyn.get(1);
		if (! response.equals("")) {
			responseDetails.addError(new Response("EB-SECURITY.VIOLATION", Response.RESPONSE_TYPE_NON_FATAL_ERROR, response, null));
		}
	}
	
	/** Process the actual request
	 * 
	 * @param routine 	- Actual routine name
	 * @param params 	- Parameters to call the above sub routine with
	 * @return			- JSubroutineParameters type object which will be parsed later
	 */
	private JSubroutineParameters processRequest (String routine, JSubroutineParameters params, JConnection ivConnection) throws SubroutineCalledFailedException, ConnectivityException {

		JSubroutineParameters returnParams = new JSubroutineParameters();
		try {
			returnParams = jcaHelper.processRequest(ivConnection, routine, params);
		} catch (JSubroutineNotFoundException e) {
			logger.severe("Subroutine call failed  - JSubroutineNotFoundException : " + e.getMessage());
			throw new SubroutineCalledFailedException("Subroutine call failed  - JSubroutineNotFoundException : "  + e.getMessage());
		} catch (JRemoteException e) {
			logger.severe("Failed to connect to remote jAgent instance - JRemoteException : " + e.getMessage());
			throw new ConnectivityException("Failed to connect to remote jAgent instance - JRemoteException : " + e.getMessage());
		}
		return returnParams;
	}
	
	/** Close the connection to release the application server resources
	 * 
	 */
	private void closeConnection(JConnection ivConnection) throws ConnectivityException {
		try {
			logMessage("Closing connection");
			jcaHelper.closeConnection(ivConnection);
			logMessage("Connection closed");
		} catch (JRemoteException e){
			logger.severe("Unable to close JRemoteconnection - JRemoteException : " + e.getMessage());
			throw new ConnectivityException();
		}
	}
	
	/**************** T24 Service Call Back *********************/
	public void setServiceCallBack (T24ServiceCallBack serviceCallBack) {
		this.serviceCallBack = serviceCallBack;
	}

	public T24ServiceCallBack getServiceCallBack () {
		return this.serviceCallBack;
	}

	/**
	 * Logging fine messages
	 */
	private void logMessage(String message) {
		logger.fine(message);
	}
	
}
