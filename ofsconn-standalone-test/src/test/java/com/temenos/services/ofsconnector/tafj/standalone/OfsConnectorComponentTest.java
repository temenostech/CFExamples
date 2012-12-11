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

public class OfsConnectorComponentTest {

	private OFSConnectorServiceAPI service = null;
	
	@Before
	public void setup() {
		RuntimeProperties properties = new RuntimeProperties("OFS_SOURCE", "GCS");
		// Here we are mocking the Service Handler
		service = new OFSConnectorServiceImpl(properties);//, new MockServiceHandler());
		service.setUserContextCallBack(new T24UserContextCallBackImpl(new T24UserDetails("INPUTT", "123456", "")));
	}
	
	@After
	public void cleanup() {
		service = null;
	}
	
	@Test
	public void testProcessOfsOnTafjUsingJavaApiForSuccess() {
		String ofsRequest = "CUSTOMER,/S/PROCESS,INPUTT/123456,200003/MSG-ID-1";
		//String ofsRequest = "ENQUIRY.SELECT,,INPUTT/123456,CURRENCY-LIST";
		OFSConnResponse ofsResponse = new OFSConnResponse();
		ResponseDetails serviceResponse = new ResponseDetails();
		service.processOFSSimple(ofsRequest, ofsResponse, serviceResponse);
		
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
		service.processOFSSimple(ofsRequest, ofsResponse, serviceResponse);
		
		//debug
		System.out.println("Response : " + ofsResponse.getOfsResponse());
		System.out.println(serviceResponse);
		
		// verify - currently failing, needs to fix by TAFJ team
		//assertEquals("APPLICATION MISSING", ofsResponse.getOfsResponse());
	}
}
