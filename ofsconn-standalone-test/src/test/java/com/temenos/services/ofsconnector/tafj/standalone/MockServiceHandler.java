package com.temenos.services.ofsconnector.tafj.standalone;

import com.temenos.soa.services.UserContextCallBack;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.tafj.TAFJDefaultServiceHandlerImpl;
import com.temenos.tafj.api.TAFJRuntimeImpl;

/**
 * Extend the Default Handler to by pass the T24 security for now
 * We can use rest of the handler functionalities i.e. TAFJ initialise, finalise etc
 * @author sjunejo
 *
 */


public class MockServiceHandler extends TAFJDefaultServiceHandlerImpl {

	@Override
	public void clearUserContext(TAFJRuntimeImpl arg0, ResponseDetails arg1) {
		// Do nothing and just return
	}

	@Override
	public void loadUserContext(TAFJRuntimeImpl arg0, UserContextCallBack arg1,
			ResponseDetails arg2) {
		// Let it go
	}
}
