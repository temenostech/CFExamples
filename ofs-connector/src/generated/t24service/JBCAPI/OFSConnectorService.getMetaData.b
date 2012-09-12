*-----------------------------------------------------------------------------
      SUBROUTINE OFSConnectorService.getMetaData(metaDataXMLString)
      
*-----------------------------------------------------------------------------
*
* Note : This subroutine is auto-generated and should not be changed.
*
* @author Auto-generated
* @stereotype subroutine
*!
*-----------------------------------------------------------------------------
      GOSUB initialise      
      GOSUB process
      GOSUB finalise
      
      RETURN
*-----------------------------------------------------------------------------
process: 
      metaDataXMLString := '<?xml version = "1.0" encoding = "utf-8"?>'
      metaDataXMLString := '<service xmlns="http://www.temenos.com/SOA/Meta"'
      metaDataXMLString := '  name="OFSConnectorService" '
      metaDataXMLString := '  majorVersion="DEV" minorVersion="latest-dev" vendor="temenos">'

      metaDataXMLString := '<operations>'

      metaDataXMLString := '<operation name="processOFS"'
      metaDataXMLString := '  integrationFlowSupportable="false"'
	  metaDataXMLString := '  idempotent="false"'
	  metaDataXMLString := '  unsafe="false">'	  
      metaDataXMLString := '<parameter name="ofsRequest"'
      metaDataXMLString := '  typeName="String"'
      metaDataXMLString := '  isPrimitive="true"'
      metaDataXMLString := '  isCollection="false"'
      metaDataXMLString := '  isMandatory="false"'
      metaDataXMLString := '  direction="IN" />'
      metaDataXMLString := '<parameter name="ofsSourceId"'
      metaDataXMLString := '  typeName="String"'
      metaDataXMLString := '  isPrimitive="true"'
      metaDataXMLString := '  isCollection="false"'
      metaDataXMLString := '  isMandatory="false"'
      metaDataXMLString := '  direction="IN" />'
      metaDataXMLString := '<parameter name="ofsResponse"'
      metaDataXMLString := '  typeName="OFSConnResponse"'
      metaDataXMLString := '  isPrimitive="false"'
      metaDataXMLString := '  isCollection="false"'
      metaDataXMLString := '  isMandatory="false"'
      metaDataXMLString := '  direction="OUT" />'
      metaDataXMLString := '</operation>'

      metaDataXMLString := '<operation name="processOFSSimple"'
      metaDataXMLString := '  integrationFlowSupportable="false"'
	  metaDataXMLString := '  idempotent="false"'
	  metaDataXMLString := '  unsafe="false">'	  
      metaDataXMLString := '<parameter name="ofsRequest"'
      metaDataXMLString := '  typeName="String"'
      metaDataXMLString := '  isPrimitive="true"'
      metaDataXMLString := '  isCollection="false"'
      metaDataXMLString := '  isMandatory="false"'
      metaDataXMLString := '  direction="IN" />'
      metaDataXMLString := '<parameter name="ofsResponse"'
      metaDataXMLString := '  typeName="OFSConnResponse"'
      metaDataXMLString := '  isPrimitive="false"'
      metaDataXMLString := '  isCollection="false"'
      metaDataXMLString := '  isMandatory="false"'
      metaDataXMLString := '  direction="OUT" />'
      metaDataXMLString := '</operation>'
      
      metaDataXMLString := '</operations>'
			
			
      metaDataXMLString := '<customizedTypes>'

      metaDataXMLString := '<customizedType name="OFSConnResponse">'
      metaDataXMLString := '<member name="ofsResponse"'
      metaDataXMLString := '  typeName="String"'
      metaDataXMLString := '  isPrimitive="true"'
      metaDataXMLString := '  isCollection="false"/>'
      metaDataXMLString := '</customizedType>'

      metaDataXMLString := '</customizedTypes>'	

      metaDataXMLString := '</service>'

      
      RETURN
*-----------------------------------------------------------------------------
initialise:
    metaDataXMLString = ''
		
    RETURN

*-----------------------------------------------------------------------------
finalise:    

    RETURN

END
