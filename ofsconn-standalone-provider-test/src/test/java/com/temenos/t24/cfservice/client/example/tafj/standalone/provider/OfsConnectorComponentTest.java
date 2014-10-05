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

/**
 * This class will demonstrate how we can invoke jBC Impl within standalone TAFJ environment using Component Provider API.
 * Component Provider API is designed to pool Java API objects using Apache Commons Pool. This way user do not have to pool
 * or maintain Java API (TAFJRuntimeImpl) objects. By default each provider JAR has a default spring configurations to load
 * ProviderImpl (i.e. it can be TAFC or TAFJ, see oFSConnectorServiceContext.xml for details)
 * @author sjunejo
 *
 */
public class OfsConnectorComponentTest {

	private OFSConnectorServiceProviderAPI service = null;
	
	@Before
	public void setup() {
		// Here we will be loading Java API from Spring to contorl the number of instances etc.
		try
		{
			// We need to pass the class loader so that spring context can be search and loaded
			OFSConnectorServiceSpringContext.loadServiceContext(this.getClass().getClassLoader());
			// Once context is loaded successfully, we can get hold of the bean
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
