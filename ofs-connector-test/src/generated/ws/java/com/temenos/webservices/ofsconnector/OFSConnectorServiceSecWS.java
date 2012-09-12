package com.temenos.webservices.ofsconnector;

// Axis2 & Spring imports
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.springframework.beans.BeansException;


import com.temenos.services.ofsconnector.data.OFSConnResponse;

// SoaFramework Libararies
import com.temenos.soa.services.T24ServiceCallBack;
import com.temenos.soa.services.T24ServiceCallBackImpl;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.Response;
import com.temenos.soa.services.data.SSOUserDetails;

// T24 Provider API Interface
import com.temenos.services.ofsconnector.OFSConnectorServiceProviderAPI;
import com.temenos.services.ofsconnector.OFSConnectorServiceSpringContext;

@WebService(name = "OFSConnectorServiceSecWS", serviceName="OFSConnectorServiceSecWS")
public class OFSConnectorServiceSecWS {

    @WebMethod
    public ProcessOFSResponse processOFS(String ofsRequest, String ofsSourceId) {

 	    OFSConnResponse ofsResponse =  new OFSConnResponse();
	    // Create Return parameters for the Web Service
        ProcessOFSResponse result = new ProcessOFSResponse();
        ResponseDetails responseDetails = new ResponseDetails();

		// Get the Provider Service Impl, verify the response and proceed
		OFSConnectorServiceProviderAPI service = getService(responseDetails);
	    if ( ! responseDetails.getReturnCode().equals(ResponseDetails.RETURN_CODE_SUCCESS) ) {
			result.setResponseDetails(responseDetails);
			return result;
		}
		
		// Create a new service call back object and pass it to provider with context
		try {
			SSOUserDetails userDetails = new SSOUserDetails(OFSConnectorServiceSecWSRequestContext.getRequestContext().getUserPrincipal());
			T24ServiceCallBack serviceCallBack = new T24ServiceCallBackImpl(userDetails);
			service.setServiceCallBack(serviceCallBack);
		} catch (Exception e) {
			String message = "Failed to retrieve user context - Exception : " + e.getMessage();
			responseDetails.addError(new Response("EB-USER.CONTEXT.NOT.AVAILABLE", Response.RESPONSE_TYPE_FATAL_ERROR, message, null));
		}
		
		// Verify responseDetails and proceed if evrything is OK
		if ( responseDetails.getReturnCode().equals(ResponseDetails.RETURN_CODE_SUCCESS) ) {
            service.processOFS(ofsRequest, ofsSourceId, ofsResponse, responseDetails);
                
            // Set-up return parameter with INOUT/OUT parameters
			result.setOfsResponse(ofsResponse);
        } 
        
        result.setResponseDetails(responseDetails);
        return result;
    }   
    @WebMethod
    public ProcessOFSSimpleResponse processOFSSimple(String ofsRequest) {

 	    OFSConnResponse ofsResponse =  new OFSConnResponse();
	    // Create Return parameters for the Web Service
        ProcessOFSSimpleResponse result = new ProcessOFSSimpleResponse();
        ResponseDetails responseDetails = new ResponseDetails();

		// Get the Provider Service Impl, verify the response and proceed
		OFSConnectorServiceProviderAPI service = getService(responseDetails);
	    if ( ! responseDetails.getReturnCode().equals(ResponseDetails.RETURN_CODE_SUCCESS) ) {
			result.setResponseDetails(responseDetails);
			return result;
		}
		
		// Create a new service call back object and pass it to provider with context
		try {
			SSOUserDetails userDetails = new SSOUserDetails(OFSConnectorServiceSecWSRequestContext.getRequestContext().getUserPrincipal());
			T24ServiceCallBack serviceCallBack = new T24ServiceCallBackImpl(userDetails);
			service.setServiceCallBack(serviceCallBack);
		} catch (Exception e) {
			String message = "Failed to retrieve user context - Exception : " + e.getMessage();
			responseDetails.addError(new Response("EB-USER.CONTEXT.NOT.AVAILABLE", Response.RESPONSE_TYPE_FATAL_ERROR, message, null));
		}
		
		// Verify responseDetails and proceed if evrything is OK
		if ( responseDetails.getReturnCode().equals(ResponseDetails.RETURN_CODE_SUCCESS) ) {
            service.processOFSSimple(ofsRequest, ofsResponse, responseDetails);
                
            // Set-up return parameter with INOUT/OUT parameters
			result.setOfsResponse(ofsResponse);
        } 
        
        result.setResponseDetails(responseDetails);
        return result;
    }   
	/** Following method will load the Provider Service Impl from Application Context
	 *  as Bean and retrun the object if found otherwise return null.
	 *
	 * @param responseDetails
	 * 				Will be populated if failed to load the bean
	 * @return OFSConnectorServiceProviderAPI object
	 */
	private OFSConnectorServiceProviderAPI getService(ResponseDetails responseDetails) {
		try {
			return ( (OFSConnectorServiceProviderAPI) OFSConnectorServiceSpringContext.getContext().getBean("OFSConnectorServiceProvider") );
		}  catch (BeansException e) {
			String message = "'OFSConnectorService' Provider is currently not availble OR does not exist - BeansException : " + e.getMessage();
			responseDetails.addError(new Response("EB-SERVICE.NOT.AVAILABLE", Response.RESPONSE_TYPE_FATAL_ERROR, message, null));
		} catch (Exception e) {
			String message = "Failed to load 'OFSConnectorService' context - Exception : " + e.getMessage();
			responseDetails.addError(new Response("EB-SERVICE.NOT.AVAILABLE", Response.RESPONSE_TYPE_FATAL_ERROR, message, null));
		}
		// if we come to this point this means Bean is not loaded, return null
		return null;
	}
}
