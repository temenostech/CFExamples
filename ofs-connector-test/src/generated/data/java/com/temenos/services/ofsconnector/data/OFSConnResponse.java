package com.temenos.services.ofsconnector.data;

// Collection - Imports
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class OFSConnResponse {

    private String ofsResponse;

// Constructors
	
	public OFSConnResponse(String ofsResponse) {
		init();		// Initialise and then assign		
        this.ofsResponse = ofsResponse;
    }

    public OFSConnResponse() {
		init();
    }
	
	// Initialise members
	private void init() {
		this.ofsResponse = "";
	}
// Getters

    public String getOfsResponse(){
        return ofsResponse;
    }

//Setters

    public void setOfsResponse(String newOfsResponse){
	    ofsResponse = newOfsResponse;
    }
}
