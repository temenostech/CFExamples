*-----------------------------------------------------------------------------
      SUBROUTINE OFSConnectorService.processOFS(ofsRequest, ofsSourceId, ofsResponse)      
*-----------------------------------------------------------------------------
*
* Note : This subroutine is auto-generated and should not be changed.
*
* @author Auto-generated
* @stereotype subroutine
* @package infra.eb
*!
*-----------------------------------------------------------------------------
 
    $INSERT I_OFSConnectorService_OFSConnResponse

*-----------------------------------------------------------------------------

      GOSUB initialise
      GOSUB process
      GOSUB finalise
      
      RETURN
*-----------------------------------------------------------------------------
process:

	CALL GetProxyInterface("OFSConnectorService", "processOFS", proxyAdaptorInterfaceName)
	IF proxyAdaptorInterfaceName EQ '' THEN
		CALL T24OFSConnectorServiceImpl.processOFS(ofsRequest, ofsSourceId, ofsResponse, responseDetails)
	END ELSE
		CALL @proxyAdaptorInterfaceName(ofsRequest, ofsSourceId, ofsResponse, responseDetails)
	END 
      
	RETURN
*-----------------------------------------------------------------------------
initialise:
* Routine which will handle and set up response as required
	responseHandlerName = ''
	
    ofsResponse = ''
    responseDetails = '' 

    RETURN

*-----------------------------------------------------------------------------
finalise:

    * Process responseDetails and return errors/overrides in the
    * standard T24 Common variables
    
    * Get the handler routine name for processing the response
    CALL GetServiceResponseHandler (responseHandlerName)
    
    * Now call the routine
    CALL @responseHandlerName (responseDetails)
   
    RETURN

END
