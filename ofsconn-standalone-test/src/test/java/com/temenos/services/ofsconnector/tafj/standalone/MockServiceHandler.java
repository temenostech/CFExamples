package com.temenos.services.ofsconnector.tafj.standalone;

import com.temenos.soa.services.UserContextCallBack;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.tafj.TAFJDefaultServiceHandlerImpl;
import com.temenos.soa.services.tafj.TAFJServiceCallBack;
import com.temenos.tafj.api.TAFJRuntimeImpl;

/**
 * Extend the Default Handler but we need to initialise TAFJ with properties, so we didn't implement initialise method
 * @author sjunejo
 *
 */


public class MockServiceHandler extends TAFJDefaultServiceHandlerImpl {

	public void clearUserContext(TAFJRuntimeImpl arg0, ResponseDetails arg1) {
		// TODO Auto-generated method stub
		
	}

	public boolean finalise(TAFJRuntimeImpl arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	public void loadUserContext(TAFJRuntimeImpl arg0, UserContextCallBack arg1,
			ResponseDetails arg2) {
		// TODO Auto-generated method stub
		
	}

	public void registerServiceCallBack(TAFJServiceCallBack arg0) {
		// TODO Auto-generated method stub
		
	}

	public void unregisterAllServiceCallback() {
		// TODO Auto-generated method stub
		
	}

	public void unregisterServiceCallBack(TAFJServiceCallBack arg0) {
		// TODO Auto-generated method stub
		
	}

}
