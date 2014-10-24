package controllers;

import models.DBUtils;
import models.FileUtil;
import models.ImageMetaData;
import models.MDUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public abstract class InjectMetaDataController {

	protected String msTargetDir = null;;
	protected DBUtils mdb = null;
	protected MDUtils mmd = null;
		
	public InjectMetaDataController(){}
	
	public InjectMetaDataController(String sTargetDir, 
			                        DBUtils db, 
			                        MDUtils md){
		msTargetDir = sTargetDir;		
		mdb = db;
		mmd = md;
	}

    public abstract void runInject();
    
    public abstract void queryAndInject(String sfolder, ArrayList<String> alfilename);
        
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
