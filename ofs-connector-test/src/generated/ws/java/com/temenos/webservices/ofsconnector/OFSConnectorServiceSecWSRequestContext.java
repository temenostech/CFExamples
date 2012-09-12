package com.temenos.webservices.ofsconnector;

class OFSConnectorServiceSecWSRequestContext {
	private final static ThreadLocal<OFSConnectorServiceSecWSRequestContext> currentContext = new ThreadLocal<OFSConnectorServiceSecWSRequestContext>();

    public static void setRequestContext(OFSConnectorServiceSecWSRequestContext context) {
        currentContext.set(context);
    }

    public static OFSConnectorServiceSecWSRequestContext getRequestContext() {
        return currentContext.get();
    }

    public static void clearRequestContext() {
        currentContext.remove();
    }
    
    private String userPrincipal;
    
    public OFSConnectorServiceSecWSRequestContext(String principal) {
    	this.userPrincipal = principal;
    }
    
    public String getUserPrincipal(){
    	return this.userPrincipal;
    }
}
