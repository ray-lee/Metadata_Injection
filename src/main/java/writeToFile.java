import java.io.FileNotFoundException;

import models.DBUtils;
import models.MDUtils;
import controllers.BAMCOLLECTIONController;
import controllers.BAMCDArchiveController;
import controllers.InjectMetaDataController;

import java.util.Date;
import java.util.Scanner;


public class writeToFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MDUtils md = null;
		DBUtils db = null;
		String scTarDir = "/path/to/directory";
		
		
		
		System.out.println("Start: " + new Date().toString());
		
		try {
			 md = MDUtils.getInstance();
		} catch (FileNotFoundException e) {
			 System.out.println("Fail to initiate the MetaData utility.");	
			 e.printStackTrace();
			 System.exit(-1);
		}

		try {
			db = DBUtils.getInstance();
		} catch (Exception e) {
			 System.out.println("Fail to initiate the DB utility.");	
			 e.printStackTrace();
			 System.exit(-1);
		} 
		
		
		InjectMetaDataController bcc = new BAMCOLLECTIONController(scTarDir,db, md);
		((BAMCOLLECTIONController)bcc).runInjectToFile("/path/to/destinationfile.txt");				// even father refers to the instance of the son class, but still need to type-change to use son't method, or it can only use father's method
		
		
		
		/*
		InjectMetaDataController bcac = new BAMCDArchiveController(scTarDir,db, md);
		((BAMCDArchiveController)bcac).runInjectToFile("/path/to/destinationfile.txt");				
		*/
		
		System.out.println("End: " + new Date().toString());

		
			
	}

}
