package com.temenos.services.ofsconnector.tafj.standalone;

import org.springframework.context.support.ClassPathXmlApplicationContext;

  
public class CustomSpringContext {
       
	// Application context will be loaded here
	private static ClassPathXmlApplicationContext appContext = null;
	
	// Default constructor
	public CustomSpringContext() {}

	public static void loadContext(){
		appContext = new ClassPathXmlApplicationContext(new String[] {"customContext.xml"}, false);
        appContext.setClassLoader(CustomSpringContext.class.getClassLoader());
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
			throw new Exception("T24 TComponent Component initialisation failed.");
		}
    }
}
