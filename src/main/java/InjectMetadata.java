import java.io.FileNotFoundException;

import models.DBUtils;
import models.MDUtils;
import controllers.BAMCOLLECTIONController;
import controllers.BAMCDArchiveController;
import controllers.InjectMetaDataController;

import java.util.Date;
import java.util.Scanner;

public class InjectMetadata {

	
	private static boolean check_dialog(String sfolderPath, String sType) {

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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MDUtils md = null;
		DBUtils db = null;
		
				
		if (args.length < 1) {
			System.out.println("Please assign the full path of the images folder!");
			return;
		}
        

		// archive start
		/*
		if (!InjectMetadata.check_dialog(args[0], "Archive"))	{
			System.out.println("Please run the program again!");
			System.exit(0);
		}
		
		System.out.println("Now processing the Archive type folders!");
		System.out.println("Start: " + new Date().toString());
		*/
		// archive end
		
		
		//bamcollection start
		
		if (!InjectMetadata.check_dialog(args[0], "BAM Collection"))	{
			System.out.println("Please run the program again!");
			System.exit(0);
		}
		
		
		System.out.println("Now processing the BAM Collection type folders!");		
		System.out.println("Start: " + new Date().toString());
		
		//bamcollection end
	    
		//System.out.println("Start: " + new Date().toString());
		
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
		 
		//System.out.println(args[0].trim());

		
		InjectMetaDataController bcc = new BAMCOLLECTIONController(args[0].trim(),db, md);
		bcc.runInject();				
        
				
		//System.out.println(args[0].trim());
		/*
		InjectMetaDataController bcac = new BAMCDArchiveController(args[0].trim(),db, md);
		bcac.runInject();				
		*/
		
		System.out.println("End: " + new Date().toString());

		
		
	}

}
