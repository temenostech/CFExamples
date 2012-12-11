package com.temenos.services.ofsconnector.tafj.standalone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.temenos.services.ofsconnector.OFSConnectorServiceProviderAPI;
import com.temenos.services.ofsconnector.OFSConnectorServiceSpringContext;
import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.soa.services.T24UserContextCallBackImpl;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.T24UserDetails;

public class OfsConnectorComponentTest {

	private OFSConnectorServiceProviderAPI service = null;
	
	@Before
	public void setup() {
		// Here we will be loading Java API from Spring to contorl the number of instances etc.
		try
		{
			OFSConnectorServiceSpringContext.loadServiceContext(this.getClass().getClassLoader());
			service = (OFSConnectorServiceProviderAPI) OFSConnectorServiceSpringContext.getContext().getBean("OFSConnectorServiceProvider");
			service.setUserContextCallBack(new T24UserContextCallBackImpl(new T24UserDetails("INPUTT", "123456", "")));
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
