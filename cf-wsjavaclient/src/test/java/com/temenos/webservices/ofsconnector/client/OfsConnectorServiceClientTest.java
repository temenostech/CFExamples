package com.temenos.webservices.ofsconnector.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;

import javax.xml.namespace.QName;

import ofsconnectorservicews.OFSConnectorServiceWS;
import ofsconnectorservicews.OFSConnectorServiceWSPortType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.temenos.services.ofsconnector.data.response.xsd.ProcessOFSResponse;
import com.temenos.services.ofsconnector.data.xsd.OFSConnResponse;
import com.temenos.soa.services.data.xsd.ResponseDetails;
import com.temenos.soa.services.data.xsd.T24UserDetails;

public class OfsConnectorServiceClientTest {

	OFSConnectorServiceWSPortType servicePort = null;
	com.temenos.soa.services.data.xsd.ObjectFactory objCommon = null;
	
	@Before
	public void setup() {
		try {
			OFSConnectorServiceWS service = new OFSConnectorServiceWS(
					new URL(Configurations.WSDL_URL), 
					new QName(Configurations.SRV_NAMESPCAE, Configurations.SRV_NAME));
			servicePort = service.getOFSConnectorServiceWSHttpSoap12Endpoint();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// Create an instance of an Soa Common Object Factory
		objCommon = new com.temenos.soa.services.data.xsd.ObjectFactory();
	}
	
	@After
	public void cleanup() {
		servicePort = null;
		objCommon = null;
	}
	
	@Test
	public void testOfsConnectiorServiceClientSuccess() {
		
		// Set User Details for an access
		T24UserDetails userDetails = objCommon.createT24UserDetails();
		userDetails.setUser(objCommon.createT24UserDetailsUser("INPUTT"));
		userDetails.setPassword(objCommon.createT24UserDetailsPassword("123456"));
		
		// Call the WS
		String ofsRequest = "ENQUIRY.SELECT,,INPUTT/123456,CURRENCY-LIST";
		ProcessOFSResponse response = servicePort.processOFS(userDetails, ofsRequest);

		// Parse the Response and ResponseDetails
		OFSConnResponse ofsResponse = response.getOfsResponse().getValue();
		ResponseDetails responseDetails =  response.getResponseDetails().getValue();
		
		// Debug
		System.out.println("Response : " + ofsResponse.getOfsResponse().getValue());
		System.out.println("Response Details : Return Code = " + responseDetails.getReturnCode().getValue());
		System.out.println("Response Details : Service Name = " + responseDetails.getServiceName().getValue());
		
		
		assertNotNull(ofsResponse.getOfsResponse().getValue());
		assertEquals("T24OFSConnectorServiceImpl.processOFS", responseDetails.getServiceName().getValue());
	}
}
