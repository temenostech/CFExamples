package com.temenos.services.ofsconnector;

// Axis and Spring imports
import org.apache.axis2.engine.ServiceLifeCycle;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisService;

public class OFSConnectorServiceSpringInit implements ServiceLifeCycle {
       
	/**
     * This will be called during the deployement time of the service. 
     * irrespective
     * of the service scope this method will be called
     */
    public void startUp(ConfigurationContext ignore, AxisService service) {
        try {
			OFSConnectorServiceSpringContext.loadServiceContext(service.getClassLoader());
        } catch (Exception ex) {
			ex.printStackTrace();
        }
    }
    
    /**
     * This will be called during the system shut down time. 
     * irrespective
     * of the service scope this method will be called
     */
    public void shutDown(ConfigurationContext ctxIgnore, AxisService ignore) {
    	// Don't do anything for now
    }
}
