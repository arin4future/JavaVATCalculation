package com.testpkg.arkx.arkxProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;


public class App 
{
	
	public static Map<Double, List<String>> countrywithHigestVAT;
	public static Map<Double, List<String>> countrywithLowestVAT;
	
	
    public static void main( String[] args )
    {
    	
    	DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	
        System.out.println( "Starting at "+ sdf.format(new java.util.Date())+"..................\n\n\n");
        
        try{
        
    	URL url = new URL("http://jsonvat.com/");
        	
    	String dateFormat = new String("yyyy-mm-dd");        
        
        Example example = new Example();
        Gson gson = new GsonBuilder().create();
        
        String jsonObj = readJsonFromUrl(url.toString());
        
        
        if(jsonObj!=null){
        	
	        //System.out.println(jsonObj);
	        example = gson.fromJson(jsonObj, Example.class);
	        
	        evaluateCountryWithHighestAndLowestStandardVAT(example);
	        
	        System.out.println("\n============================================================================");
	        
	        
	        Iterator it1 = countrywithHigestVAT.entrySet().iterator();
	        
	        System.out.println("Highest 3 Standard VAT values with the corresponding Contries \n\n");
	        
	        while(it1.hasNext()){
	        
	        	Map.Entry<Double, List<String>> pair = (Map.Entry<Double, List<String>>) it1.next();
	        	
	        	
	        	
	        	System.out.println("VAT value:"+ pair.getKey().toString()+" for the countries: ");
	        	for (String str : pair.getValue())
	        			System.out.println(str);
	        	
	        	System.out.println("\n");
	        	
	        }
	        
	        
	        System.out.println("\n============================================================================");
	        
	        Iterator it2 = countrywithLowestVAT.entrySet().iterator();
	        
	        System.out.println("Lowest 3 Standard VAT values with the corresponding Contries \n\n");
	        
	        while(it2.hasNext()){
	        
	        	Map.Entry<Double, List<String>> pair = (Map.Entry<Double, List<String>>) it2.next();
	        	
	        	
	        	System.out.println("VAT values:"+ pair.getKey().toString()+" for the countries: ");
	        	for (String str : pair.getValue())
        			System.out.println(str);
	        	
	        	System.out.println("\n");
        	
	        		        	
	        }
        
        }
        
     
           
    }catch(Exception exp){
    	
    	exp.printStackTrace();
     }
        
        System.out.println( "\n\n\n\nEnding at "+ sdf.format(new java.util.Date())+"................!" );
    }
    
    
    private static void evaluateCountryWithHighestAndLowestStandardVAT(Example example) throws ParseException, Exception{
    	
    	Map<Double, List<String>> countryMapwithVAT = null;
    	String dateFormat = new String("yyyy-mm-dd"); 
    	
    	double tempHigestStandardVATRate = 0.0d;
    	
    	String tempCountryName = null;
    	
    	List<Rate> countryList  = example.getRates();
    	
    	if(countryList.size()>0){
    		
    		countrywithHigestVAT = new HashMap<Double, List<String>>();
    		countrywithLowestVAT = new HashMap<Double, List<String>>();
    		
    		//finalCountryList = new ArrayList<String>();
    		
        	for(Rate rate :example.getRates()){
        		
        		tempCountryName = rate.getName();
        		java.util.Date effectiveDate = null;
        		
        		if(rate.getPeriods().size()>0){
        		
        		effectiveDate = new java.text.SimpleDateFormat(dateFormat).parse(rate.getPeriods().get(0).getEffectiveFrom());
        		
        			for(Period period : rate.getPeriods()){
        				
        					java.util.Date tempDate = new java.text.SimpleDateFormat(dateFormat).parse(period.getEffectiveFrom());
        					if(effectiveDate.before(tempDate)){
        						effectiveDate = tempDate;
        					}
            		}
        			
        			for(Period period : rate.getPeriods()){
        				
        				if(effectiveDate.compareTo(new java.text.SimpleDateFormat(dateFormat).parse(period.getEffectiveFrom()))==0){
        					
        					countrywithHigestVAT = evaluateAndAddAgainstTopThreeStandardVAT(countrywithHigestVAT, period, tempCountryName);
        					
        					countrywithLowestVAT = evaluateAndAddAgainstLowestThreeStandardVAT(countrywithLowestVAT, period, tempCountryName);
        				}
        				
        			}
        			
        			
        		}
        		
        	}
        		
        		
        }
    	
    }
    
    
    private static Map<Double, List<String>> evaluateAndAddAgainstTopThreeStandardVAT(Map<Double, List<String>> countryMapwithVAT, Period period, String countryName) {
    	
    	//System.out.println("Inside evaluateAndAddAgainstTopThreeStandardVAT ....");
    	
    	List<String> countryList = null;
    	
    	Map<Double, List<String>> tempMap = new HashMap<Double, List<String>>();
    	
    	List<Double> minVatValueList = new ArrayList<Double>();  // this will be used to find the min amongst the max values
    	
    	
    	if(period!=null && period.getRates().getStandard()!=null && period.getRates().getStandard().doubleValue()>0.0 && countryMapwithVAT!=null){
    		
    		   		
    		// Find the number of elements in the map,
    		// if the count is less than 3, add the new element
    		// Else, determine if the new value is greater than any of the existing 3 values, if yes add the new value and remove the minimum value
    				
    			
    			//If the new value matches any of the existing values, then add that country name in the country List
    			
    			if(countryMapwithVAT.size() == 0){                                               // This takes care of the empty Map -- first time case
    				
    				countryList = new ArrayList<String>();
    				countryList.add(countryName);            			
        			countryMapwithVAT.put(period.getRates().getStandard(), countryList);
    				
    			}else{
    			
    				Iterator it1 = countryMapwithVAT.entrySet().iterator();
	    			
	    			while(it1.hasNext()){ 
	    				
	    				Map.Entry<Double, List<String>> pair = (Map.Entry<Double, List<String>>) it1.next();
	    				
	    				if(pair.getKey().doubleValue() == period.getRates().getStandard().doubleValue()){
	    					
	    					countryList = pair.getValue();
	    					countryList.add(countryName);
	    					tempMap.put(pair.getKey(),countryList);
	        				
	        			}else if(pair.getKey().doubleValue() > period.getRates().getStandard().doubleValue()){
	        				
	        				if(countryMapwithVAT.size()<3){
	        					countryList = new ArrayList<String>();
		        				countryList.add(countryName);            			
		        				tempMap.put(period.getRates().getStandard(), countryList);
	        				}
	        				
	        			}else if(pair.getKey().doubleValue() < period.getRates().getStandard().doubleValue()){
	        				
	        				countryList = new ArrayList<String>();
	        				countryList.add(countryName);            			
	        				tempMap.put(period.getRates().getStandard(), countryList);
	            			
	            			//System.out.println("Added max value.....");
	        				
	            			minVatValueList.add(pair.getKey().doubleValue());  // keep in the list for evaluation and removal of the minimum later
	        				
	        			}
	    				
	    			}
	    			
	    			
	    			//Copy from tempMap to finalMap
	    			
	    			Iterator it2 = tempMap.keySet().iterator();
	    				while(it2.hasNext()){
	    					
	    					Double keyVal = (Double) it2.next();
	    					countryMapwithVAT.put(keyVal, tempMap.get(keyVal));
	    					
	    				}
    			
    		}
    			
    			
    			if(countryMapwithVAT.size()>3){
    			
	    			double minVatValue = 0.0d;
	    			
	    			if(minVatValueList!=null && minVatValueList.size()>0){
	    				
	    				if( minVatValueList.size()<=3){
	    					
	    					int listSize = minVatValueList.size();
	    					
	    					minVatValue = minVatValueList.get(0);
	    					for (int count =listSize-1;count>0;count--){
	    						if(minVatValue>minVatValueList.get(count))
	    							minVatValue = minVatValueList.get(count);
	    					}					
	    					
	    					
	    				}else{
	    					
	    					System.out.println("trouble, check the logic!!!!!!!!!!!!!!!!!");
	    				}
	    			}
	    			
	    			
	    			if(minVatValue>0.0){
	    				countryMapwithVAT.remove(Double.valueOf(minVatValue));
	    				
	    				//System.out.println("removed min value.....");
	    			}    			
    		}
    			
    	}  	
    	
    	
    	//System.out.println("Exiting evaluateAndAddAgainstTopThreeStandardVAT ....");
    	
    	return countryMapwithVAT;
    	
    }
    
    
    
  private static Map<Double, List<String>> evaluateAndAddAgainstLowestThreeStandardVAT(Map<Double, List<String>> countryMapwithVAT, Period period, String countryName) {
	  
	 // System.out.println("Inside evaluateAndAddAgainstLowestThreeStandardVAT ....");
    	
	  List<String> countryList = null;
	  
	 Map<Double, List<String>> tempMap = new HashMap<Double, List<String>>();
  	
  	List<Double> maxVatValueList = new ArrayList<Double>();  // this will be used to find the min amongst the max values
  	
  	
  	if(period!=null && period.getRates().getStandard()!=null && period.getRates().getStandard().doubleValue()>0.0 && countryMapwithVAT!=null){
  		
  		   		
  		// Find the number of elements in the map,
  		// if the count is less than 3, add the new element
  		// Else, determine if the new value is lesser than any of the existing 3 values, if yes add the new value and remove the maximum value
  				
  			
  			//If the new value matches any of the existing values, then add that country name in the country List
  			if(countryMapwithVAT.size() == 0){                                               // This takes care of the empty Map -- first time case
				
				countryList = new ArrayList<String>();
				countryList.add(countryName);            			
    			countryMapwithVAT.put(period.getRates().getStandard(), countryList);
				
			}else{
			
				Iterator it1 = countryMapwithVAT.entrySet().iterator();	
  			
	  			while(it1.hasNext()){ 
	  				
	  				Map.Entry<Double, List<String>> pair = (Map.Entry<Double, List<String>>) it1.next();
	  				
	  				if(pair.getKey().doubleValue() == period.getRates().getStandard().doubleValue()){
	  					
	  					countryList = pair.getValue();
	  					countryList.add(countryName);
	  					tempMap.put(pair.getKey(),countryList);
	      				
	      			}else if(pair.getKey().doubleValue() < period.getRates().getStandard().doubleValue()){
	      				
	      				if(countryMapwithVAT.size()<3){
        					countryList = new ArrayList<String>();
	        				countryList.add(countryName);            			
	        				tempMap.put(period.getRates().getStandard(), countryList);
        				}
	      				
	      				
	      			}else if(pair.getKey().doubleValue() > period.getRates().getStandard().doubleValue()){
	      				
	      				countryList = new ArrayList<String>();
	      				countryList.add(countryName);            			
	      				tempMap.put(period.getRates().getStandard(), countryList);
	          			
	          			//System.out.println("Added min value.....");
	      				
	          			maxVatValueList.add(pair.getKey().doubleValue());  // keep in the list for evaluation and removal of the minimum later
	      				
	      			}
	  				
	  			}
  			
			}
  			
  			
  		//Copy from tempMap to finalMap
			
			Iterator it2 = tempMap.keySet().iterator();
				while(it2.hasNext()){
					
					Double keyVal = (Double) it2.next();
					countryMapwithVAT.put(keyVal, tempMap.get(keyVal));
					
				}
  			
  			
  			if(countryMapwithVAT.size()>3){
  			
	    			double maxVatValue = 0.0d;
	    			
	    			if(maxVatValueList!=null && maxVatValueList.size()>0){
	    				
	    				if( maxVatValueList.size()<=3){
	    					
	    					int listSize = maxVatValueList.size();
	    					
	    					maxVatValue = maxVatValueList.get(0);
	    					for (int count =listSize-1;count>0;count--){
	    						if(maxVatValue<maxVatValueList.get(count))
	    							maxVatValue = maxVatValueList.get(count);
	    					}					
	    					
	    					
	    				}else{
	    					
	    					System.out.println("trouble, check the logic!!!!!!!!!!!!!!!!!");
	    				}
	    			}
	    			
	    			
	    			if(maxVatValue>0.0){
	    				countryMapwithVAT.remove(Double.valueOf(maxVatValue));
	    				
	    				//System.out.println("removed max value.....");
	    			}    			
  		}
  			
  	}  	
  	
  //	System.out.println("Exiting evaluateAndAddAgainstLowestThreeStandardVAT ....");
  	
  	return countryMapwithVAT;
    	
    }
    
    
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }

      public static String readJsonFromUrl(String url) throws IOException, JsonIOException {
        InputStream is = new URL(url).openStream();
        try {
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          String jsonText = readAll(rd);
          return jsonText;
        } finally {
          is.close();
        }
      }
    
}
