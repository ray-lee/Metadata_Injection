package models;

import models.ImageMetaData;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class DBUtils {

	
	private static volatile DBUtils mdbUniqueDbu = null;
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://bampfa.cspace.berkeley.edu/bampfa_domain?ssl=true"; //&sslfactory=org.postgresql.ssl.NonValidatingFactory";
	protected Connection mSqlConnection = null;
	protected ArrayList<Statement> mStatements = null;	
    protected PreparedStatement mSqlImageMetaDataQuery = null;    
	protected ArrayList<ImageMetaData> mImageMetaDataDataList = null;
	
	
	public static synchronized DBUtils getInstance() throws SQLException, ClassNotFoundException, InstantiationException,IllegalAccessException {
		    if (mdbUniqueDbu == null) {
		    	mdbUniqueDbu = new DBUtils();
		    }
		    return mdbUniqueDbu;
    }
	
	private DBUtils() throws SQLException, ClassNotFoundException, InstantiationException,IllegalAccessException {
		mStatements = new ArrayList<Statement>();				
		Class.forName(DRIVER).newInstance();
		connect();
	}

	private void connect() throws SQLException {
		Console console = System.console();
		console.writer().println("Enter your database login information:");
		String username = console.readLine("User: ");
		char[] password = console.readPassword("Password: ");

		mSqlConnection = DriverManager.getConnection(URL, username, new String(password));
	}
	
	private PreparedStatement prepareStatement(String sStatement) throws SQLException {
		PreparedStatement p = mSqlConnection.prepareStatement(sStatement);
		mStatements.add(p);		
		return p;
	} 
	
	public void close() throws SQLException {
		
		for (int i = 0 ; i < mStatements.size() ; i++ ) {
			((Statement)mStatements.get(i)).close();			
		} 

		if (mStatements != null) { 
			mStatements.clear();
			mStatements = null;
		}
		
		if (mSqlConnection != null ) mSqlConnection.close();

		if (mImageMetaDataDataList != null) {
			mImageMetaDataDataList.clear();
			mImageMetaDataDataList = null;
		}
						
						
	}

	
	public ArrayList<ImageMetaData> queryImageMetaData(String sACCNumber) throws SQLException {
    	ResultSet rsIMD = null;
        int iRecordCount = 0;
         	        	        
    	if ( mSqlImageMetaDataQuery == null) mSqlImageMetaDataQuery = prepareStatement(getImageMetaDataSqlQuery());
    	mSqlImageMetaDataQuery.setString( 1, sACCNumber);
    	rsIMD = mSqlImageMetaDataQuery.executeQuery();
			    		    	
    	if ( mImageMetaDataDataList == null ) 	    	
    		mImageMetaDataDataList = new ArrayList<ImageMetaData>();
    	else	
    		mImageMetaDataDataList.clear();
        
    	while (rsIMD.next()) {
        	iRecordCount++;
        	
        	mImageMetaDataDataList.add( 
        	new ImageMetaData(rsIMD.getString("Title"),
        			rsIMD.getString("Artist"),
        			rsIMD.getString("CreditLine"),
        			rsIMD.getString("DateMade"),
        			rsIMD.getString("IdNumber"),
        			rsIMD.getString("ItemClass"),
        			rsIMD.getString("Materials"),
        			rsIMD.getString("Measurement"),
        			rsIMD.getString("OriginOrPlace"),
	        		rsIMD.getString("PhotoCredit"),
	        		rsIMD.getString("Site"),
	        		rsIMD.getString("CopyRightCredit"),
        			rsIMD.getString("SubjectOne"),
        			rsIMD.getString("SubjectTwo"),
        			rsIMD.getString("SubjectThree"),
        			rsIMD.getString("SubjectFour"),
        			rsIMD.getString("SubjectFive"))
        	);
        }
        	    	
    	rsIMD.close();
        	        
        if (iRecordCount == 0) throw new SQLException("No Records Found.");
        
        return mImageMetaDataDataList;        
    }	

	    
    
    private String getImageMetaDataSqlQuery() { 
    	return new String ("SELECT artistcalc AS Artist, fullbampfacreditline AS CreditLine, datemade AS DateMade, idnumber AS IdNumber, itemclass AS ItemClass, " +
    					   "materials AS Materials, measurement AS Measurement, artistorigin AS OriginOrPlace, title AS Title, " +
    					   "photocredit AS PhotoCredit, site AS Site, copyrightcredit AS CopyRightCredit, " +
    					   "subjectone AS SubjectOne, subjecttwo AS SubjectTwo, subjectthree AS SubjectThree, " +
    					   "subjectfour AS SubjectFour, subjectfive AS SubjectFive " +
    					   "FROM bampfa_denorm.bampfa_collectionitems_vw WHERE idnumber = ? "); 
    }
    
    
            
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<ImageMetaData> alimd;
        
		try {
		DBUtils db = DBUtils.getInstance();
		
		System.out.println("");
		System.out.println("");

		System.out.println("------------------------------");
		
		//alimd = db.queryImageMetaData("1991.17");
		alimd = db.queryImageMetaData("1992.51.99");
		
		
		for (int i=0; i < alimd.size() ; i++ ) {
						
			System.out.println(alimd.get(i).getTitle());
			System.out.println(alimd.get(i).getArtist());
			System.out.println(alimd.get(i).getCreditLine());
			System.out.println(alimd.get(i).getDateMade());
			System.out.println(alimd.get(i).getIdNumber());
			System.out.println(alimd.get(i).getItemClass());
			System.out.println(alimd.get(i).getMaterials());
			System.out.println(alimd.get(i).getMeasurement());
			System.out.println(alimd.get(i).getOriginOrPlace());
			System.out.println(alimd.get(i).getPhotoCredit());
			System.out.println(alimd.get(i).getSite());
			System.out.println(alimd.get(i).getCopyRightCredit());
			System.out.println(alimd.get(i).getSubjectOne());
			System.out.println(alimd.get(i).getSubjectTwo());
			System.out.println(alimd.get(i).getSubjectThree());
			System.out.println(alimd.get(i).getSubjectFour());
			System.out.println(alimd.get(i).getSubjectFive());
		}

		
		System.out.println("------------------------------");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		

	}

}
