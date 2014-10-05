sandbox-ofsconnectorpoc
=======================
These projects are to provide example usage for invoking T24 Enterprise components to communicate with T24 hosted with Temenos Application Framework C/J

Navigate to the PROJECT_HOME (directory where you have loaded the Git repository) and setup build and run environment by executing the bat file (Please update the TAFJ_MB_DIR in the file according to your machine path)
<pre>
	$ dev
</pre>

## EB_OFSConnector

This is T24 component to wrap OFS.BULK.MANAGER calls into component framework. This component should be available in T24 environment by default

# jBoss Security Config

As few of the following samples will be using secure component api's, you will be required to configured jBoss for the same to provide the security domain, users etc. 
Note: We will be using jboss default login modules to serve our need, but same can be achieved using industry standard LDAP server's, all you need to make sure that USER context and role is available.

Lets define a new jBoss Secutiry domain by adding following xml in 'JBOSS_HOME/server/&lt;profile&gt;/conf/login-config.xml' file;
<pre>
	<!-- T24 Application Login Module -->
	&lt;application-policy name="T24App"&gt;
		&lt;authentication&gt;
			&lt;login-module code="org.jboss.security.auth.spi.UsersRolesLoginModule" flag="required"&gt;
				&lt;module-option name="usersProperties"&gt;props/jbossws-users.properties&lt;/module-option&gt;
				&lt;module-option name="rolesProperties"&gt;props/jbossws-roles.properties&lt;/module-option&gt;
				&lt;module-option name="unauthenticatedIdentity"&gt;anonymous&lt;/module-option&gt;
			&lt;/login-module&gt;
		&lt;/authentication&gt;
	&lt;/application-policy&gt;
</pre>

Above configurations defines a new security domain in jboss as 'T24App' with user configuration file located at './props/jbossws-users.properties' and user rols configuration file located at './props/jbossws-roles.properties'

Now add a new user in jboss as 'SSOUSER' with password '123456' by adding following line in 'JBOSS_HOME/server/&lt;profile&gt;/conf/props/jbossws-users.properties';
<pre>
	SSOUSER=123456
</pre>

And define a role for the above user SSOUSER as 't24user' by adding the following line in 'JBOSS_HOME/server/&lt;profile&gt;/conf/props/jbossws-roles.properties';
<pre>
	SSOUSER=t24user
</pre>

NOTE: As you can see we have defined a jBoss user called SSOUSER, once authenticated 'SSOUSER' will be our PRINCIPAL and T24 needs to load the context for this user to execute the request. For that we need to define user
profile within T24 as SIGN.ON.NAME = SSOUSER (password can be antyhing for this user as it will be ignored during JF.VALIDATE.SIGN.ON) and one of the ATTRIBUTES of this user profile must be set to 'PREAUTHENTICATED'.

# TAFJ Only Samples

Make sure Databse configured with TAFJ MB is up and running

[ofsconn-standalone-test](ofsconn-standalone-test/README.md)

[ofsconn-standalone-provider-test](ofsconn-standalone-provider-test/README.md)
	
[ofsconn-appsrv-provider-test](ofsconn-appsrv-provider-test/README.md)

[ofsconn-standalone-proxy-tafj-test](ofsconn-standalone-proxy-tafj-test/README.md)

# TAFC Only Samples

Make sure tafc_agent is up and running correctly

[ofsconn-standalone-provider-tafc-test](ofsconn-standalone-provider-tafc-test/README.md)

[ofsconn-standalone-provider-tafc-customCXManager-test](ofsconn-standalone-provider-tafc-customCXManager-test/README.md)


# TAFC & TAFJ Samples [Applicable for Both]

[ofsconn-standalone-ejb-test](ofsconn-standalone-ejb-test/README.md)

[cf-wsjavaclient](cf-wsjavaclient/README.md)

[cf-wsjavaclientDynamic](cf-wsjavaclientDynamic/README.md)

[ofsconn-appserv-ejb-test](ofsconn-appserv-ejb-test/README/md)

