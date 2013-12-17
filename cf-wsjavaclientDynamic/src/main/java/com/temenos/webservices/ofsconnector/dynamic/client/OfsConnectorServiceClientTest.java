package com.temenos.webservices.ofsconnector.dynamic.client;

import java.net.URL;

import javax.xml.namespace.QName;

import ofsconnectorservicews.OFSConnectorServiceWS;
import ofsconnectorservicews.OFSConnectorServiceWSPortType;

import com.temenos.services.ofsconnector.data.response.xsd.ProcessOFSResponse;
import com.temenos.services.ofsconnector.data.xsd.OFSConnResponse;
import com.temenos.soa.services.data.xsd.Response;
import com.temenos.soa.services.data.xsd.ResponseDetails;
import com.temenos.soa.services.data.xsd.T24UserDetails;

public class OfsConnectorServiceClientTest {

	public static void main (String[] args) {
		testOfsConnectiorServiceClientSuccess();
	}
	
	public static void testOfsConnectiorServiceClientSuccess() {
		OFSConnectorServiceWSPortType servicePort = null;
		try {
			OFSConnectorServiceWS service = new OFSConnectorServiceWS(
					new URL(Configurations.WSDL_URL), 
					new QName(Configurations.SRV_NAMESPCAE, Configurations.SRV_NAME));
			servicePort = service.getOFSConnectorServiceWSHttpSoap12Endpoint();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		com.temenos.soa.services.data.xsd.ObjectFactory objCommon = new com.temenos.soa.services.data.xsd.ObjectFactory();
		
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
		
		// Print the Response 
		System.out.println("Response Receieved");
		System.out.println("==================");
		
		System.out.println("Output					: " + ofsResponse.getOfsResponse().getValue());
		System.out.println("Response Details - Response (size)	: " + responseDetails.getResponses().size());
		if (responseDetails.getResponses().size() > 0) {
			System.out.println("	List Of Responses");
			System.out.println("	-----------------");
			int i = 1;
			for (Response res : responseDetails.getResponses()) {
				System.out.println("	" + i + " - Response Code : " + res.getResponseCode().getValue());
				System.out.println("	" + i + " - Response Type : " + res.getResponseType().getValue());
				System.out.println("	" + i + " - Response Text : " + res.getResponseText().getValue());
				System.out.println("	" + i + " - Response Info : " + res.getResponseInfo().getValue());
				i++;
			}
		}
		System.out.println("Response Details - Return Code		: " + responseDetails.getReturnCode().getValue());	
		System.out.println("Response Details - Subroutine		: " + responseDetails.getServiceName().getValue());
	}
}
