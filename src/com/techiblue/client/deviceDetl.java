package com.techiblue.client;

public class deviceDetl {
	
	
	 private static deviceDetl ref;
	    private String yourVariable = "blah";

	    private deviceDetl() {
	        // no code req'd
	    }

	    public static deviceDetl getSingletonObject()  {
	      if (ref == null)
	          ref = new deviceDetl();
	      return ref;
	    }

	    public void setYourVar(String input) {
	         this.yourVariable = input;
	    }

	    public String getYourVar() {
	          return yourVariable;
	    }   


}
