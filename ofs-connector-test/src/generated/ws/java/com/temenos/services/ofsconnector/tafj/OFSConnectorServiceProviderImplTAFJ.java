package com.temenos.services.ofsconnector.tafj;

// Java imports
import java.util.logging.Logger;

// Java spring related imports
import org.springframework.aop.target.CommonsPoolTargetSource;
import org.springframework.beans.BeansException;
import com.temenos.services.ofsconnector.OFSConnectorServiceSpringContext;

// Complex Data classes
import com.temenos.services.ofsconnector.data.OFSConnResponse;

// Soaframework imports
import com.temenos.soa.services.T24ServiceCallBack;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.Response;

// The Service and Provider INTERFACE
import com.temenos.services.ofsconnector.OFSConnectorServiceAPI;
import com.temenos.services.ofsconnector.OFSConnectorServiceProviderAPI;

public class OFSConnectorServiceProviderImplTAFJ implements OFSConnectorServiceProviderAPI {

	private final static Logger logger = Logger.getLogger(OFSConnectorServiceProviderImplTAFJ.class.getName());
	private CommonsPoolTargetSource oFSConnectorServicePool;
	private T24ServiceCallBack serviceCallBack = null;
	
	// Default constructor - Initialise Java API Pool
	public OFSConnectorServiceProviderImplTAFJ() {
		try {
			oFSConnectorServicePool = (CommonsPoolTargetSource) OFSConnectorServiceSpringContext.getContext().getBean("OFSConnectorServiceAPIPool");
		} catch (BeansException e) {
			String responseDetailsResponseText = "'OFSConnectorService' API Pool is currently not availble OR does not exist - BeansException : " + e.getMessage();
			logger.severe (responseDetailsResponseText);
			e.printStackTrace();
		} catch (Exception e) {
			String responseDetailsResponseText = "Failed to load 'OFSConnectorService' context - Exception : " + e.getMessage();
			logger.severe (responseDetailsResponseText);
			e.printStackTrace();
		}
	}
	
	@Override
    public void processOFS(String ofsRequest, String ofsSourceId, OFSConnResponse ofsResponse, ResponseDetails responseDetails) {
		OFSConnectorServiceAPI oFSConnectorServiceImpl = null;
		// Get the object from pool
		try {
			oFSConnectorServiceImpl = (OFSConnectorServiceAPI) oFSConnectorServicePool.getTarget();
		} catch (RuntimeException e) {
			String responseDetailsResponseText = "RuntimeException : " + e.getMessage();
			responseDetails.addError(new Response("EB-JAVA.API.INITIALISATION.ERROR", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} catch (Exception e) {
			String responseDetailsResponseText = "'OFSConnectorService' is currently not availble OR does not exist - Exception : " + e.getMessage();
			responseDetails.addError(new Response("EB-JAVA.API.NOT.AVAILABLE", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} 
		
		if ( ! (responseDetails.getReturnCode().equals(ResponseDetails.RETURN_CODE_FAILURE)) && oFSConnectorServiceImpl != null) {
			
			// Set the call back for user context
			oFSConnectorServiceImpl.setServiceCallBack(getServiceCallBack());
			
			// Now call the operation
			oFSConnectorServiceImpl.processOFS(ofsRequest, ofsSourceId, ofsResponse, responseDetails);
			
			// Now put the object back in pool for others to use
			try {
				oFSConnectorServicePool.releaseTarget(oFSConnectorServiceImpl);
			} catch (Exception e) {
				String responseDetailsResponseText = "Failed to release JAVA API resource of service 'OFSConnectorService' after use - Exception : " + e.getMessage();
				responseDetails.addError(new Response("EB-JAVA.API.RELEASE.ERROR", Response.RESPONSE_TYPE_WARNING, responseDetailsResponseText, null));
			}
		}
	}
	@Override
    public void processOFSSimple(String ofsRequest, OFSConnResponse ofsResponse, ResponseDetails responseDetails) {
		OFSConnectorServiceAPI oFSConnectorServiceImpl = null;
		// Get the object from pool
		try {
			oFSConnectorServiceImpl = (OFSConnectorServiceAPI) oFSConnectorServicePool.getTarget();
		} catch (RuntimeException e) {
			String responseDetailsResponseText = "RuntimeException : " + e.getMessage();
			responseDetails.addError(new Response("EB-JAVA.API.INITIALISATION.ERROR", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} catch (Exception e) {
			String responseDetailsResponseText = "'OFSConnectorService' is currently not availble OR does not exist - Exception : " + e.getMessage();
			responseDetails.addError(new Response("EB-JAVA.API.NOT.AVAILABLE", Response.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, null));
		} 
		
		if ( ! (responseDetails.getReturnCode().equals(ResponseDetails.RETURN_CODE_FAILURE)) && oFSConnectorServiceImpl != null) {
			
			// Set the call back for user context
			oFSConnectorServiceImpl.setServiceCallBack(getServiceCallBack());
			
			// Now call the operation
			oFSConnectorServiceImpl.processOFSSimple(ofsRequest, ofsResponse, responseDetails);
			
			// Now put the object back in pool for others to use
			try {
				oFSConnectorServicePool.releaseTarget(oFSConnectorServiceImpl);
			} catch (Exception e) {
				String responseDetailsResponseText = "Failed to release JAVA API resource of service 'OFSConnectorService' after use - Exception : " + e.getMessage();
				responseDetails.addError(new Response("EB-JAVA.API.RELEASE.ERROR", Response.RESPONSE_TYPE_WARNING, responseDetailsResponseText, null));
			}
		}
	}

	/**************** T24 Service Call Back *********************/
	public void setServiceCallBack (T24ServiceCallBack serviceCallBack) {
		this.serviceCallBack = serviceCallBack;
	}

	public T24ServiceCallBack getServiceCallBack () {
		return this.serviceCallBack;
	}
}

