package com.temenos.services.ofsconnector.tafc.standalone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.temenos.custom.cxmanager.CustomJCAHelper;
import com.temenos.services.ofsconnector.OFSConnectorServiceProviderAPI;
import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.services.ofsconnector.tafc.OFSConnectorServiceProviderImplTAFC;
import com.temenos.soa.services.T24UserContextCallBackImpl;
import com.temenos.soa.services.UserContextCallBack;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.SSOUserDetails;
import com.temenos.soa.services.data.T24UserDetails;
import com.temenos.soa.services.tafc.JCAHelper;
import com.temenos.soa.services.tafc.TAFCDefaultServiceHandlerImpl;
import com.temenos.soa.services.tafc.TAFCServiceHandler;

/**
 * Test methods to explain how to invoke the OFSConnector jBC Impl using Component provider APIs
 * from a Java client by providing a custom connection manager for remote T24 connectivity
 * 
 */
public class OfsConnectorComponentTest {

	JCAHelper customJCAHelper =  new CustomJCAHelper();
	OFSConnectorServiceProviderAPI service = null;
	
	@Before
	public void setUp() {
		// Here we will override the JCA Helper, Component API will try to retreive from ServiceHandler
		TAFCServiceHandler serviceHandler = new TAFCDefaultServiceHandlerImpl();
		serviceHandler.setJCAHelper(customJCAHelper);
		
		// And now instantiate the Component Provider API, this all can also be done in Spring but not now
		service = new OFSConnectorServiceProviderImplTAFC(serviceHandler);
	}
	
	@Test
	public void testProcessOFSForValidEnquiry() {
		// Set the user context
		UserContextCallBack userContext = new T24UserContextCallBackImpl(
				new T24UserDetails("INPUTT", "123456", ""));
		service.setUserContextCallBack(userContext);
		
		// Prepare a request and response
		OFSConnResponse response = new OFSConnResponse();
		ResponseDetails responseDetails = new ResponseDetails();
		String request = "ENQUIRY.SELECT,,INPUTT/123456,CURRENCY-LIST";
		
		// Call the service
		service.processOFS(request, response, responseDetails);
		
		// Debug
		System.out.println(response.getOfsResponse());
		System.out.println(responseDetails);
				
		// Verify the response
		assertNotNull(response.getOfsResponse());
		assertEquals("SUCCESS", responseDetails.getReturnCode());
	}
	
	// Followning test requires a 'PREAUTHENTICATED' USER within T24 with Sign On Name as 'SSOUSER'
	@Test
	public void testProcessOFSForValidEnquiryWithSSO() {
		// Set the user context
		UserContextCallBack userContext = new T24UserContextCallBackImpl(new SSOUserDetails("SSOUSER"));
		service.setUserContextCallBack(userContext);
		
		// Prepare a request and response
		OFSConnResponse response = new OFSConnResponse();
		ResponseDetails responseDetails = new ResponseDetails();
		String request = "ENQUIRY.SELECT,,INPUTT/123456,CURRENCY-LIST";
		
		// Call the service
		service.processOFS(request, response, responseDetails);

		// Debug
		System.out.println(response.getOfsResponse());
		System.out.println(responseDetails);
		
		// Verify the response
		assertNotNull(response.getOfsResponse());
		assertEquals("SUCCESS", responseDetails.getReturnCode());
	}

	@Test
	public void testProcessOFSForInvalidEnquiry() {
		// Set the user context
		UserContextCallBack userContext = new T24UserContextCallBackImpl(new T24UserDetails("INPUTT", "123456", ""));
		service.setUserContextCallBack(userContext);
		
		// Prepare a request and response
		OFSConnResponse response = new OFSConnResponse();
		ResponseDetails responseDetails = new ResponseDetails();
		String request = "ENQUIRY.SELECT,,INPUTT/123456,BLAHBLAH";
		
		// Call the service
		service.processOFS(request, response, responseDetails);
		
		System.out.println(response.getOfsResponse());
		System.out.println(responseDetails);
		
		// Verify the response
		assertNotNull(response.getOfsResponse());
		assertEquals("FAILURE", responseDetails.getReturnCode());
	}

	@After
	public void tearDown() {
		// do nothing
	}
}
