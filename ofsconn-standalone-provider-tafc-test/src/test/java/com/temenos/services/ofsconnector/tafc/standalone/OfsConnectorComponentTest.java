package com.temenos.services.ofsconnector.tafc.standalone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.support.GenericApplicationContext;

import com.temenos.services.ofsconnector.OFSConnectorServiceProviderAPI;
import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.services.ofsconnector.tafc.OFSConnectorServiceProviderImplTAFC;
import com.temenos.soa.services.T24UserContextCallBackImpl;
import com.temenos.soa.services.UserContextCallBack;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.SSOUserDetails;
import com.temenos.soa.services.data.T24UserDetails;

/**
 * Test methods to explain how to invoke the OFSConnector component provider APIs
 * from a Java client to access the implementations on a remote TAFC
 * environment using JENCKS container
 * 
 */
public class OfsConnectorComponentTest {

	private static GenericApplicationContext serviceContext;

	private static final String TAFC_AGENT_HOST = "localhost";
	private static final String TAFC_AGENT_PORT = "20002";
	private static final String T24_OFS_SOURCE = "GCS";

	@BeforeClass
	public static void setUp() {
		initialiseTafcServiceContext();
	}

	@Test
	public void testProcessOFSForValidEnquiry() {
		// Get the context
		OFSConnectorServiceProviderAPI service = (OFSConnectorServiceProviderAPI) serviceContext.getBean("OFSConnectorService");
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
		// Get the context
		OFSConnectorServiceProviderAPI service = (OFSConnectorServiceProviderAPI) serviceContext.getBean("OFSConnectorService");
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
		// Get the context
		OFSConnectorServiceProviderAPI service = (OFSConnectorServiceProviderAPI) serviceContext.getBean("OFSConnectorService");
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

	private static void initialiseTafcServiceContext() {
		JencksJcaContainer jcaContainer = new JencksJcaContainer(
				TAFC_AGENT_HOST, TAFC_AGENT_PORT, T24_OFS_SOURCE);
		jcaContainer.load();
		serviceContext = new GenericApplicationContext();
		BeanDefinitionBuilder tafcProvider = BeanDefinitionBuilder
				.rootBeanDefinition(OFSConnectorServiceProviderImplTAFC.class);
		serviceContext.registerBeanDefinition("OFSConnectorService", tafcProvider
				.getBeanDefinition());
	}

	@AfterClass
	public static void tearDown() {
		serviceContext = null;
	}
}
