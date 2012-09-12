package com.temenos.services.ofsconnector;

// TAJF System Classes
import com.temenos.tafj.common.jSession;
import com.temenos.soa.utils.JVarUtils;
import com.temenos.tafj.common.jVar;
import com.temenos.tafj.common.jVarFactory;
import com.temenos.tafj.common.TAFJSystem;

// Complex Data classes and its Converters
import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.services.ofsconnector.data.converter.OFSConnResponseConverter;

// TAFJ Names of the routine we need to call
import com.temenos.t24.T24OFSConnectorServiceImpl_processOFS_24_cl;
import com.temenos.t24.T24OFSConnectorServiceImpl_processOFSSimple_29_cl;
import com.temenos.t24.OFSConnectorService_getMetaData_23_cl;

// Java imports
import java.util.logging.Logger;
import java.util.Iterator;
import java.util.Map;
import java.sql.SQLException;

// Generic Soaframework imports
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.Response;
import com.temenos.soa.services.data.converter.ResponseDetailsConverter;
import com.temenos.soa.services.T24ServiceCallBack;
import com.temenos.soa.services.tafj.TAFJT24SessionHandler;
import com.temenos.soa.services.tafj.TAFJSessionProperties;

// Exception classes
import com.temenos.soa.exceptions.*;

public class OFSConnectorServiceImpl implements OFSConnectorServiceAPI {
	private final static Logger logger = Logger.getLogger(OFSConnectorServiceImpl.class.getName());
	private jSession session;
	private T24ServiceCallBack serviceCallBack;
	private TAFJT24SessionHandler sessionHandler;
	private TAFJSessionProperties properties;

	// Default Constructor
	public OFSConnectorServiceImpl(TAFJSessionProperties properties) throws RuntimeException {
		
		// Set the properties object
		this.properties = properties;
		
		// Initialse jSession
		String argsStr[] = new String[] {};
		TAFJSystem.init(argsStr, "");
		session = new jSession();
		
		// Set the properties to jSession
		setSessionProperties();
		
		// Create and object and Initailse global T24 common variables
		sessionHandler = new TAFJT24SessionHandler(session);
		try {
			sessionHandler.initialise();
		} catch (Throwable t) {
			logger.severe ("Failed to initialse OFSConnectorService - Message : " + t.getMessage());
			throw new RuntimeException("Internal TAFJ error - Cause : " + 
				t.getCause().toString() + " - LocalizedMessage : " + 
				t.getLocalizedMessage() + " - Message : " + t.getMessage());
		}
	}
	
	// For testing only
	public OFSConnectorServiceImpl(String testStr) {
		// Initialse jSession
		String argsStr[] = new String[] {};
		TAFJSystem.init(argsStr, "");
		session = new jSession();
	}
	
	
	@Override
	public String getMetaData() {
		jVar jVarServiceMetaData = jVarFactory.get();
		
		OFSConnectorService_getMetaData_23_cl.INSTANCE(session).invoke(jVarServiceMetaData);
		
		return jVarServiceMetaData.toString();
	}
	
	    @Override
    public void processOFS(String ofsRequest, String ofsSourceId, OFSConnResponse ofsResponse, ResponseDetails responseDetails) {

        JVarUtils utils = new JVarUtils();
        
		boolean requestCheck = false;
		try {
		    jVar jVarOfsRequest = utils.toJVar(ofsRequest);
		    jVar jVarOfsSourceId = utils.toJVar(ofsSourceId);
		    OFSConnResponseConverter ofsResponseConverterObj = new OFSConnResponseConverter();
		    jVar jVarOfsResponse = ofsResponseConverterObj.toJVar(ofsResponse);
            // ResponseDetails is an out parameters only so no need to do a toJVar
            jVar jVarResponseDetails = jVarFactory.get();
        
		    requestCheck = true;	// If we reach to this point, this mean request is converted successfully
			
			// Before we call an actual operation, load user SMS details for this request
			loadUserSMS(responseDetails);
			if ( responseDetails.getReturnCode().equals(ResponseDetails.RETURN_CODE_FAILURE) ) {
				return ;
			}
			
			// Call the actaul routine for processing
			T24OFSConnectorServiceImpl_processOFS_24_cl.INSTANCE(session).invoke(jVarOfsRequest, jVarOfsSourceId, jVarOfsResponse,  jVarResponseDetails);
			
            ofsResponseConverterObj.fromJVar(ofsResponse, jVarOfsResponse );

	        ResponseDetailsConverter responseDetailsConverterObj = new ResponseDetailsConverter();
            responseDetailsConverterObj.fromJVar(responseDetails, jVarResponseDetails);

	    } catch (InvalidNestLevelException e) {
        	String parameterFailed;		// Just to figure it out where actually we getting the Conversion Failure
			String code;
        	if (! requestCheck) {
        		parameterFailed = "Input";
				code = "EB-INVALID.REQUEST";
        	} else {
        		parameterFailed = "Output";
				code = "EB-INVALID.RESPONSE";
        	}
        	String responseDetailsResponseText = "Failed to convert '" + parameterFailed + "' parameters for T24 - InvalidNestLevelException : " + e.getMessage();
        	logger.severe (responseDetailsResponseText);
            responseDetails.addError(new Response(code, Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
        } catch (Throwable t) {
			String responseDetailsResponseText = "Internal TAFJ error - Cause : " + t.getCause() + " - Message : " + t.getMessage() + " - LocalizedMessage : " + t.getLocalizedMessage();
			logger.severe (responseDetailsResponseText);
			responseDetails.addError(new Response("EB-OPERATION.INVOCATION.ERROR", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} finally {
			try {
				// For DB's other than jBase we have to close the connection explicitly - DBMS
    			if (session.getDB().getConductor().getConnection() != null) {
    				session.getDB().getConductor().getConnection().close();
    			} else {
    				// Do nothing. This is jBase DB
    			}
			} catch (SQLException e) {
				String responseDetailsResponseText = "Error closing wrapped JDBC connection - SQLException : " + e.getMessage(); 
				logger.severe (responseDetailsResponseText);
				responseDetails.addError(new Response("EB-DB.CONNECTION.CLOSE.ERROR", Response.RESPONSE_TYPE_WARNING, responseDetailsResponseText, null));
			}
		}
    }
    @Override
    public void processOFSSimple(String ofsRequest, OFSConnResponse ofsResponse, ResponseDetails responseDetails) {

        JVarUtils utils = new JVarUtils();
        
		boolean requestCheck = false;
		try {
		    jVar jVarOfsRequest = utils.toJVar(ofsRequest);
		    OFSConnResponseConverter ofsResponseConverterObj = new OFSConnResponseConverter();
		    jVar jVarOfsResponse = ofsResponseConverterObj.toJVar(ofsResponse);
            // ResponseDetails is an out parameters only so no need to do a toJVar
            jVar jVarResponseDetails = jVarFactory.get();
        
		    requestCheck = true;	// If we reach to this point, this mean request is converted successfully
			
			// Before we call an actual operation, load user SMS details for this request
			loadUserSMS(responseDetails);
			if ( responseDetails.getReturnCode().equals(ResponseDetails.RETURN_CODE_FAILURE) ) {
				return ;
			}
			
			// Call the actaul routine for processing
			T24OFSConnectorServiceImpl_processOFSSimple_29_cl.INSTANCE(session).invoke(jVarOfsRequest, jVarOfsResponse,  jVarResponseDetails);
			
            ofsResponseConverterObj.fromJVar(ofsResponse, jVarOfsResponse );

	        ResponseDetailsConverter responseDetailsConverterObj = new ResponseDetailsConverter();
            responseDetailsConverterObj.fromJVar(responseDetails, jVarResponseDetails);

	    } catch (InvalidNestLevelException e) {
        	String parameterFailed;		// Just to figure it out where actually we getting the Conversion Failure
			String code;
        	if (! requestCheck) {
        		parameterFailed = "Input";
				code = "EB-INVALID.REQUEST";
        	} else {
        		parameterFailed = "Output";
				code = "EB-INVALID.RESPONSE";
        	}
        	String responseDetailsResponseText = "Failed to convert '" + parameterFailed + "' parameters for T24 - InvalidNestLevelException : " + e.getMessage();
        	logger.severe (responseDetailsResponseText);
            responseDetails.addError(new Response(code, Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
        } catch (Throwable t) {
			String responseDetailsResponseText = "Internal TAFJ error - Cause : " + t.getCause() + " - Message : " + t.getMessage() + " - LocalizedMessage : " + t.getLocalizedMessage();
			logger.severe (responseDetailsResponseText);
			responseDetails.addError(new Response("EB-OPERATION.INVOCATION.ERROR", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} finally {
			try {
				// For DB's other than jBase we have to close the connection explicitly - DBMS
    			if (session.getDB().getConductor().getConnection() != null) {
    				session.getDB().getConductor().getConnection().close();
    			} else {
    				// Do nothing. This is jBase DB
    			}
			} catch (SQLException e) {
				String responseDetailsResponseText = "Error closing wrapped JDBC connection - SQLException : " + e.getMessage(); 
				logger.severe (responseDetailsResponseText);
				responseDetails.addError(new Response("EB-DB.CONNECTION.CLOSE.ERROR", Response.RESPONSE_TYPE_WARNING, responseDetailsResponseText, null));
			}
		}
    }

	// Set the jSession properties, if there are no properties set by user then throw an exception as we can not continue
	private void setSessionProperties() throws RuntimeException {
		// Check if there are any properties set by user
		if (properties.getSessionProperties().size() > 0) {
    		Iterator it = properties.getSessionProperties().entrySet().iterator();
    		while (it.hasNext()) {
    	        Map.Entry pairs = (Map.Entry)it.next();
    			session.setProperty((String)pairs.getKey(), (String)pairs.getValue());
    	    }
		} else {
			throw new RuntimeException(
			"OFS_SOURCE property is mendatory to instatiate session. Properties are defined under " +
				"'oFSConnectorServiceContext.xml' file present under t24-OFSConnectorService-jws.aar archive. " +
				"Request Aborted!!!");
		}
	}

	// Set/Switch security context for current operation
	private void loadUserSMS(ResponseDetails responseDetails) {
		try {
			sessionHandler.loadUserSMS(getServiceCallBack().getUserDetails(), responseDetails);
		} catch (NullPointerException e) {
			String response = "T24 User Details are not available. Unable to switch context.";
			responseDetails.addError(new Response("EB-USER.DETAILS.MISSING", Response.RESPONSE_TYPE_FATAL_ERROR, response, null));
		}
	}

	/*************** Service Call Back ****************/
	public void setServiceCallBack(T24ServiceCallBack serviceCallBack) {
		this.serviceCallBack = serviceCallBack;
	}
	
	public T24ServiceCallBack getServiceCallBack() {
		return this.serviceCallBack;
	}

}
