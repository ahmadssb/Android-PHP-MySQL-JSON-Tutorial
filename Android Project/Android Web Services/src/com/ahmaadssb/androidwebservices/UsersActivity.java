/***
 * @author Ahmed Saleh
 * Created by ahmadssb on 2014-12-13
 * Website: http://www.ahmadssb.com
 * Email: ahmad@ahmadssb.com
 * Facebook: https://www.facebook.com/ahmadssbblog
 * Twitter: https://twitter.com/ahmadssb
 * YouTube: http://www.youtube.com/user/ahmadssbblog
 */
package com.ahmaadssb.androidwebservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class UsersActivity extends Activity {
	
	// Declare Variables
	JSONObject jsonobject;
	JSONArray jsonArrayUsers;
	
	ListView listview;
	ListViewAdapter adapterListView;
	
	ProgressDialog mProgressDialog;
	
	ArrayList<HashMap<String, String>> arraylistUsers;
	
	// JSON parser Class
	JSONParser jsonParser = new JSONParser();

	// „‰ Œ·«· ‘»ﬂ… «·Ê«Ì ›«Ì
	private static String USERS_URL = "http://192.168.1.103/GoogleDrive/android-webservice1/users.php";

	// „‰ Œ·«· Android emulator
	// private static String USERS_URL = "http://10.0.2.2/GoogleDrive/android-webservice1/users.php";

	// „‰ Œ·«· —«»ÿ «·„Êﬁ⁄ ⁄·Ï «·«‰ —‰ 
	// private static String USERS_URL = "http://www.YOUR-DOMAIN.com/GoogleDrive/android-webservice1/users.php";

	
	// JSON IDS:
	static final String TAG_SUCCESS = "success";
	static final String TAG_USERS_LIST = "users";
	static final String TAG_USER_ID = "id";
	static final String TAG_USER_USERNAME = "username";
	static final String TAG_USER_DISPLAYNAME = "displayname";
	private static final String TAG_MESSAGE = "message";
    
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_layout);

		new DownloadJSON().execute();
	}
	
	


	/***
	 * 
	 * @author Ahmadssb 
	 * ›Ì Â–« «·ﬂ·«” ”‰ﬁÊ„ »«·≈ ’«· »—«»ÿ  ”ÃÌ· „” Œœ„ ÃœÌœ  Ê‰ﬁÊ„
	 * Ê„‰ À„ ‰Õ’· ⁄·Ï JSON Response
	 * Ê√ŒÌ—« Õ”» ﬁÌ„… success ‰ﬁ—— „« ”‰ﬁÊ„ »⁄„·Â
	 * 
	 */
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {
		int status;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			// ‰ﬁÊ„ » ⁄—Ì› ArrayList 
			arraylistUsers = new ArrayList<HashMap<String, String>>();
			
			try {
				
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				//›ﬁÿ ·  »⁄ „« ÌÕ’· ›Ì LogCat
				Log.d("request!", "Starting");
				

				// ‰ﬁÊ„ »«—”«· ÿ·» POST «·Ï —«»ÿ Users 
				// ··Õ’Ê· ⁄·Ï JSON Objects 
			jsonobject = JSONParser.makeHttpRequest(USERS_URL,"POST",param);

			//›ﬁÿ ·  »⁄ „« ÌÕ’· ›Ì LogCat
			Log.d("Loding Restaurant", jsonobject.toString());
			

			// ‰÷⁄ ﬁÌ„… success Ê«· Ì Õ’·‰« ⁄·ÌÂ« „‰ JSON
			// »œ«Œ· «·„ €Ì— success
			status = jsonobject.getInt(TAG_SUCCESS);
			
			if(status != 0)
			{
				// ‰ﬁÊ„ »«·Õ’Ê· ⁄·Ï „’›Ê›… users ›Ì JSON 
				jsonArrayUsers = jsonobject.getJSONArray(TAG_USERS_LIST);


				for (int i = 0; i < jsonArrayUsers.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject jsonobject = jsonArrayUsers.getJSONObject(i);
					
					// ‰ﬁÊ„ »«·Õ’Ê· ⁄·Ï JSON Objects „⁄ ﬁÌ„… ﬂ· Object 
					// Ê‰ﬁÊ„ »Õ›ŸÂ« ›Ì ﬂ«∆‰ HashMap 
					map.put(TAG_USER_ID, jsonobject.getString(TAG_USER_ID));
					map.put(TAG_USER_USERNAME, jsonobject.getString(TAG_USER_USERNAME));
					map.put(TAG_USER_DISPLAYNAME, jsonobject.getString(TAG_USER_DISPLAYNAME));
					
					
					// ‰ﬁÊ„ »Õ›Ÿ «·»Ì«‰«  «·„Œ“‰… ›Ì map  »œ«Œ· arraylistUsers
					arraylistUsers.add(map);
				}
				//›ﬁÿ ·  »⁄ „« ÌÕ’· ›Ì LogCat
				Log.d("jsonArrayKioskRestaurant Size:", ""+arraylistUsers.size());
				
				
				
			}else{
				//›ﬁÿ ·  »⁄ „« ÌÕ’· ›Ì LogCat
				Log.d("Loding Restaurant Failure!", jsonobject.getString(TAG_MESSAGE));
			}
					
			
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {

			if (status != 0) {
				
				// √Ê·« ‰ﬁÊ„ » ⁄—Ì› listView
				listview = (ListView) findViewById(R.id.listView1);
				// ‰ﬁÊ„ »≈—”«· »Ì«‰«  «·„’›Ê›… arraylistUsers »œ«Œ· ListViewAdapter
				// ÕÌÀ «‰ ListViewAdapter ”Ì ﬂ›· » ‰”Ìﬁ «·»Ì«‰«  
				adapterListView = new ListViewAdapter(UsersActivity.this, arraylistUsers);
				// ‰ﬁÊ„ » ⁄ÌÌ‰ adapterListView »œ«Œ· listView
				listview.setAdapter(adapterListView);
				
				//  ›Ì Õ«· ﬂ‰   —Ìœ «‰  ﬁÊ„ » ‰›Ì– «„— „⁄Ì‰ ⁄‰œ «·‰ﬁ— ⁄·Ï «Õœ «·ÕﬁÊ· ›Ì ListView
				// ⁄·Ìﬂ «” œ⁄«¡ «·œ«·… setOnItemClickListener()
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						
						TextView tvUserID = (TextView) (arg1.findViewById(R.id.txtID));
						String sUserID = tvUserID.getText().toString();
						TextView tvUsername = (TextView) (arg1.findViewById(R.id.txtUsername));
						String sUsername = tvUsername.getText().toString();
						TextView tvDisplayName = (TextView) (arg1.findViewById(R.id.txtDisplayName));
						String sDisplayName = tvDisplayName.getText().toString();
						
						Toast.makeText(UsersActivity.this, sUserID + " - " + sUsername + " - " + sDisplayName, Toast.LENGTH_LONG).show();
						

					}
				});
				
			}
			
		}
	}	
}
