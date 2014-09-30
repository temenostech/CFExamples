package com.temenos.t24.cfservice.ofsconnector.prox.impl;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.temenos.soa.services.RuntimeProperties;
import com.temenos.soa.services.tafj.TAFJServiceInitialisationHandler;
import com.temenos.tafj.api.client.TAFJRuntime;
import com.temenos.tafj.api.client.jVarClient;
import com.temenos.tafj.api.client.impl.TAFJRuntimeFactory;
import com.temenos.tafj.api.client.impl.jVarClientFactory;

public class OFSConnectorProxyImplTests {

	private static TAFJRuntime runtime;

	@BeforeClass
	public static void setup() throws Exception {
		// Get the TAFJ Runtime
		runtime = TAFJRuntimeFactory.getTAFJRuntime();
		
		// Setup TAFJ Runtime
		runtime.setAppServerMode(false);
		runtime.init(false);
		runtime.setProperty("OFS_SOURCE", "GCS");
		
		// Set Precompile so that TAFJ can find few other T24 dependencies
		runtime.setProperty("temn.tafj.directory.precompile", System.getenv("PRECOMPILE"));
	}
	
	@AfterClass
	public static void tearDown() {
		runtime.destruct();
	}
	
	@Test
	public void testProcessOFSProxy() {
		// Initialize variables
		jVarClient requestStr = jVarClientFactory.get("ExtrernalOFSRequest");
		jVarClient response = jVarClientFactory.get();
		
		// Call the JBC AI
		runtime.callJBC("OFSConnectorService.processOFS", requestStr, response);
		
		// Verify the Response
		System.out.println("Proxy Output = " + response.toString());
		assertTrue(response.toString().equals("Proxy Impl Response of processOFS. Received = ExtrernalOFSRequest"));
	}
	
	@Test
	public void testProcessOFSwithOFSSourceIdProxy() {
		// Initialize variables
		jVarClient requestStr = jVarClientFactory.get("ExtrernalOFSRequest");
		jVarClient requestOFSSource = jVarClientFactory.get("ExternalOFSSource");
		jVarClient response = jVarClientFactory.get();
		
		// Call JBC API
		runtime.callJBC("OFSConnectorService.processOFSwithOFSSourceId", requestStr, requestOFSSource, response);
		
		// Verify Response
		System.out.println("Proxy Output = " + response.toString());
		assertTrue(response.toString().equals("Proxy Impl Resopnse of processOFSWithOFSSourceId . Received = ExtrernalOFSRequest and ExternalOFSSource"));
	}
}
