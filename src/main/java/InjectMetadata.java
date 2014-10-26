import java.io.FileNotFoundException;

import models.DBUtils;
import models.MDUtils;
import controllers.BAMCOLLECTIONController;
import controllers.BAMCDArchiveController;
import controllers.InjectMetaDataController;

import java.util.Date;
import java.util.Scanner;

public abstract class InjectMetadata {
	protected abstract String getType();
	protected abstract InjectMetaDataController getController(String targetDir, DBUtils dbUtils, MDUtils mdUtils);
	
	protected static boolean check_dialog(String sfolderPath, String sType) {

		System.out.println("Please make sure the folder is " + sType + " type:");
		System.out.println(sfolderPath);
		System.out.print("(y/N):");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		//System.out.println(input);

		if (input.equals("y")) 
			return true;
		else
			return false;
		
	}
	
	
	
	/**
	 * @param args
	 */
	protected void injectMetadata(String[] args) {
		// TODO Auto-generated method stub
		MDUtils md = null;
		DBUtils db = null;
		
				
		if (args.length < 1) {
			System.out.println("Please assign the full path of the images folder!");
			return;
		}
		
		
		if (!InjectMetadata.check_dialog(args[0], getType()))	{
			System.out.println("Please run the program again!");
			System.exit(0);
		}
		
		
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
			 System.out.println(e.getLocalizedMessage());
			 System.exit(-1);
		} 
		 
		System.out.println("Now processing the " + getType() + " type folders!");
		System.out.println("Start: " + new Date().toString());

		//System.out.println(args[0].trim());

		InjectMetaDataController bcc = getController(args[0].trim(),db, md);
		bcc.runInject();				
		
		System.out.println("End: " + new Date().toString());

		
		
	}

}
