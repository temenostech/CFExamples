package com.temenos.services.ofsconnector;

// T24 ResponseDetails
import com.temenos.soa.services.data.ResponseDetails;

/** This interface is the gateway to call T24 Impl within TAFJ system **/
public interface OFSConnectorServiceProxyAPI extends OFSConnectorService {

	// Following are some extra functionality required by Java Proxy API which are not pure business functions.
	void setSecurityContext(String userId, ResponseDetails responseDetails);
}
