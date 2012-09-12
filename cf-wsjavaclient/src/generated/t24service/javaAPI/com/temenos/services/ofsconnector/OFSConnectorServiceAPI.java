package com.temenos.services.ofsconnector;

// Soaframework import
import com.temenos.soa.services.T24ServiceCallBack;

/** This interface is the gateway to call T24 Impl within TAFJ system **/
public interface OFSConnectorServiceAPI extends OFSConnectorService {

	// Following are some extra functionality required by Java API which are not pure business functions.
	String getMetaData();
	void setServiceCallBack(T24ServiceCallBack serviceCallBack);
	T24ServiceCallBack getServiceCallBack();
}
