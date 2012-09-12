package com.sandbox.poc;

import com.temenos.soa.services.T24ServiceCallBack;
import com.temenos.soa.services.data.T24UserDetails;
import com.temenos.soa.services.data.UserDetails;

public class MockServiceCallback implements T24ServiceCallBack {

	@Override
	public UserDetails getUserDetails() {
		return new T24UserDetails("INPUTT","123456","");
	}

	@Override
	public void setUserDetails(UserDetails arg0) {
		// do nothing
	}
}
