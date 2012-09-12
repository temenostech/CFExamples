package com.temenos.webservices.ofsconnector;

import com.temenos.services.ofsconnector.data.OFSConnResponse;
import com.temenos.soa.services.data.ResponseDetails;


public class ProcessOFSSimpleResponse {

    OFSConnResponse ofsResponse;
    ResponseDetails responseDetails;


    public OFSConnResponse getOfsResponse() {
        return ofsResponse;
    }

    public void setOfsResponse(OFSConnResponse ofsResponse) {
        this.ofsResponse = ofsResponse;
    }
    
    public ResponseDetails getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(ResponseDetails responseDetails) {
        this.responseDetails = responseDetails;
    }
}
