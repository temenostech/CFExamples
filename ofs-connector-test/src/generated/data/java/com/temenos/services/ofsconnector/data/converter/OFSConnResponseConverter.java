package com.temenos.services.ofsconnector.data.converter;

// JVar [TAFJ] Imports
import com.temenos.soa.utils.JVarObject;
import com.temenos.soa.utils.JVarUtils;
import com.temenos.tafj.common.jVar;
import com.temenos.tafj.common.jVarFactory;

// JDynArray [TAFC] Imports
import com.jbase.jremote.JDynArray;
import com.temenos.soa.utils.JDynObject;
import com.temenos.soa.utils.JDynUtils;

// Base Data Class Imports
import com.temenos.services.ofsconnector.data.OFSConnResponse;

// Exceptions - Imports
import com.temenos.soa.exceptions.InvalidNestLevelException;

// Collection - Imports
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// Other Complex Classes - Imports (if any) involved

public class OFSConnResponseConverter implements JVarObject, JDynObject {
	
    final int OFSRESPONSE_INDEX = 1;

    private String ofsResponse;
	
	// Constructor 
	public OFSConnResponseConverter () {
		init();		// Initialise all the members
	}

	private void init() {
		this.ofsResponse = "";
	}
	
	// jVar Constructor
	public OFSConnResponseConverter (jVar jv) throws InvalidNestLevelException {
		init();
		this.fromJVar(jv);
	}

	// jDyn Constructor
	public OFSConnResponseConverter (JDynArray jd) throws InvalidNestLevelException {
		init();
		this.fromJDyn(jd);
	}
	
// Getters

    public String getOfsResponse(){
        return ofsResponse;
    }

//Setters

    public void setOfsResponse(String newOfsResponse){
        ofsResponse = newOfsResponse;
    }

	/**************** Data Class to Converter Class *****************/
	
    public void dataToConverter(OFSConnResponse source, OFSConnResponseConverter target) throws InvalidNestLevelException {
		target.setOfsResponse(source.getOfsResponse());
	}
	
    public void dataFromConverter(OFSConnResponseConverter source, OFSConnResponse target) throws InvalidNestLevelException {
		target.setOfsResponse(source.getOfsResponse());
	}

    // List Conversion    
     public void dataListToConverterList(List<OFSConnResponse> sourceList, List<OFSConnResponseConverter> targetList) throws InvalidNestLevelException {
    	for (int i=0 ; i < sourceList.size(); i++) {
    		OFSConnResponseConverter oFSConnResponseConverter = new OFSConnResponseConverter();
    		dataToConverter (sourceList.get(i), oFSConnResponseConverter);
    		targetList.add(oFSConnResponseConverter);
    	}
	}	
   
    public void dataListFromConverterList(List<OFSConnResponseConverter> sourceList, List<OFSConnResponse> targetList) throws InvalidNestLevelException {
    	for (int i=0 ; i < sourceList.size(); i++) {
    		OFSConnResponse oFSConnResponse = new OFSConnResponse();
    		dataFromConverter (sourceList.get(i), oFSConnResponse);
    		targetList.add(oFSConnResponse);
    	}
	}
    
    // Array Conversion
    public void dataArrayToConverterArray(OFSConnResponse[] sourceArray, OFSConnResponseConverter[] targetArray) throws InvalidNestLevelException {
    	for (int i=0 ; i < sourceArray.length; i++) {
    		OFSConnResponseConverter oFSConnResponseConverter = new OFSConnResponseConverter();
    		dataToConverter (sourceArray[i], oFSConnResponseConverter);
    		targetArray[i] = oFSConnResponseConverter;
    	}
   }
	
    public void dataArrayFromConverterArray(OFSConnResponseConverter[] sourceArray, OFSConnResponse[] targetArray) throws InvalidNestLevelException {
    	for (int i=0 ; i < sourceArray.length; i++) {
    		OFSConnResponse oFSConnResponse = new OFSConnResponse();
    		dataFromConverter (sourceArray[i], oFSConnResponse);
    		targetArray[i] = oFSConnResponse;
    	}
	}


	/**************** JVar Conversion ****************/
	
    @Override
    public void fromJVar(jVar jv) throws InvalidNestLevelException {
        JVarUtils utils = new JVarUtils();

        this.setOfsResponse(utils.getString(jv, OFSRESPONSE_INDEX));
    }

    public void fromJVar(OFSConnResponse oFSConnResponse, jVar jv) throws InvalidNestLevelException {
        
        JVarUtils utils = new JVarUtils();

        oFSConnResponse.setOfsResponse(utils.getString(jv, OFSRESPONSE_INDEX));
    }
	
	// List support for class
	public void fromJVar(List<OFSConnResponse> oFSConnResponseList, jVar jv) throws InvalidNestLevelException {
        
        JVarUtils utils = new JVarUtils();
        
        List<OFSConnResponseConverter> oFSConnResponseConverterList = new ArrayList<OFSConnResponseConverter>();
        utils.getJVarObjectList(jv, oFSConnResponseConverterList, new OFSConnResponseConverter() );
        dataListFromConverterList(oFSConnResponseConverterList, oFSConnResponseList);
    }
	
	// List support for class with Multivalue field
	public void fromJVar(List<OFSConnResponse> oFSConnResponseList, jVar jv, int fieldNum) throws InvalidNestLevelException {
        
        JVarUtils utils = new JVarUtils();
        
        List<OFSConnResponseConverter> oFSConnResponseConverterList = new ArrayList<OFSConnResponseConverter>();
        utils.getJVarObjectList(jv, fieldNum, oFSConnResponseConverterList, new OFSConnResponseConverter() );
        dataListFromConverterList(oFSConnResponseConverterList, oFSConnResponseList);
    }
	
	// Array support for class
	public void fromJVar(OFSConnResponse[] oFSConnResponseArray, jVar jv) throws InvalidNestLevelException {
        JVarUtils utils = new JVarUtils();
        OFSConnResponseConverter[] oFSConnResponseConverterArray = new OFSConnResponseConverter[utils.getNumberOfAttributes(jv)];
        utils.getJVarObjectArray(jv, oFSConnResponseConverterArray, new OFSConnResponseConverter() );
        dataArrayFromConverterArray(oFSConnResponseConverterArray, oFSConnResponseArray);
    }
	
	// Array support for class
	public void fromJVar(OFSConnResponse[] oFSConnResponseArray, jVar jv, int fieldNum) throws InvalidNestLevelException {
        JVarUtils utils = new JVarUtils();
        OFSConnResponseConverter[] oFSConnResponseConverterArray = new OFSConnResponseConverter[utils.getNumberOfValues(jv, fieldNum)];
        utils.getJVarObjectArray(jv, fieldNum, oFSConnResponseConverterArray, new OFSConnResponseConverter() );
        dataArrayFromConverterArray(oFSConnResponseConverterArray, oFSConnResponseArray);
    }
	
    @Override
    public jVar toJVar() throws InvalidNestLevelException {
		
        JVarUtils utils = new JVarUtils();
        jVar jv = jVarFactory.get();  //Initialise a new jVar

        utils.setValue(jv, OFSRESPONSE_INDEX, ofsResponse);

        return jv;
    }
	
	public jVar toJVar(OFSConnResponse oFSConnResponse) throws InvalidNestLevelException {

        JVarUtils utils = new JVarUtils();
        jVar jv = jVarFactory.get();  //Initialise a new jVar

        utils.setValue(jv, OFSRESPONSE_INDEX, oFSConnResponse.getOfsResponse());
        return jv;
    }
	
	// List support
	public jVar toJVar(List<OFSConnResponse> oFSConnResponseList) throws InvalidNestLevelException {

        JVarUtils utils = new JVarUtils();
        jVar jv = jVarFactory.get();  //Initialise a new jVar

        for (int i=0; i < oFSConnResponseList.size(); i++) {
        	OFSConnResponseConverter oFSConnResponseConverter = new OFSConnResponseConverter();
        	dataToConverter(oFSConnResponseList.get(i), oFSConnResponseConverter);
        	utils.setValue(jv, i+1, oFSConnResponseConverter);
        }
        
        return jv;
    }
	
	// Array support
	public jVar toJVar(OFSConnResponse[] oFSConnResponseArray) throws InvalidNestLevelException {

        JVarUtils utils = new JVarUtils();
        jVar jv = jVarFactory.get();  //Initialise a new jVar

        for (int i=0; i < oFSConnResponseArray.length; i++) {
        	OFSConnResponseConverter oFSConnResponseConverter = new OFSConnResponseConverter();
        	dataToConverter(oFSConnResponseArray[i], oFSConnResponseConverter);
        	utils.setValue(jv, i+1, oFSConnResponseConverter);
        }
        return jv;
    }
	
	@Override
	public JVarObject get(jVar jv) throws InvalidNestLevelException {
		if (jv == null) {
			return (new OFSConnResponseConverter());
		} else {
			return (new OFSConnResponseConverter(jv));
		}
    }     
	
	/**************** JDynArray Conversion ****************/
	/* Converting JDynArrays into Complex object */
	
	// This method will take JDynArray and convert into Complex objects
	@Override
	public void fromJDyn(JDynArray  jd) throws InvalidNestLevelException {
        
        JDynUtils jdUtils = new JDynUtils();

		this.setOfsResponse(jdUtils.getString(jd, OFSRESPONSE_INDEX));
	}

	// This method will take JDynArray and convert into Complex objects
	public void fromJDyn(OFSConnResponse oFSConnResponse, JDynArray  jd) throws InvalidNestLevelException {
        
        JDynUtils jdUtils = new JDynUtils();

		oFSConnResponse.setOfsResponse(jdUtils.getString(jd, OFSRESPONSE_INDEX));
	}
	
	// This method will take JDynArray and convert into Complex objects from Value marks
	public void fromJDyn(OFSConnResponse oFSConnResponse, JDynArray  jd, int fieldNum) throws InvalidNestLevelException {
        
        JDynUtils jdUtils = new JDynUtils();

		oFSConnResponse.setOfsResponse(jdUtils.getString(jd, fieldNum, OFSRESPONSE_INDEX));
	}

	// This method will take JDynArray and convert into Complex objects from Sub Value marks
	public void fromJDyn(OFSConnResponse oFSConnResponse, JDynArray  jd, int fieldNum, int valueNum) throws InvalidNestLevelException {
        
        JDynUtils jdUtils = new JDynUtils();

		oFSConnResponse.setOfsResponse(jdUtils.getString(jd, fieldNum, valueNum, OFSRESPONSE_INDEX));
	}
	
    // This method will take JDynArray and convert into LIST<Complex> objects 
    public void fromJDyn(List<OFSConnResponse> oFSConnResponseList, JDynArray  jd) throws InvalidNestLevelException {
        
		JDynUtils utils = new JDynUtils();
        List<OFSConnResponseConverter> oFSConnResponseConverterList = new ArrayList<OFSConnResponseConverter>();
        utils.getJDynObjectList(jd, oFSConnResponseConverterList, new OFSConnResponseConverter());
        dataListFromConverterList(oFSConnResponseConverterList, oFSConnResponseList);
                                        		    }
    
	// This method will take JDynArray<fieldNum> and convert into LIST<Complex> objects 
    public void fromJDyn(List<OFSConnResponse> oFSConnResponseList, JDynArray  jd, int fieldNum) throws InvalidNestLevelException {
        
		JDynUtils utils = new JDynUtils();
        List<OFSConnResponseConverter> oFSConnResponseConverterList = new ArrayList<OFSConnResponseConverter>();
        utils.getJDynObjectList(jd, fieldNum, oFSConnResponseConverterList, new OFSConnResponseConverter());
        dataListFromConverterList(oFSConnResponseConverterList, oFSConnResponseList);
                                        		    }

	// This method will take JDynArray<ValueMark> and convert into LIST<Complex> objects 
    public void fromJDyn(List<OFSConnResponse> oFSConnResponseList, JDynArray  jd, int fieldNum, int valueNum) throws InvalidNestLevelException {
        
		JDynUtils utils = new JDynUtils();
        List<OFSConnResponseConverter> oFSConnResponseConverterList = new ArrayList<OFSConnResponseConverter>();
        utils.getJDynObjectList(jd, fieldNum, valueNum, oFSConnResponseConverterList, new OFSConnResponseConverter());
        dataListFromConverterList(oFSConnResponseConverterList, oFSConnResponseList);
                                        		    }
	
	// This method will take JDynArray and convert into Complex[] objects 
    public void fromJDyn(OFSConnResponse[] oFSConnResponseArray, JDynArray  jd) throws InvalidNestLevelException {
    
		JDynUtils utils = new JDynUtils();
        OFSConnResponseConverter[] oFSConnResponseConverterArray = new OFSConnResponseConverter[jd.getNumberOfAttributes()];
        utils.getJDynObjectArray(jd, oFSConnResponseConverterArray, new OFSConnResponseConverter());
        dataArrayFromConverterArray(oFSConnResponseConverterArray, oFSConnResponseArray);
                                        		    }
    
	// This method will take JDynArray<fieldNum> and convert into Complex[] objects 
    public void fromJDyn(OFSConnResponse[] oFSConnResponseArray, JDynArray  jd, int fieldNum) throws InvalidNestLevelException {
    
	    JDynUtils utils = new JDynUtils();
        OFSConnResponseConverter[] oFSConnResponseConverterArray = new OFSConnResponseConverter[jd.getNumberOfValues(fieldNum)];
        utils.getJDynObjectArray(jd, fieldNum, oFSConnResponseConverterArray, new OFSConnResponseConverter());
        dataArrayFromConverterArray(oFSConnResponseConverterArray, oFSConnResponseArray);
                                            }

	// This method will take JDynArray<ValueMark> and convert into Complex[] objects 
    public void fromJDyn(OFSConnResponse[] oFSConnResponseArray, JDynArray  jd, int fieldNum, int valueNum) throws InvalidNestLevelException {
        
        for (int i = 1; i <= jd.getNumberOfSubValues(fieldNum, valueNum); i++){
            OFSConnResponse oFSConnResponse = new OFSConnResponse();
            fromJDyn(oFSConnResponse,jd, fieldNum, valueNum);
            oFSConnResponseArray[i-1] = oFSConnResponse;
        }
    }
	
	
	/* Converting Complex object to JDynArray */
	@Override
	public JDynArray toJDyn() throws InvalidNestLevelException {
	
		JDynArray jd = new JDynArray();  //Initialise a new JDynArray
		JDynUtils jdUtils = new JDynUtils();
		
        jdUtils.setValue(jd, OFSRESPONSE_INDEX, ofsResponse);
	
		return jd;
    } 
	
	// This method will convert the Complex object into JDynArray
	public JDynArray toJDyn(OFSConnResponse oFSConnResponse) throws InvalidNestLevelException {
	
		JDynUtils jdUtils = new JDynUtils();
		JDynArray jd = new JDynArray();
		
        jdUtils.setValue(jd, OFSRESPONSE_INDEX, jdUtils.getStringValue(oFSConnResponse.getOfsResponse()));
		return jd;
	}
    
	// This method will insert Complex Object into a JDynArray as 'Multi-Value' field
	public void toJDyn(OFSConnResponse oFSConnResponse, JDynArray jd, int fieldNum) throws InvalidNestLevelException {
		
		JDynUtils jdUtils = new JDynUtils();
		jdUtils.setValue(jd, fieldNum, OFSRESPONSE_INDEX, jdUtils.getStringValue(oFSConnResponse.getOfsResponse()));
	}
	
	// This method will insert Complex Object into a JDynArray as 'Sub-Value' field,
	public void toJDyn(OFSConnResponse oFSConnResponse, JDynArray jd, int fieldNum, int valueNum) throws InvalidNestLevelException {
		
		JDynUtils jdUtils = new JDynUtils();
		jdUtils.setValue(jd, fieldNum, valueNum, OFSRESPONSE_INDEX, jdUtils.getStringValue(oFSConnResponse.getOfsResponse()));
	}
	
	// This method will convert List of Complex objects into JDynArray as Value-Marks
    public JDynArray toJDyn(List<OFSConnResponse> oFSConnResponseList) throws InvalidNestLevelException {
        
        JDynArray jd = new JDynArray();
		JDynUtils jdUtils = new JDynUtils();
        
        			    		List<OFSConnResponseConverter> oFSConnResponseConverterList = new ArrayList<OFSConnResponseConverter>();
		dataListToConverterList(oFSConnResponseList, oFSConnResponseConverterList);
		jdUtils.setJDynObjectList(jd, oFSConnResponseConverterList);
        
		return jd;
    }
	
	// This method will convert List of Complex classes into JDynArray as Sub-Values
	 public void toJDyn(List<OFSConnResponse> oFSConnResponseList, JDynArray jd, int fieldNum) throws InvalidNestLevelException {
		
		JDynUtils jdUtils = new JDynUtils();
        
		List<OFSConnResponseConverter> oFSConnResponseConverterList = new ArrayList<OFSConnResponseConverter>();
		dataListToConverterList(oFSConnResponseList, oFSConnResponseConverterList);
        jdUtils.setJDynObjectList(jd, fieldNum, oFSConnResponseConverterList);
		
		                	}
	
	// This method will throws exception as you can not have List beyond Sub-value Markers
	 public void toJDyn(List<OFSConnResponse> oFSConnResponseList, JDynArray jd, int fieldNum, int valueNum) throws InvalidNestLevelException {
        if (oFSConnResponseList.size() > 0) {
			illegalJDynNestLevel ("Attempt to nest to Objects beyond allowable range");
		} else {
			// We can not have list at this point but we have to represent the space so JBC Impl wont confuse 
			jd.insert("", fieldNum, valueNum, 1);	
		}
	}
	
	// This method will convert Arrays[] of Complex objects into JDynArray as Value-Marks
    public JDynArray toJDyn(OFSConnResponse[] oFSConnResponseArray) throws InvalidNestLevelException {
        
        JDynArray jd = new JDynArray();
        
        for (int i=0; i < oFSConnResponseArray.length; i++ ) {
			toJDyn (oFSConnResponseArray[i], jd, i+1);
	    }
        return jd;
    }
	
	// This method will convert Array[] of Complex classes into JDynArray as Sub-Values
	 public void toJDyn(OFSConnResponse[] oFSConnResponseArray, JDynArray jd, int fieldNum) throws InvalidNestLevelException {
        for (int i=0; i < oFSConnResponseArray.length; i++ ) {
        	toJDyn (oFSConnResponseArray[i], jd, fieldNum, i+1);
        }
	}
	
	// This method will throws exception as you can not have Arrays beyond Sub-value Markers
	 public void toJDyn(OFSConnResponse[] oFSConnResponseArray, JDynArray jd, int fieldNum, int valueNum) throws InvalidNestLevelException {
        if (oFSConnResponseArray.length > 0) {
			illegalJDynNestLevel ("Attempt to nest to Objects beyond allowable range");
		} else {
			// We can not have list at this point but we have to represent the space so JBC Impl wont confuse 
			jd.insert("", fieldNum, valueNum, 1);	
		}
	}
	
	// This method will simply return the InvalidNestLevelException as request is out of Nesting level
	public void illegalJDynNestLevel (String errorMessage) throws InvalidNestLevelException {
		throw new InvalidNestLevelException (errorMessage);
	}	
 
	@Override
	public JDynObject get(JDynArray jd) throws InvalidNestLevelException {
		if (jd == null) {
			return (new OFSConnResponseConverter());
		} else {
			return (new OFSConnResponseConverter(jd));
		}
	}
	
}
