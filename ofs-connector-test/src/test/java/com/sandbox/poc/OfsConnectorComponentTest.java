package com.sandbox.poc;

import static org.junit.Assert.*;
import org.junit.Test;
import com.temenos.soa.services.data.Response;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.services.ofsconnector.OFSConnectorService;
import com.temenos.services.ofsconnector.OFSConnectorServiceImpl;
import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.soa.services.tafj.TAFJSessionProperties;

public class OfsConnectorComponentTest {

	@Test
	public void testProcessOfsOnTafjUsingJavaApiForSuccess() {
		OFSConnectorService service = getService();
		
		String ofsRequest = "CUSTOMER,/S/PROCESS,INPUTT/123456,200003/MSG-ID-1";
		OFSConnResponse ofsResponse = new OFSConnResponse();
		ResponseDetails serviceResponse = new ResponseDetails();
		service.processOFSSimple(ofsRequest, ofsResponse, serviceResponse);
		
		// debug
		printResponse(ofsResponse, serviceResponse);
		
		// verify
		assertTrue(ofsResponse.getOfsResponse().startsWith("200003/MSG-ID-1/1"));
	}

	@Test
	public void testProcessOfsOnTafjUsingJavaApiForError() {
		OFSConnectorService service = getService(); 
		String ofsRequest = "FOO,/S/PROCESS,INPUTT/123456,BLAH";
		OFSConnResponse ofsResponse = new OFSConnResponse();
		ResponseDetails serviceResponse = new ResponseDetails();
		service.processOFSSimple(ofsRequest, ofsResponse, serviceResponse);
		
		//debug
		printResponse(ofsResponse, serviceResponse);
		
		// verify
		assertEquals("APPLICATION MISSING", ofsResponse.getOfsResponse());
	}

	@Test
	public void testProcessOfsOnTafjUsingWebserviceApiForSuccess() {
		// TODO
	}
	
	@Test
	public void testProcessOfsOnTafjUsingWebserviceApiForError() {
		// TODO
	}
	
	private OFSConnectorService getService(){
		TAFJSessionProperties properties = new TAFJSessionProperties();
		properties.addSessionProperty("OFS_SOURCE", "GCS");
		OFSConnectorServiceImpl service = new OFSConnectorServiceImpl(
				properties);
		service.setServiceCallBack(new MockServiceCallback());
		return service;
	}

	private void printResponse(OFSConnResponse ofsResponse,
			ResponseDetails serviceResponse) {
		// build response string
		StringBuffer buffer = new StringBuffer();
		buffer.append("RESPONSE:\n");
		if (ResponseDetails.RETURN_CODE_SUCCESS.equals(serviceResponse
				.getReturnCode())) {
			buffer.append(ofsResponse.getOfsResponse());
		} else {
			for (Response response : serviceResponse.getResponses()) {
				buffer.append("Response code [" + response.getResponseCode()
						+ "] Response Text [" + response.getResponseText()
						+ "] Response Info [" + response.getResponseInfo()
						+ "]");
			}
		}
		// print out
		System.out
				.println("\n--------------------------------------------------------------");
		System.out.println(buffer.toString());
		System.out
				.println("--------------------------------------------------------------");
	}
}
