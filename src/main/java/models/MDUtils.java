package models;

import models.DBUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ca.digitalcave.moss.image.ExifToolWrapper;


public class MDUtils {

	
	private static volatile ExifToolWrapper metw = null;
	private final static String msExifToolWrapperPath = "/usr/bin/exiftool";	
	public static synchronized void getWrapperInstance() throws FileNotFoundException {
	    if (metw == null) {
	    	metw = new ExifToolWrapper(new File(msExifToolWrapperPath));	    
	    }
	}

	private static volatile MDUtils mmdu = null;	
	public static synchronized MDUtils getInstance() throws FileNotFoundException {
	    if (mmdu == null) {
	    	mmdu = new MDUtils();	    
	    }
	    
	    return mmdu;
	}

		
	public MDUtils() throws FileNotFoundException {
		MDUtils.getWrapperInstance();		
	}

	public MDUtils(ExifToolWrapper etw) {
		metw = etw;	
	}

	
	public Map prepareEraseData() {
		Map<String, String> tagearse = new HashMap<String, String>();		
		tagearse.put("all", "");

		return  tagearse;
	
	}
	
	
	public Map prepareInjectData(ArrayList<ImageMetaData> alimd){
		Map<String, String> tags = new HashMap<String, String>();
		
		for (int i=0; i < alimd.size() ; i ++) {			
			tags.clear();		
			tags.put("creator", alimd.get(i).getArtist());
			tags.put("description", "ARTIST: " + alimd.get(i).getArtist() +
									" NATIONALITY: " + alimd.get(i).getOriginOrPlace() + 
									" DATE_OF_WORK: " + alimd.get(i).getDateMade() +
									((alimd.get(i).getSite().trim().isEmpty()) ? "" : " PLACE: " + alimd.get(i).getSite()) + 
									" MATERIALS: " + alimd.get(i).getMaterials() + 
									" DIMENSIONS: " + alimd.get(i).getMeasurement() +
									" CREDIT_LINE: " + alimd.get(i).getCreditLine() +
									((alimd.get(i).getPhotoCredit().trim().isEmpty()) ? "" : " PHOTOGRAPHER: " + alimd.get(i).getPhotoCredit()));
			tags.put("coverage", alimd.get(i).getDateMade() + 
								((alimd.get(i).getSite().trim().isEmpty()) ? "" : " PLACE: " + alimd.get(i).getSite()));
			tags.put("identifier", alimd.get(i).getIdNumber());
			tags.put("type", alimd.get(i).getItemClass());
			tags.put("title", alimd.get(i).getTitle());
			tags.put("contributor", ((alimd.get(i).getPhotoCredit().trim().isEmpty())?"":"PHOTOGRAPHER: " + alimd.get(i).getPhotoCredit()));
			tags.put("rights", alimd.get(i).getCopyRightCredit());
			tags.put("subject", alimd.get(i).getSubjectOne() + 
								alimd.get(i).getSubjectTwo() +
								alimd.get(i).getSubjectThree() +
								alimd.get(i).getSubjectFour() +
								alimd.get(i).getSubjectFive());
		  
			
		}

		return tags;
		
	}	
	
	public void setTagsToFile(File fFile, Map mTags) {				
		metw.setTagsToFile(fFile, mTags);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBUtils db = null;
		ArrayList<ImageMetaData> alimd = null;
		ExifToolWrapper etw = null;
		MDUtils mdu = null;
		Map<String, String> tagerase = null;
		Map<String, String> tags = null;
		
		try {
		 mdu = MDUtils.getInstance();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
				
		try {
			db = DBUtils.getInstance();
		    //alimd = db.queryImageMetaData("1992.51.99");
			alimd = db.queryImageMetaData("1991.17");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		tagerase = mdu.prepareEraseData();
		tags = mdu.prepareInjectData(alimd);
		
		mdu.setTagsToFile(new File("/path/to/image.jpg"), tagerase);
		mdu.setTagsToFile(new File("/path/to/image.jpg"), tags);
		System.out.println("ok!");
		
	}

}
