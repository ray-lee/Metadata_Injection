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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

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
		
		for (ImageMetaData md : alimd) {
			tags.clear();		
			tags.put("creator", md.getArtist());
			tags.put("description", "ARTIST: " +md.getArtist() +
									" NATIONALITY: " + md.getOriginOrPlace() + 
									" DATE_OF_WORK: " + md.getDateMade() +
									((md.getSite().trim().isEmpty()) ? "" : " PLACE: " + md.getSite()) + 
									" MATERIALS: " + md.getMaterials() + 
									" DIMENSIONS: " + md.getMeasurement() +
									" CREDIT_LINE: " + md.getCreditLine() +
									((md.getCopyRightCredit().trim().isEmpty()) ? "" : " COPYRIGHT: " + md.getCopyRightCredit()) +
									((md.getPhotoCredit().trim().isEmpty()) ? "" : " PHOTOGRAPHER: " + md.getPhotoCredit()));
			tags.put("coverage", md.getDateMade() + 
								((md.getSite().trim().isEmpty()) ? "" : " PLACE: " + md.getSite()));
			tags.put("identifier", md.getIdNumber());
			tags.put("type", md.getItemClass());
			tags.put("title", md.getTitle());
			tags.put("contributor", ((md.getPhotoCredit().trim().isEmpty())?"":"PHOTOGRAPHER: " + md.getPhotoCredit()));
			tags.put("rights", md.getCopyRightCredit());
			
			String[] allSubjects = new String[]{
					md.getSubjectOne(),
					md.getSubjectTwo(),
					md.getSubjectThree(),
					md.getSubjectFour(),
					md.getSubjectFive()				
			};
			
			List<String> subjects = new ArrayList<String>(allSubjects.length);
			
			for (int i=0; i<allSubjects.length; i++) {
				if (StringUtils.isNotBlank(allSubjects[i])) {
					subjects.add(allSubjects[i]);
				}
			}
			
			tags.put("subject", StringUtils.join(subjects, ", "));
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
