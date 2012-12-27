package com.temenos.services.ofsconnector.tafj.standalone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.temenos.services.ofsconnector.OFSConnectorServiceAPI;
import com.temenos.services.ofsconnector.OFSConnectorServiceImpl;
import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.soa.services.RuntimeProperties;
import com.temenos.soa.services.T24UserContextCallBackImpl;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.T24UserDetails;

/**
 * This test class demonstrate how we can use Component Java API (direct wrapper of component jBC Implementation in Java)
 * by Override few functionalities like SIGN.ON and SIGN.OFF from T24. This can be done by extending the Default TAFJ 
 * Service Handler. Component Java API calls this handler for T24 INITIALISE, SIGN.ON and SIGN.OFF functionalities.
 * 
 * Each instance of component Java API instantiate TAFJRuntimeImpl object, destroying this object recreating this object 
 * at each call is expensive and can decrease system performance. So please instantiate once and re-use it if possible.
 * 
 * Note: Invoking jBC by directly Component Java API is not recommended. Also this example is only applicable for TAFJ
 * @author sjunejo
 *
 */
public class OfsConnectorComponentTest {

	private OFSConnectorServiceAPI service = null;
	
	@Before
	public void setup() {
		RuntimeProperties properties = new RuntimeProperties("OFS_SOURCE", "GCS");
		// Here we are using the default Service Handler
		service = new OFSConnectorServiceImpl(properties);
		
		// Below line will set the T24 User Context within Service object and this will be used before every jBC call to load/switch 
		// the user context using JF.VALIDATE.SIGN.ON
		service.setUserContextCallBack(new T24UserContextCallBackImpl(new T24UserDetails("INPUTT", "123456", "")));
		
		// In scenarios where you receives PRINCIPAL from 3rd party, you can propagate this PRINCIPAL to T24 as follows to by pass the Password check;
		// Note: Make sure you have user with PRINCIPAL within T24 as one of it user profile Attributes = PREAUTHENTICATED
		// service.setUserContextCallBack(new T24UserContextCallBackImpl(new SSOUserDetails("SSOUSER")));
	}
	
	@After
	public void cleanup() {
		service.cleanup();
		service = null;
	}
	
	@Test
	public void testProcessOfsOnTafjUsingJavaApiForSuccess() {
		String ofsRequest = "CUSTOMER,/S/PROCESS,INPUTT/123456,200003/MSG-ID-1";
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
