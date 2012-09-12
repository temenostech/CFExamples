*------------------------------------------------------------------------------
* 
*------------------------------------------------------------------------------
     SUBROUTINE T24OFSConnectorServiceImpl.processOFS(ofsRequest, ofsSourceId, ofsResponse, responseDetails)
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
	$INSERT I_GTS.COMMON
	$INSERT I_F.OFS.SOURCE
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


	 OFS.SOURCE.ID = ofsSourceId
	 LOG.NAME=OFS.SOURCE.ID:"_":TIME()
     OFS.SOURCE.ID.SAVE = OFS.SOURCE.ID
     OFFLINE.FLAG = ''
     CALL OFS.INITIALISE.SOURCE(OFS.SOURCE.ID, OFFLINE.FLAG, LOG.NAME)
	 
     IF OFS.SOURCE.ID = '' THEN
         CRT "WARNING OFS.SOURCE ":OFS.SOURCE.ID.SAVE: " does not exist"
     END
	 
     CALL OFS.BULK.MANAGER(ofsRequest, ofsResponse, committed)
	 fields = ofsResponse[INDEX(ofsResponse, ',', 1)+1,9999]
     id = FIELD(ofsResponse, '/', 1)
	
* sometimes going through the bulk request brings bag request tags, remove these to just get the id

     IF INDEX(id,'<request>',1) THEN
        CHANGE '<request>' TO '' IN id
        CHANGE '<requests>' TO '' IN id
     END
    RETURN    

*------------------------------------------------------------------------------
initialise:
*
    ofsResponse = ''

    CALL SetServiceCommon
    response = ''
    responseDetails = '' 
    responseDetails.serviceName = "T24OFSConnectorServiceImpl.processOFS"

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
