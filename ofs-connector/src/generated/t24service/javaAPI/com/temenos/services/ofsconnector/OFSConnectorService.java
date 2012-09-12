package com.temenos.services.ofsconnector;

// Complex Classes
import com.temenos.services.ofsconnector.data.OFSConnResponse;

// T24 ResponseDetails
import com.temenos.soa.services.data.ResponseDetails;

// Java Utils

/** This interface will define core T24 Business operation and will be extended by all other
 *  implementation say Java API, Java Proxy etc to make sure we have common interface.
 */
public interface OFSConnectorService {

	// Operations from service model
    void processOFS(String ofsRequest, String ofsSourceId, OFSConnResponse ofsResponse, ResponseDetails responseDetails);		
    void processOFSSimple(String ofsRequest, OFSConnResponse ofsResponse, ResponseDetails responseDetails);		

}
