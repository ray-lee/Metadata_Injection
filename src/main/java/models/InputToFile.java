package models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import models.ImageMetaData;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import java.io.InputStream;



public class InputToFile {

	public final static String sTemplateFileName = "/MetaDataTemplate/MetaDataTemplate.txt";

	
	private final static Hashtable<String,String> htImageTypeMapping = new Hashtable<String,String>() 
	 {{  		 
			put("jpg","jpg");     
			put("jpeg","jpg");
			put("tif","tif");     
			put("tiff","tif");     
			put("png","png");     						
	 }}; 

	private static String returnHex(byte[] inBytes) throws Exception {
	        String hexString = new String();
	        for (int i=0; i < inBytes.length; i++) { 
	            hexString += Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
	        }                                  
	        
	        return hexString;
	}                                        

	
	public static String getCheckSumNew(String sFullPathImageFile) throws NoSuchAlgorithmException, Exception{
	       
		InputStream fis =  new FileInputStream(sFullPathImageFile);

	       byte[] buffer = new byte[1024];
	       MessageDigest complete = MessageDigest.getInstance("MD5");
	       int numRead;

	       do {
	           numRead = fis.read(buffer);
	           if (numRead > 0) {
	               complete.update(buffer, 0, numRead);
	           }
	       } while (numRead != -1);

	       fis.close();
	
	       return returnHex(complete.digest());
    
	}
	
	
	 
	 
	public static String getCheckSum(String sFullPathImageFile) throws NoSuchAlgorithmException, Exception {
		
		File input = new File(sFullPathImageFile);
	    int i = sFullPathImageFile.lastIndexOf("."); 
		String extension = new String();
	    
		if (i > 0 ) {
	    	extension = sFullPathImageFile.substring(i+1);
	        //System.out.println(htImageTypeMapping.get(extension.trim().toLowerCase()));
		} else {
	    	throw new Exception("file : " + sFullPathImageFile + " has no extenstion name.");
	    }	
		
	    
        BufferedImage buffImg = ImageIO.read(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(buffImg, htImageTypeMapping.get(extension.trim().toLowerCase()), outputStream);
        byte[] data = outputStream.toByteArray();

        //System.out.println("Start MD5 Digest");
        MessageDigest md = MessageDigest.getInstance("MD5");
        //md.update(data);
        byte[] hash = md.digest();
        
				
        return returnHex(hash);	
	}


	public static String getCheckSumByJAI(String sFullPathImageFile) throws NoSuchAlgorithmException, Exception {
		
		File input = new File(sFullPathImageFile);
	    int i = sFullPathImageFile.lastIndexOf("."); 
		String extension = new String();
	    
		if (i > 0 ) {
	    	extension = sFullPathImageFile.substring(i+1);
	        //System.out.println(htImageTypeMapping.get(extension.trim().toLowerCase()));
		} else {
	    	throw new Exception("file : " + sFullPathImageFile + " has no extenstion name.");
	    }	
		
	    
		RenderedImage image = JAI.create("fileload", sFullPathImageFile);
        //BufferedImage buffImg = ImageIO.read(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, htImageTypeMapping.get(extension.trim().toLowerCase()), outputStream);
        byte[] data = outputStream.toByteArray();

        //System.out.println("Start MD5 Digest");
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        byte[] hash = md.digest();
        
				
        return returnHex(hash);	
	}

	
	
	
	
	public static void execute(ArrayList<ImageMetaData> alimd, 
							   ArrayList<String> alfilename, 
							   String sDestFileName,
							   String sImagesFolder) throws FileNotFoundException,
							   								SecurityException,
							   								IOException,
							   								Exception {
       
		
		if (alimd.size() > 1) throw new Exception("This folder (acc#) retrive two records of metadata.");					
		FileUtil.isCopyToUTF8 = true;
		FileUtil.isWriteToUTF8 = true;
		if (!FileUtil.isExist(sDestFileName)) {

			try {
				FileUtil.CopyFile(InputToFile.sTemplateFileName, sDestFileName);		
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException(e.getMessage());
			} catch (SecurityException e) {
				throw new SecurityException(e.getMessage());
			} catch (IOException e) {
				throw new IOException(e.getMessage());
			} catch (Exception e) {
			    e.printStackTrace();
				throw new Exception(e.getMessage());				
			} 
			
		}
			
		String outputString = new String();
		for (int i=0 ; i < alfilename.size() ; i ++) {
	
			if (alfilename.get(i).trim().equals(".DS_Store")) {
				System.out.println("Skip the hidden folder .DS_Store");
				continue;
			}
			
			//get checksum of the image file from MD5
			String sMD5Checksum = null;
			try {
				//sMD5Checksum = InputToFile.getCheckSum(sImagesFolder + alfilename.get(i).trim());
				sMD5Checksum = InputToFile.getCheckSumNew(sImagesFolder + alfilename.get(i).trim());
			} catch (NoSuchAlgorithmException e) {
				System.out.println("(NoSuchAlgorithmException) Not get MD5 for :" + alfilename.get(i) + " : " + e.getMessage());
				sMD5Checksum = "CheckSum_Not_Available";
			} catch (Exception e) {
				System.out.println("Not get MD5 for :" + alfilename.get(i) + " : " + e.getMessage());
				sMD5Checksum = "CheckSum_Not_Available";
			}
			
			for (int j=0 ; j < alimd.size() ; j ++) {
					
				outputString = outputString 
							   + parseFilePath(alfilename.get(i).trim()) + "	"
					           + alimd.get(j).getTitle().trim() + "	" 
					           + alimd.get(j).getArtist().trim() + "	" 
					           + alimd.get(j).getDateMade().trim() + "	"
					           + sMD5Checksum.trim() + "	"
					           + alimd.get(j).getIdNumber().trim() + "	"
					           + System.getProperty("line.separator");		   
			
			}
						
			//System.out.println(msTargetDir + File.separator + sfolder + File.separator + alfilename.get(i).trim());
			//System.out.println( "--fileName: " + alfilename.get(i) + " ok!");
		}

		//write the string to the txt file
		//System.out.println(outputString);
		
		
		try {
			FileUtil.WriteFile(sDestFileName, outputString);		
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(e.getMessage());
		} catch (SecurityException e) {
			throw new SecurityException(e.getMessage());
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		} catch (Exception e) {
		    e.printStackTrace();
			throw new Exception(e.getMessage());				
		} 
		
		
	}
	
	private static String parseFilePath(String sFilePath) {
		
		int iLastIndex = sFilePath.lastIndexOf(File.separator);
		
		if ( iLastIndex < 0 )
			return sFilePath;
		
		return sFilePath.substring(iLastIndex+1);
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		//System.out.println(parseFilePath("/ggyyggy.ggyy/678576.xxxxx.tif"));
		
		try {
		String tmp = InputToFile.getCheckSumNew("/path/to/image.tif");
		System.out.println(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		FileUtil.isCopyToUTF8 = false;
		FileUtil.isWriteToUTF8 = false;
		if (!FileUtil.isExist("/path/to/bcmetadata.txt")) {

			try {
				FileUtil.CopyFile(InputToFile.sTemplateFileName, "/path/to/bcmetadata.txt");		
			}  catch (Exception e) {
			    e.printStackTrace();
			} 
			
		}
		 */
		
		
		
	}

}
