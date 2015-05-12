package models;

import java.io.Console;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class DBUtils {
	private static volatile DBUtils mdbUniqueDbu = null;
	
	private static final String CSPACE_HOST = "bampfa.cspace.berkeley.edu";
	private static final String REPORT_URL = "https://" + CSPACE_HOST + "/cspace-services/reports/8e22a9da-efa1-4d1b-902c";
	private static final String AUTH_REALM = "org.collectionspace.services";
	
	protected ArrayList<ImageMetaData> mImageMetaDataDataList = null;
	
	protected HttpClientContext mClientContext = null;
	protected CloseableHttpClient mClient = null;
	
	public static synchronized DBUtils getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClientProtocolException, IOException {
		if (mdbUniqueDbu == null) {
			mdbUniqueDbu = new DBUtils();
		}
		return mdbUniqueDbu;
		}
	
	private DBUtils() throws ClassNotFoundException, InstantiationException,IllegalAccessException, ClientProtocolException, IOException {
		connect();
	}

	private void connect() throws ClientProtocolException, IOException {
		Console console = System.console();
		
		console.writer().println("Enter your CollectionSpace login information:");
		
		String username = console.readLine("Email: ");
		char[] password = console.readPassword("Password: ");

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		
		credsProvider.setCredentials(
				new AuthScope(CSPACE_HOST, AuthScope.ANY_PORT, AUTH_REALM, "basic"),
				new UsernamePasswordCredentials(username, new String(password)));
		
		mClientContext = HttpClientContext.create();
		mClientContext.setCredentialsProvider(credsProvider);

		mClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(REPORT_URL);
		CloseableHttpResponse response = mClient.execute(httpGet, mClientContext);
		
		int statusCode = response.getStatusLine().getStatusCode();
		response.close();
		
		if (statusCode < 200 || statusCode > 299) {
			mClient.close();
			throw new IOException(response.getStatusLine().getReasonPhrase());
		}
	}
	
	public ArrayList<ImageMetaData> queryImageMetaData(String sACCNumber) throws ClientProtocolException, IOException {
				int iRecordCount = 0;					

			if ( mImageMetaDataDataList == null ) {
				mImageMetaDataDataList = new ArrayList<ImageMetaData>();
			}
			else {	
				mImageMetaDataDataList.clear();
			}

		StringEntity payload = new StringEntity(
			"<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>" +
				"<ns2:invocationContext xmlns:ns2=\"http://collectionspace.org/services/common/invocable\">" +
					"<mode>nocontext</mode>" +
					"<params>" +
							"<param>" +
									"<key>idNumber</key>" +
									"<value>" + StringEscapeUtils.escapeXml10(sACCNumber) + "</value>" +
							"</param>" +
					"</params>" +
			"</ns2:invocationContext>"
		);
		
		HttpPost httpPost = new HttpPost(REPORT_URL);
		httpPost.setHeader("Content-Type", "application/xml");
		httpPost.setEntity(payload);
		
		CloseableHttpResponse response = mClient.execute(httpPost, mClientContext);

		int statusCode = response.getStatusLine().getStatusCode();
		
		if (statusCode < 200 || statusCode > 299) {
			response.close();
			throw new IOException(response.getStatusLine().getReasonPhrase());
		}
		
		String responseContent = null;
		
		try {
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
			}
		}
		finally {
				response.close();
		}
		
		if (responseContent != null) {
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().withNullString("null").parse(new StringReader(responseContent));
			
			for (CSVRecord record : records) {
						iRecordCount++;
					
						mImageMetaDataDataList.add(new ImageMetaData(
					record.get("title"),
					record.get("artist"),
					record.get("creditline"),
					record.get("datemade"),
					record.get("idnumber"),
					record.get("itemclass"),
					record.get("materials"),
					record.get("measurement"),
					record.get("originorplace"),
					record.get("photocredit"),
					record.get("site"),
					record.get("copyrightcredit"),
					record.get("subjectone"),
					record.get("subjecttwo"),
					record.get("subjectthree"),
					record.get("subjectfour"),
					record.get("subjectfive")));
			}
		}
											
				if (iRecordCount == 0) throw new IOException("No Records Found.");
		
				return mImageMetaDataDataList;				
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
