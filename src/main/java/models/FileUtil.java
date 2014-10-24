package models;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.SecurityException;
import java.io.IOException;
import java.io.FilenameFilter;

import java.util.ArrayList;
import java.util.TreeMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


class OnlyExtension implements FilenameFilter {
	
	private String msExtension;

	public OnlyExtension(String sExtension) {
		msExtension = "." + sExtension;
	}

	public boolean accept(File dir, String name) {
		return name.endsWith(msExtension);
	}

}

public class FileUtil {

	private static TreeMap<String, ArrayList<String>> msalFolderFile = new TreeMap<String, ArrayList<String>>();
    public static boolean isCopyToUTF8 = true;
    public static boolean isWriteToUTF8 = true;
    
	public static boolean isExist(String sInputFileName){
		File fInputFileName = new File(sInputFileName.trim());
		if (fInputFileName.isFile() && fInputFileName.exists())
			return true;
		else
			return false;	
	}
	
	public static boolean deleteFile(String sInputFileName){
		File fInputFileName = new File(sInputFileName.trim());
        return fInputFileName.delete();
	}
	
	
	public static void deleteSpecificFilesUnderFolder(String sFolderName, String sExtention) throws IOException {

		FilenameFilter ffFileFilter = new OnlyExtension(sExtention);
		String[] saMatchedFileArray = new File(sFolderName).list(ffFileFilter);
		
		  if (saMatchedFileArray.length != 0) {
		    for (int i = 0; i < saMatchedFileArray.length; i++) {
		    	if (!FileUtil.deleteFile(sFolderName + File.separator + saMatchedFileArray[i]))
		    		throw new IOException("Failed to delete those html files!");
		    }		   
		  }
	
	}

	public static void CopyFile(String sInFileName, String sOutFileName) throws IOException, 
																		 FileNotFoundException, 
																		 SecurityException {
		
		String tmpLine = null;
		boolean toAppend = false;
		BufferedReader brReader; 
		BufferedWriter bwWriter;

		File fOutFile;
		
		if (new File(sInFileName.trim()).isDirectory()) throw new IOException("Please Input the File Name for Input File!");
		if (new File(sOutFileName.trim()).isDirectory()) throw new IOException("Please Input the File Name for Output File!");
				
		fOutFile = new File(sOutFileName.trim());		
		if (fOutFile.isFile() && fOutFile.exists()) 
			toAppend = true;

		try {
			//brReader = new BufferedReader(new FileReader(sInFileName.trim()));
			//System.out.println(sInFileName);
			brReader = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream(sInFileName.trim())));
			//bwWriter = new BufferedWriter(new FileWriter(sOutFileName.trim(), toAppend));
			
			if (FileUtil.isCopyToUTF8) 
				bwWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sOutFileName.trim(), toAppend), "UTF-8"));
			else 
				bwWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sOutFileName.trim(), toAppend)));							
			
			while ((tmpLine = brReader.readLine()) != null ){
				bwWriter.write(tmpLine);
				bwWriter.newLine();				
			}
			
			bwWriter.flush();
			bwWriter.close();
			brReader.close();
			
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File Not Found. From : " + sInFileName + " To :" + sOutFileName);
		} catch (SecurityException e) {
			throw new SecurityException("Security Issue. From : " + sInFileName + " To :" + sOutFileName);
		} catch (IOException e) {
			throw new IOException("IO issue. From : " + sInFileName + " To :" + sOutFileName);
		}
				
	}
	
	public static void WriteFile(String sOutFileName, String sOutPutString) throws IOException, FileNotFoundException, SecurityException {
			boolean toAppend = false;
			BufferedWriter bwWriter;
			File fOutFile;

			if (new File(sOutFileName.trim()).isDirectory()) throw new IOException("Please Input the File Name for Output File!");

			fOutFile = new File(sOutFileName.trim());		
			if (fOutFile.isFile() && fOutFile.exists()) toAppend = true;

			try {
				
				if (FileUtil.isWriteToUTF8) 					
					bwWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sOutFileName.trim(), toAppend), "UTF-8"));				
				else 				
					bwWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sOutFileName.trim(), toAppend)));				
					
				bwWriter.write(sOutPutString.trim());
				bwWriter.newLine();				
				bwWriter.flush();
				bwWriter.close();

			} catch (FileNotFoundException e) {
				throw new FileNotFoundException("File Not Found. - " + sOutFileName);
			} catch (SecurityException e) {
				throw new SecurityException("Security Issue. - " + sOutFileName);
			} catch (IOException e) {
				throw new IOException("IO issue. - " + sOutFileName);
			}
	
	}
	
	public static TreeMap<String, ArrayList<String>> getFileAndFolder() {
		return FileUtil.msalFolderFile;
	}
	

	public static void listFileAndFolderBC(File fTargetDir) {
				
		if (fTargetDir.exists()) {
			
			//System.out.println("The folder : " + fTargetDir.getPath() + " " + fTargetDir.getName()) ;
			msalFolderFile.put(fTargetDir.getName(), new ArrayList<String>(0));
			
			File[] FileList = fTargetDir.listFiles();
			
			if (FileList != null && FileList.length > 0) {
				
				for (int i = 0; i < FileList.length ; i++) {					
					
					if (FileList[i].isFile()) {
						//System.out.println("The file name : " + FileList[i].getName()) ;
						msalFolderFile.get(fTargetDir.getName()).add(FileList[i].getName());
					} else {						
						listFileAndFolderBC(FileList[i]);						
					}					
					
				}
				
			} else {
				//System.out.println("The folder of " + fTargetDir.getPath() + " " + fTargetDir.getName()  + " is empty") ;
			}
							
		}
		
		
	}
	

	public static void listFileAndFolderCA(File fTargetDir) {
		
		String sAccNumber = null;
		String[] saTmpSplit = null;
		
		if (fTargetDir.exists()) {
			
			//System.out.println("The folder : " + fTargetDir.getPath() + " " + fTargetDir.getName()) ;
			
			File[] FileList = fTargetDir.listFiles();
			
			if (FileList != null && FileList.length > 0) {
				
				for (int i = 0; i < FileList.length ; i++) {					
					
					if (FileList[i].isFile()) {
						//System.out.println("The file name : " + FileList[i].getName()) ;
						saTmpSplit = FileList[i].getName().trim().split("_");
												
						if (! ( saTmpSplit.length > 1) )
							continue;
						
						sAccNumber = saTmpSplit[1];												
						if (!msalFolderFile.containsKey(sAccNumber))
							msalFolderFile.put(sAccNumber, new ArrayList<String>(0));
						
						msalFolderFile.get(sAccNumber).add(fTargetDir.getName() + File.separator + FileList[i].getName());
										
					} else {						
						listFileAndFolderCA(FileList[i]);						
					}					
					
				}
				
			} else {
				//System.out.println("The folder of " + fTargetDir.getPath() + " " + fTargetDir.getName()  + " is empty") ;
			}
							
		}
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		System.out.println(System.getProperty("user.dir"));
		try {
			
			String sTarFolder = "/path/to/directory";
			String sParentFolder = sTarFolder.substring(sTarFolder.lastIndexOf('/')+1);
			
			FileUtil.listFileAndFolderCA(new File(sTarFolder));				
			TreeMap<String, ArrayList<String>> salFolderFile = FileUtil.getFileAndFolder();
			
			System.out.println("-------------------------------------");
			
			for ( String key : salFolderFile.keySet() ) {
			    System.out.println( "Folder: " + key );
			    
			    if (key.equals(sParentFolder)) {
			    	System.out.println( "No for : " + key );
			    	continue;
			    }
			    
			    
			    ArrayList<String> arr1 = salFolderFile.get(key);
			    
			    if (arr1.size() == 0) 
			    	System.out.println( "No files under : " + key);
			    else {	
			    	for (int i=0 ; i < arr1.size() ; i ++) {
			    		System.out.println( "--fileName: " + arr1.get(i));
			    	}
			    }
			    
			}
						
			/*
		    for (Iterator it = salFolderFile.keySet().iterator(); it.hasNext();) {
		    	   
	               String n = (String) it.next();
	               System.out.println(n);
		    }
			*/
			
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

}
