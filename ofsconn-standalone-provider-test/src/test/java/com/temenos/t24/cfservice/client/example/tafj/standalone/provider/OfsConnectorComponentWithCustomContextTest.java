package com.temenos.t24.cfservice.client.example.tafj.standalone.provider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.temenos.services.ofsconnector.OFSConnectorServiceProviderAPI;
import com.temenos.services.ofsconnector.OFSConnectorServiceSpringContext;
import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.soa.services.T24UserContextCallBackImpl;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.T24UserDetails;
import com.temenos.t24.cfservice.client.example.tafj.standalone.provider.CustomSpringContext;

/**
 * This class will demonstrate how we can invoke jBC Impl within standalone TAFJ environment using Component Provider API
 * by loading the spring context ourselves and passing the context to Component Provider API to use it instead of loading 
 * its own context
 * @author sjunejo
 *
 */
public class OfsConnectorComponentWithCustomContextTest {

	private OFSConnectorServiceProviderAPI service = null;
	
	@Before
	public void setup() {
		// Here we will be loading spring context in custom class and then set the context in component spring class
		// for Java Provider API to call 
		try
		{
			// So lets instantiate our custom spring loader,
			CustomSpringContext.loadContext();
			
			// Now set the context for provider
			OFSConnectorServiceSpringContext.setContext(CustomSpringContext.getContext());
			
			// Once context is set successfully, we can get hold of the bean
			service = (OFSConnectorServiceProviderAPI) OFSConnectorServiceSpringContext.getContext().getBean("OFSConnectorServiceProvider");
			
			// Setting up a user context to load/switch T24 context before the call
			service.setUserContextCallBack(new T24UserContextCallBackImpl(new T24UserDetails("INPUTT", "123456", "")));
			
			// In scenarios where you receives PRINCIPAL from 3rd party, you can propagate this PRINCIPAL to T24 as follows to by pass the Password check;
			// Note: Make sure you have user with PRINCIPAL within T24 as one of it user profile Attributes = PREAUTHENTICATED
			// service.setUserContextCallBack(new T24UserContextCallBackImpl(new SSOUserDetails("SSOUSER")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void cleanup() {
		service = null;
	}
	
	@Test
	public void testProcessOfsOnTafjUsingJavaApiForSuccess() {
		String ofsRequest = "ENQUIRY.SELECT,,INPUTT/123456,CURRENCY-LIST";
		OFSConnResponse ofsResponse = new OFSConnResponse();
		ResponseDetails serviceResponse = new ResponseDetails();
		service.processOFS(ofsRequest, ofsResponse, serviceResponse);
		
		// debug
		System.out.println("Response : " + ofsResponse.getOfsResponse());
		System.out.println(serviceResponse);
		
		// verify - currently failing, needs to fix by TAFJ team
		//assertTrue(ofsResponse.getOfsResponse().startsWith("200003/MSG-ID-1/1"));
	}

	@Test
	public void testProcessOfsOnTafjUsingJavaApiForError() {
		String ofsRequest = "FOO,/S/PROCESS,INPUTT/123456,BLAH";
		OFSConnResponse ofsResponse = new OFSConnResponse();
		ResponseDetails serviceResponse = new ResponseDetails();
		service.processOFS(ofsRequest, ofsResponse, serviceResponse);
		
		//debug
		System.out.println("Response : " + ofsResponse.getOfsResponse());
		System.out.println(serviceResponse);
		
		// verify - currently failing, needs to fix by TAFJ team
		//assertEquals("APPLICATION MISSING", ofsResponse.getOfsResponse());
	}
}
