package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import models.DBUtils;
import models.FileUtil;
import models.ImageMetaData;
import models.InputToFile;
import models.MDUtils;

import controllers.InjectMetaDataController;

public class BAMCDArchiveController extends InjectMetaDataController {
	
	
	public BAMCDArchiveController(){}
	
	public BAMCDArchiveController(String sTargetDir, 
			                        DBUtils db, 			                        
			                        MDUtils md){
		super(sTargetDir, db, md);

		
	}

	
	public void runInject() {
	
		
    	ArrayList<String> alfilename;
    	//String sParentFolder = msTargetDir.substring(msTargetDir.lastIndexOf(File.separator)+1);
		FileUtil.listFileAndFolderCA(new File(msTargetDir));				
		TreeMap<String, ArrayList<String>> salFolderFile = FileUtil.getFileAndFolder();
				
    	//System.out.println( "===========================");		
		
		for ( String sfolder : salFolderFile.keySet() ) {
		  
			/*
		    if (sfolder.equals(sParentFolder)) {
		    	System.out.println();
		    	System.out.println( "**Do not do anything for the parent folder : " + sfolder + "**");
		    	continue;
		    }
		    */
		    		    
		    alfilename = salFolderFile.get(sfolder);
		    
		    /*
		    if (alfilename.size() == 0) 
		    	System.out.println( "No files under : " + sfolder);
		    else
		    */		     		    	
		    	queryAndInject(sfolder, alfilename);
		    
		    
		}
				
		
	}
		

	
	public void runInjectToFile(String sDestFileName) {
	
		
    	ArrayList<String> alfilename;
    	//String sParentFolder = msTargetDir.substring(msTargetDir.lastIndexOf(File.separator)+1);
		FileUtil.listFileAndFolderCA(new File(msTargetDir));				
		TreeMap<String, ArrayList<String>> salFolderFile = FileUtil.getFileAndFolder();
				
    	//System.out.println( "===========================");		
		
		for ( String sfolder : salFolderFile.keySet() ) {
		  
			/*
		    if (sfolder.equals(sParentFolder)) {
		    	System.out.println();
		    	System.out.println( "**Do not do anything for the parent folder : " + sfolder + "**");
		    	continue;
		    }
		    */
		    		    
		    alfilename = salFolderFile.get(sfolder);
		    
		    /*
		    if (alfilename.size() == 0) 
		    	System.out.println( "No files under : " + sfolder);
		    else
		    */		     		    	
		    queryAndInjectToTextFile(sfolder, alfilename, sDestFileName);
		    
		    
		}
				
		
	}

	
    public void queryAndInjectToTextFile(String sfolder, ArrayList<String> alfilename, String sDestFileName) {
    	
    	ArrayList<ImageMetaData> alimd;
    	
    	try {			
			//System.out.println( "Folder : " + sfolder);
			alimd = mdb.queryImageMetaData(sfolder.trim());
			InputToFile.execute(alimd, alfilename, sDestFileName, msTargetDir + File.separator);	    	
				    	
	    	//System.out.print(".");
			System.out.println(sfolder + " ok!");
			
		} catch (Exception e) {
			System.out.println(sfolder + " " + e.getMessage());
			
			System.out.println("Files For the acc# - " + sfolder + " : ");
			for (int i = 0; i < alfilename.size() ; i++) {
				System.out.println("File Name: " + alfilename.get(i));
			}
			System.out.println("");

			//e.printStackTrace();
		}
				    	
    	
    } 

	
	
    public void queryAndInject(String sfolder, ArrayList<String> alfilename) {
    	
    	ArrayList<ImageMetaData> alimd;
    	Map<String, String> tags;
    	    	
    	
    	try {			
			//System.out.println( "Folder : " + sfolder);
			alimd = mdb.queryImageMetaData(sfolder.trim());
			tags = mmd.prepareInjectData(alimd);
			
	    	for (int i=0 ; i < alfilename.size() ; i ++) {
	    		mmd.setTagsToFile(new File(msTargetDir + File.separator + alfilename.get(i).trim()), tags);	
	    		//System.out.println(msTargetDir + File.separator + alfilename.get(i).trim());
	    		//System.out.println( "--fileName: " + alfilename.get(i) + " ok!");
	    	}
	    	
	    	System.out.print(".");
	    	
		} catch (Exception e) {
			System.out.println(sfolder + " " + e.getMessage());
			//e.printStackTrace();
		}
				    	
    	
    } 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
