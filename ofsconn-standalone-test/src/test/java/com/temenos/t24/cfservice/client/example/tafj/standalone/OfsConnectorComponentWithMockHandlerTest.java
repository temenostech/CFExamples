package com.temenos.t24.cfservice.client.example.tafj.standalone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.temenos.services.ofsconnector.OFSConnectorServiceAPI;
import com.temenos.services.ofsconnector.OFSConnectorServiceImpl;
import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.soa.services.RuntimeProperties;
import com.temenos.soa.services.data.ResponseDetails;

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

public class OfsConnectorComponentWithMockHandlerTest {

	private OFSConnectorServiceAPI service = null;
	
	@Before
	public void setup() {
		RuntimeProperties properties = new RuntimeProperties("OFS_SOURCE", "GCS");
		// Here we going to mock the service handler, this can be useful to bypass T24 security, 
		// or add extend the service behaviour, . 
		service = new OFSConnectorServiceImpl(properties, new MockServiceHandler());
		// if you notice we are not providing any USER context to be loaded 
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
		assertTrue(ofsResponse.getOfsResponse().startsWith("200003/MSG-ID-1/1"));
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
		assertEquals("APPLICATION MISSING", ofsResponse.getOfsResponse());
	}
}
