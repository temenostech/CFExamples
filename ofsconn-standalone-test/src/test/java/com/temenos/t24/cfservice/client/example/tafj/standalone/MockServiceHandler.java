package com.temenos.t24.cfservice.client.example.tafj.standalone;

import com.temenos.soa.services.UserContextCallBack;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.tafj.TAFJDefaultServiceHandlerImpl;
import com.temenos.tafj.api.client.TAFJRuntime;

/**
 * Extend the Default Handler to by pass the T24 security for now
 * We can use rest of the handler functionalities i.e. TAFJ initialise, finalise etc
 * @author sjunejo
 *
 */


public class MockServiceHandler extends TAFJDefaultServiceHandlerImpl {

	@Override
	public void clearUserContext(TAFJRuntime arg0, ResponseDetails arg1) {
		// Do nothing and just return
	}

	@Override
	public void loadUserContext(TAFJRuntime arg0, UserContextCallBack arg1,
			ResponseDetails arg2) {
		// Let it go
	}
}
