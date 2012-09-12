package com.temenos.services.ofsconnector;

// Spring imports
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** This class will be responsible to load and hold the application context for OFSConnectorService.
 *  This class can be called by OFSConnectorService standalone as well as WS clients to load the 
 *  bean definitions for use.
 */
  
public class OFSConnectorServiceSpringContext {
       
	// Application context will be loaded here
	private static ClassPathXmlApplicationContext appContext = null;
	
	// Default constructor
	public OFSConnectorServiceSpringContext() {}
	
	/** This method will load the application context xml file using provided context class loader
	 *
	 * @param classLoader		- Generic ClassLoader type object which can be of following context;
	 *								Thread 		- For an standalone clients
	 *								AxisService	- For WS clients
	 */
	public static void loadServiceContext(ClassLoader classLoader) throws Exception {
        appContext = new ClassPathXmlApplicationContext(new String[] {"oFSConnectorServiceContext.xml"}, false);
        appContext.setClassLoader(classLoader);
        appContext.refresh();
    }

	/** This method will be called to retrieve the application context.
	 *  
	 * @return ClassPathXmlApplicationContext	- Valid object if exist otherwise return 'null'
	 * @throws Exception						- Throws generic Exception to let user know that we have no context
	 */
	public static ClassPathXmlApplicationContext getContext() throws Exception {
		if (appContext != null) {
			return appContext;
		} else {
			// Now let calling class know that we have failed to load the context earlier
			throw new Exception("T24 OFSConnectorService Component initialisation failed.");
		}
    }
}
