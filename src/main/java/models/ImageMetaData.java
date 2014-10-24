package models;

public class ImageMetaData {

	
	private String msTitle;	
	private String msArtist;
	private String msCreditLine;
	private String msDateMade;
	private String msIdNumber;
	private String msItemClass;
	private String msMaterials;
	private String msMeasurement;
	private String msOriginOrPlace;
	private String msPhotoCredit;
	private String msSite;
	private String msCopyRightCredit;
	private String msSubjectOne;
	private String msSubjectTwo;
	private String msSubjectThree;
	private String msSubjectFour;
	private String msSubjectFive;
	
	public ImageMetaData(){}	
	
	public ImageMetaData(String sTitle,
						 String sArtist,
						 String sCreditLine,
						 String sDateMade,
						 String sIdNumber,
						 String sItemClass,
						 String sMaterials,
						 String sMeasurement,
						 String sOriginOrPlace,
						 String sPhotoCredit,
					     String sSite,
					     String sCopyRightCredit,
						 String sSubjectOne,
						 String sSubjectTwo,
						 String sSubjectThree,
						 String sSubjectFour,
						 String sSubjectFive)
	{
						
	    msTitle = initWhenNull(sTitle);	
		msArtist = initWhenNull(sArtist); //andrew 2/3/2014
		msCreditLine = initWhenNull(sCreditLine);
		msDateMade = initWhenNull(sDateMade); //andrew 1/28/2014
		msIdNumber = initWhenNull(sIdNumber);
		msItemClass = initWhenNull(sItemClass);
		msMaterials = initWhenNull(sMaterials);
		msMeasurement = initWhenNull(sMeasurement);
		msOriginOrPlace = initWhenNull(sOriginOrPlace);
		msPhotoCredit = initWhenNull(sPhotoCredit);
		msSite = initWhenNull(sSite);
		msCopyRightCredit = initWhenNull(sCopyRightCredit);
		msSubjectOne = initWhenNull(sSubjectOne);
		msSubjectTwo = initWhenNull(sSubjectTwo);
		msSubjectThree = initWhenNull(sSubjectThree);
		msSubjectFour = initWhenNull(sSubjectFour);
		msSubjectFive = initWhenNull(sSubjectFive);

	
	
	}

	public void setTitle(String sTitle) { 
		msTitle = sTitle;
	}
	public String getTitle() {
		 return msTitle;
	}	

	public void setArtist(String sArtist) { 
		msArtist = sArtist;
	}
	public String getArtist() {
		 return msArtist;
	}	

	public void setCreditLine(String sCreditLine) { 
		msCreditLine = sCreditLine;
	}
	public String getCreditLine() {
		 return msCreditLine;
	}	
	
	public void setDateMade(String sDateMade) { 
		msDateMade = sDateMade;
	}
	public String getDateMade() {
		 return msDateMade;
	}	
							
	public void setIdNumber(String sIdNumber) { 
		msIdNumber = sIdNumber;
	}
	public String getIdNumber() {
		 return msIdNumber;
	}	

	public void setItemClass(String sItemClass) { 
		msItemClass = sItemClass;
	}
	public String getItemClass() {
		 return msItemClass;
	}	
	
	public void setMaterials(String sMaterials) { 
		msMaterials = sMaterials;
	}
	public String getMaterials() {
		 return msMaterials;
	}	
	
	public void setMeasurement(String sMeasurement) { 
		msMeasurement = sMeasurement;
	}
	public String getMeasurement() {
		 return msMeasurement;
	}	

	public void setOriginOrPlace(String sOriginOrPlace) { 
		msOriginOrPlace = sOriginOrPlace;
	}
	public String getOriginOrPlace() {
		 return msOriginOrPlace;
	}	
	
	public void setPhotoCredit(String sPhotoCredit) { 
		msPhotoCredit = sPhotoCredit;
	}
	public String getPhotoCredit() {
		 return msPhotoCredit;
	}	
	
	public void setSite(String sSite) { 
		msSite = sSite;
	}
	public String getSite() {
		 return msSite;
	}	
	
	public void setCopyRightCredit(String sCopyRightCredit) { 
		msCopyRightCredit = sCopyRightCredit;
	}
	public String getCopyRightCredit() {
		 return msCopyRightCredit;
	}	
	
	public void setSubjectOne(String sSubjectOne) { 
		msSubjectOne = sSubjectOne;
	}
	public String getSubjectOne() {
		 return msSubjectOne;
	}	

	public void setSubjectTwo(String sSubjectTwo) { 
		msSubjectTwo = sSubjectTwo;
	}
	public String getSubjectTwo() {
		 return msSubjectTwo;
	}	

	public void setSubjectThree(String sSubjectThree) { 
		msSubjectThree = sSubjectThree;
	}
	public String getSubjectThree() {
		 return msSubjectThree;
	}	

	public void setSubjectFour(String sSubjectFour) { 
		msSubjectFour = sSubjectFour;
	}
	public String getSubjectFour() {
		 return msSubjectFour;
	}	
	
	public void setSubjectFive(String sSubjectFive) { 
		msSubjectFive = sSubjectFive;
	}
	public String getSubjectFive() {
		 return msSubjectFive;
	}	

	
	private String initWhenNull(String sTmp) {
		
		if (sTmp == null) 
			return new String("");
		else
		    return sTmp;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
