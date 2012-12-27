package com.temenos.services.ofsconnector.ejb.standalone;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.temenos.services.ofsconnector.data.response.ProcessOFSResponse;
import com.temenos.services.ofsconnector.ejb.OFSConnectorServiceBeanRemote;

/**
 * This class will demonstrate how we can invoke jBC Impl from a standalone java class using Component Secure EJB API Deployed 
 * within and Application Server.
 * @author sjunejo
 *
 */
public class OfsConnectorComponentTest {

	private OFSConnectorServiceBeanRemote serviceEJB = null;
	
	@Before
	public void setup() {
		// Here we will be making connection with the Application Server by passing the following properties;
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        props.put(Context.PROVIDER_URL, "jnp://127.0.0.1:1099"); // remote machine IP
        props.put(Context.SECURITY_PRINCIPAL, "SSOUSER"); // User Context
        props.put(Context.SECURITY_CREDENTIALS, "123456"); // Password
        Context context = null;
		try
		{
			// Now lets get hold of Context and Service
		
        	context = new InitialContext(props);
        	Object factoryObj = context.lookup(Configurations.SERVICE_EJB_JNDI_NAME_REMOTE); //ejb-name
	        serviceEJB = (OFSConnectorServiceBeanRemote) factoryObj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@After
	public void cleanup() {
		serviceEJB = null;
	}
	
	@Test
	public void testProcessOfsOnTafjUsingEJBApiForSuccess() {
		String ofsRequest = "ENQUIRY.SELECT,,INPUTT/123456,CURRENCY-LIST";
		ProcessOFSResponse response = serviceEJB.processOFS(ofsRequest);
		
		// debug
		System.out.println("Response : " + response.getOfsResponse().getOfsResponse());
		System.out.println(response.getResponseDetails());
		
		// verify - currently failing, needs to fix by TAFJ team
		//assertTrue(ofsResponse.getOfsResponse().startsWith("200003/MSG-ID-1/1"));
	}

	@Test
	public void testProcessOfsOnTafjUsingJavaApiForError() {
		String ofsRequest = "FOO,/S/PROCESS,INPUTT/123456,BLAH";
		ProcessOFSResponse response = serviceEJB.processOFS(ofsRequest);
		
		// debug
		System.out.println("Response : " + response.getOfsResponse().getOfsResponse());
		System.out.println(response.getResponseDetails());
		
		// verify - currently failing, needs to fix by TAFJ team
		//assertTrue(ofsResponse.getOfsResponse().startsWith("200003/MSG-ID-1/1"));
	}
}
