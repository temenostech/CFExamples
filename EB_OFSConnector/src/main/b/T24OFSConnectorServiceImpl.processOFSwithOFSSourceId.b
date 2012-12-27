*------------------------------------------------------------------------------
* 
*------------------------------------------------------------------------------
    SUBROUTINE T24OFSConnectorServiceImpl.processOFSwithOFSSourceId(ofsRequest, ofsSourceId, ofsResponse, responseDetails)
*------------------------------------------------------------------------------
** Generated Service Adaptor 
* @author youremail@temenos.com
* @stereotype subroutine
* @package infra.eb
*!
* In/out parameters:  
* ofsRequest - String, IN
* ofsSourceId - String, IN
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
	$INSERT I_SECCTX.COMMON     
     $INSERT I_PresentationServicesCommon
     $INSERT I_System
     $INSERT I_Tws
     $INSERT I_TwsCommon

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
	
	 GOSUB INITIALISE.OFS	 
	 GOSUB OTHER.INITIALISATION 
    
     CALL OFS.BULK.MANAGER(ofsRequest, ofsResponse, committed)
	 fields = ofsResponse[INDEX(ofsResponse, ',', 1)+1,9999]
     id = FIELD(ofsResponse, '/', 1)
	
* sometimes going through the bulk request brings bag request tags, remove these to just get the id

     IF INDEX(id,'<request>',1) THEN
        CHANGE '<request>' TO '' IN id
        CHANGE '<requests>' TO '' IN id
     END
    RETURN    
*---------------------------
INITIALISE.OFS:

     GTSACTIVE = 1
     GOSUB LOAD.SOURCE
     OFS.SOURCE.ID.BACKUP = OFS$SOURCE.ID
     OFS.SOURCE.REC.BACKUP = OFS$SOURCE.REC
*
     OFS.VERSION = '<tSS version="1.1">'
     OFS.VERSION := "<t24version>":R.SPF.SYSTEM<SPF.CURRENT.RELEASE>:"</t24version>"
     IF PID THEN
         OFS.VERSION := "<t24pid>":PID:"</t24pid>"
     END
     OFS.VERSION := "<t24ofssource>":OFS.SOURCE.ID:"</t24ofssource>"
     OFS.VERSION := "<clientIP/>"        ;* To announce to TCS that this version support the clientIP extraction
     OFS.VERSION := "</tSS>"

     RETURN
	 
	 
	  *** <region name= Other iniitialisation>
*** <desc>Clear the security context. Processing to load the TSA.SERVICE record has been removed. Maybe needs to go back to enable tracking of the service status</desc>
OTHER.INITIALISATION:

     SEC.CTX = ''    ;* Security context

     c$iAmNeo = System.getUiType()  ;
     c$supervisorApproved = ''  ;
     c$isTws = Tws.isTws()

     RETURN

 *** </region>

 
 *** <region name= Load OFS Source>
*** <desc>Load the ofs source. JAVA.FRAMEWORK is the default record although it can be set as an environment variable</desc>
LOAD.SOURCE:
	 OFS.SOURCE.ID = ofsSourceId
	 LOG.NAME=OFS.SOURCE.ID:"_":TIME()
     OFS.SOURCE.ID.SAVE = OFS.SOURCE.ID
     OFFLINE.FLAG = ''    
     CALL OFS.INITIALISE.SOURCE(OFS.SOURCE.ID, OFFLINE.FLAG, LOG.NAME)
     IF OFS.SOURCE.ID = '' THEN
         CRT "WARNING OFS.SOURCE ":OFS.SOURCE.ID.SAVE: " does not exist"
     END
     RETURN

 *** </region>
*------------------------------------------------------------------------------
initialise:
*
    ofsResponse = ''

    CALL SetServiceCommon
    response = ''
    responseDetails = '' 
    responseDetails.serviceName = "T24OFSConnectorServiceImpl.processOFSwithOFSSourceId"

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
