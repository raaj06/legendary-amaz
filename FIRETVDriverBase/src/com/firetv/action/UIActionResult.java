package com.firetv.action;

import org.testng.Assert;
import org.testng.Reporter;

import com.firetv.util.UITestBase;

public class UIActionResult extends UITestBase{
    
	private String message="";
   
    public static UIActionResult actionSuccess (String resultMessage) {
    	Reporter.log(resultMessage,true);
		UIActionResult result = new UIActionResult();
     result.message = resultMessage;
     return result;
 }
	
	public static UIActionResult actionSuccess () {
     return actionSuccessWithoutLogger("Action completed successfully");
 }
	
	public static UIActionResult actionSuccessWithoutLogger(String resultMessage) {
    UIActionResult result = new UIActionResult();
     result.message = resultMessage;
     return result;
 }
	
	 public static UIActionResult actionFailed(String resultMessage) {
		 Reporter.log(resultMessage,true);
		 Assert.fail(resultMessage);
	        UIActionResult result = new UIActionResult ();
	        result.message = resultMessage;
	        return result;
	    }
	 
	 public static UIActionResult actionFailedOptional(String resultMessage) {
		 Reporter.log(resultMessage,true);
		 softAssert.fail(resultMessage);
	        UIActionResult result = new UIActionResult ();
	        result.message = resultMessage;
	        return result;
	    }
	 
	 
	 public static UIActionResult actionFailed (Exception e) {
	        return actionFailed (new ActionException(e));
	    }
	 
	 public static UIActionResult actionFailed (ActionException e) {
		 Assert.fail(e.toString());
		 UIActionResult result = new UIActionResult ();
	        result.message = e.getMessage();
	        return result;
	    }

}