package com.temenos.t24.cfservice.ofsconnector.prox.impl;

import com.temenos.services.ofsconnector.OFSConnectorServiceProxyAPI;
import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.soa.services.UserContextCallBack;
import com.temenos.soa.services.data.CFConstants;
import com.temenos.soa.services.data.ResponseDetails;

public class OFSConnectorProxyImpl implements OFSConnectorServiceProxyAPI {

	@Override
	public void processOFS(String arg0, OFSConnResponse arg1,
			ResponseDetails arg2) {
		arg1.setOfsResponse("Proxy Impl Response of processOFS. Received = " + arg0);
	}

	@Override
	public void processOFSwithOFSSourceId(String arg0, String arg1,
			OFSConnResponse arg2, ResponseDetails arg3) {
		arg2.setOfsResponse("Proxy Impl Resopnse of processOFSWithOFSSourceId . Received = " + arg0 + " and " + arg1);
	}

	@Override
	public void setSecurityContext(UserContextCallBack arg0) {
		// TODO Auto-generated method stub

	}

}
