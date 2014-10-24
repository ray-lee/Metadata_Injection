package views;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestString {

	
	public void getPattern(String sStr){

		
		
	      String pattern = "bampfa_([0-9]*\\.[0-9]*\\.[0-9]*)_?(.*)";

	      // Create a Pattern object
	      Pattern r = Pattern.compile(pattern);

	      // Now create matcher object.
	      Matcher m = r.matcher(sStr);
	      if (m.find()) {
	    	  
	         System.out.println("Found value: " + m.group(0) );
	         System.out.println("Found value: " + m.group(1) );
	         System.out.println("Found value: " + m.group(2) );
	         
	      } else {
	         System.out.println("NO MATCH");
	      }		
		
		/*
		String[] split = sStr.split("_");
        for (int i=0; i < split.length ; i++)
		System.out.println( i + ":" + split[i]);
		
        System.out.println(split[0]);
		*/
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestString ts = new TestString();
		ts.getPattern("bampfa_1992.4.2_1.39_t.tif");
		ts.getPattern("bampfa_1992.4.2_1.tif");
		ts.getPattern("bampfa_1992.4.2_.tif");
		ts.getPattern("bampfa_1992.4.2.tif");
		ts.getPattern("icon");
		

	}

}
