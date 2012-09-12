package com.temenos.services.ofsconnector;

// Soaframework Classes
import com.temenos.soa.services.T24ServiceCallBack;

// Service INTERFACE
import com.temenos.services.ofsconnector.OFSConnectorService;

public interface OFSConnectorServiceProviderAPI extends OFSConnectorService {

	// Extra operation which will be part of Provider Implementation
	void setServiceCallBack(T24ServiceCallBack serviceCallBack);
	T24ServiceCallBack getServiceCallBack();

}
