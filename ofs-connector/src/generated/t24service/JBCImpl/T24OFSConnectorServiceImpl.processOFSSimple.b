*------------------------------------------------------------------------------
* 
*------------------------------------------------------------------------------
    SUBROUTINE T24OFSConnectorServiceImpl.processOFSSimple(ofsRequest, ofsResponse, responseDetails)
*------------------------------------------------------------------------------
** Generated Service Adaptor 
* @author youremail@temenos.com
* @stereotype subroutine
* @package infra.eb
*!
* In/out parameters:  
* ofsRequest - String, IN
* ofsResponse - OFSConnResponse (Single), OUT
* responseDetails - ResponseDetails, OUT
*
*------------------------------------------------------------------------------
    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_responseDetails
    $INSERT I_OFSConnectorService_OFSConnResponse

*------------------------------------------------------------------------------
    GOSUB initialise
    IF ETEXT THEN
    	RETURN
    END
    GOSUB process
    GOSUB finalise

    RETURN
*------------------------------------------------------------------------------
process:

******************* FILL IN YOUR CODE HERE ***********************************
    * Attributes in the outward class OFSConnResponse:
    *   ofsResponse<OFSConnResponse.ofsResponse> = 

*
    RETURN

*------------------------------------------------------------------------------
initialise:
*
    ofsResponse = ''

    CALL SetServiceCommon
    response = ''
    responseDetails = '' 
    responseDetails.serviceName = "T24OFSConnectorServiceImpl.processOFSSimple"

	ETEXT = ''
    
    
*
    RETURN

*------------------------------------------------------------------------------
finalise:
*
    CALL SetServiceResponse(responseDetails)  
*
    RETURN
    
*------------------------------------------------------------------------------

    END
