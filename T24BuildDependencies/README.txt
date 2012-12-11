######################################################
# T24 Enterprise Component Build System Instructions
######################################################
# IMPORTANT: Please refer to the complete guide of T24 
# Enterprise Build System present under 
# 'T24BuildDependencies/doc' directory.
#######################################################

Requirements
------------
Following softwares packages are mandatory to build T24 Enterprise components;

	Common
	------
	- Platform specific TAFC or TAFJ or both 
	- Java 1.6
	
	Windows
	-------
	- MS Visual Studio 2010 or Microsoft SDK v7.1 (with compiler). Please install according to your
	machine specifications i.e. 32-bit or 64-bit (recommended).
	
 	Unix & Linux
 	------------
 	- C++ compiler should be installed and available to use. 
 	
Instructions
------------
Following are instructions to build T24 Enterprise components on different platforms; 
 
 	Common Configurations - Load Workspace and Set Mandatory Environment Variables
 	------------------------------------------------------------------------------
 	- Load T24 components from RTC on your system. If successful, your repository workspace 
 	will have following projects loaded;
 		T24BuildDependencies
 		ST_BF/Source/Private/ST_Customer
 		IF/Source/Private/IF_IntegrationFlow
 		IF/Source/Private/IF_IntegrationFramework
 		IF/Source/Private/IF_IntegrationLandscape
 		...
 	 Note: If you have loaded your project as root folder i.e. on the same directory level as 
 	 	T24BuildDependencies project then you have to update build.xml for each	project 
		individually to point to correct T24BuildDependencies directory by updating the
		following property in 'build.xml';
 	 		<property name="build.dependencies.dir" value="${basedir}/../T24BuildDependencies" />
 	 	 	 	
 	- Set following environment variables;
		-> TAFC_HOME or TAFJ_HOME
			- Requires by build to use 'jcompile' and 'tCompile'.
			- If both are set, build will generate artifacts for both TAFC and TAFJ. 
			- If both were not set, build will fail.
			- If only TAFC_HOME is set, build will continue and build artifacts for TAFC only.
			- If only TAFJ_HOME is set, build will continue and build artifacts for TAFJ only.
		-> T24_HOME
			- Should be set to T24_BP directory as it will be used to retrieve includes.
			i.e. I_COMMON, I_EQUATE etc.
		-> JBCEMULATE
			- Must be set to 'prime' for TAFJ, TAFC is by default prime so not required.
				- This is to sync enterprise component with entire T24.
		-> JAVA_HOME
			- Should be set to jdk1.6+
		-> PATH
			- Add JAVA_HOME\bin, TAFC_HOME\bin and TAFJ_HOME\bin into PATH environment variable
 	
	Windows
	-------
	- Open VS2010 (x64 if available) command prompt which will load compiler environment for you.
	- Set the environment variables stated in 'Common Configuration' above.
	- Set following environment variables for Windows x64 only;
				set TAFC_EXCLUDE_UNDERSCORE_NAMES_FROM_DEF=1
	- To clean, build and release the artifacts, Navigate to your 'T24BuildDependencies' directory and apply following command;
		-> build.bat clean
			- This is to clean the previously build artifacts.
		-> build.bat release
			- This will build and release the aritfacts into centralised location
			- Release artifacts can be found under 'WORKSPACE_HOME\dist' directory
			- Logs for each component can be found under 'WORKSPACE_HOME\build\log' directory.
		-> build.bat ?
			- For all available build options.

	Unix & Linux
	------------
	- Set the environment variables stated in 'Common Configuration' above.
	- Please set following additional environment variables to load TAFC Libraries on UNIX platform
		-> export INCLUDE=$TAFC_HOME/include
		-> export LD_LIBRARY_PATH=$TAFC_HOME/lib:$LD_LIBRARY_PATH
		-> export LIBPATH=$TAFC_HOME/lib:$LIBPATH
		-> export SHLIB_PATH=$TAFC_HOME/LIB:$SHLIB_PATH
	- Make sure C++ compiler environment is set and available to use
	- To clean, build and release the artifacts, Navigate to your 'T24BuildDependencies' directory and apply following command;
		-> ./build.sh clean
			- This is to clean the previously build artifacts.
		-> ./build.sh release
			- This will build and release the artifacts into centralised location
			- Release artifacts can be found under 'WORKSPACE_HOME/dist' directory
			- Logs for each component can be found under 'WORKSPACE_HOME/build/log' directory.
		-> ./build.sh ?
			- For all available build options.