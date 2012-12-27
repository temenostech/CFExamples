package com.temenos.custom.cxmanager;

import java.util.Properties;

import com.jbase.jremote.DefaultJConnectionFactory;
import com.jbase.jremote.JConnection;
import com.jbase.jremote.JRemoteException;
import com.temenos.soa.services.tafc.DefaultJCAHelper;

public class CustomJCAHelper extends DefaultJCAHelper {
	private static final long serialVersionUID = -3755932185807535721L;
	private String _host = "localhost";
	private int _port = 20002;
	private String _ofsSource = "GCS";
	private String _uid = null;
	private String _pass = null;
	
	public CustomJCAHelper (String host, int port, String ofsSource, String uid, String pass) {
		_host = host;
		_port = port;
		_ofsSource = ofsSource;
		_uid = uid;
		_pass = pass;		
	}
	
	public CustomJCAHelper () {
	}
	
	// We just need to override the way we get Connection
	@Override
	public JConnection getConnection() throws JRemoteException {
		
		DefaultJConnectionFactory jConnectionFactory = new DefaultJConnectionFactory();
        jConnectionFactory.setHost(_host);
        jConnectionFactory.setPort(_port);
        
        Properties properties = new Properties();
        properties.put("env.OFS_SOURCE", _ofsSource); 
        JConnection jConnection = jConnectionFactory.getConnection( _uid , _pass , properties);
        
        // Initialise the connection
        jConnection.call("JF.INITIALISE.CONNECTION", null);
        return jConnection;
	}
}
