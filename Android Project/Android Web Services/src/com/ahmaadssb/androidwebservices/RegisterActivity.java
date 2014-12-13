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
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	private EditText user, pass, displayname;
	private Button btnRegister;

	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();

	// „‰ Œ·«· ‘»ﬂ… «·Ê«Ì ›«Ì
	private static String REGISTER_URL = "http://192.168.1.103/GoogleDrive/android-webservice1/register.php";


	// „‰ Œ·«· Android emulator
	// private static String REGISTER_URL = "http://10.0.2.2/GoogleDrive/android-webservice1/register.php";

	// „‰ Œ·«· —«»ÿ «·„Êﬁ⁄ ⁄·Ï «·«‰ —‰ 
	// private static String REGISTER_URL = "http://www.YOUR-DOMAIN.com/GoogleDrive/android-webservice1/register.php";


	/***
	 * ﬂ„« ›⁄·‰« ›Ì „·›«  PHP 
	 * ﬁ„‰« »Ê÷⁄ „›« ÌÕ („ €Ì—« ) · JSON
	 * Ì›÷· √‰ ‰ﬁÊ„ » ⁄—Ì›Â« ﬂ‹ static final
	 * ·√‰Â« ﬁÌ„ À«» … ”‰Õ «ÃÂ« ·«Õﬁ« 
	 */
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_USERNAME = "username";
	private static final String TAG_PASSWORD = "password";
	private static final String TAG_DISPLAYNAME = "displayname";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);


		user = (EditText) findViewById(R.id.edtRegisterUserName);
		pass = (EditText) findViewById(R.id.edtRegisterPassword);
		displayname = (EditText) findViewById(R.id.edtRegisterDisplayName);

		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		new Register().execute();
	}

	/***
	 * 
	 * @author Ahmadssb 
	 * ›Ì Â–« «·ﬂ·«” ”‰ﬁÊ„ »«·≈ ’«· »—«»ÿ  ”ÃÌ· „” Œœ„ ÃœÌœ  Ê‰ﬁÊ„
	 * «—”«· «·»«—«„Ì —“ «·„ÿ·»ÊÌ… ussername , password, displayname
	 * Ê„‰ À„ ‰Õ’· ⁄·Ï JSON Response
	 * Ê√ŒÌ—« Õ”» ﬁÌ„… success ‰ﬁ—— „« ”‰ﬁÊ„ »⁄„·Â
	 * 
	 */
	class Register extends AsyncTask<String, String, String> {

		boolean failure = false;

		// ‰ﬁÊ„ »⁄—÷ ProgressDialog
		// Õ Ï  ‰ ÂÌ Ã„Ì⁄ «·√Ê«„— «·„ÊÃÊœ… ›Ì doInBackground()
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RegisterActivity.this);
			pDialog.setMessage("„Õ«Ê·…  ”ÃÌ· „” Œœ„ ÃœÌœ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// ‰ﬁÊ„ » ”ÃÌ· «·»Ì«‰«  «·„ÊÃÊœ… ›Ì ÕﬁÊ· username Ê password Ê displayname ﬂ‰’

			int success;
			String username = user.getText().toString();
			String password = pass.getText().toString();
			String sdisplayname = displayname.getText().toString();
			try {
				// ‰ﬁÊ„ »»‰«¡ «·»«—«„Ì —“ «·„ÿ·Ê»… ·⁄„·Ì…  ”ÃÌ· «·œŒÊ·
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair(TAG_USERNAME, username));
				params.add(new BasicNameValuePair(TAG_PASSWORD, password));
				params.add(new BasicNameValuePair(TAG_DISPLAYNAME, sdisplayname));

				//›ﬁÿ ·  »⁄ „« ÌÕ’· ›Ì LogCat
				Log.d("request!", "starting");
				
				// ‰ﬁÊ„ »«—”«· ÿ·» POST «·Ï —«»ÿ  ”ÃÌ· «·œŒÊ· „⁄ ≈÷«›… «·»«—«„Ì Ì—“
				// ··Õ’Ê· ⁄·Ï JSON Objects 
				JSONObject json = JSONParser.makeHttpRequest(REGISTER_URL,"POST", params);
				//›ﬁÿ ·  »⁄ „« ÌÕ’· ›Ì LogCat
				Log.d("Register attempt", json.toString());

				// ‰÷⁄ ﬁÌ„… success Ê«· Ì Õ’·‰« ⁄·ÌÂ« „‰ JSON
				// »œ«Œ· «·„ €Ì— success
				success = json.getInt(TAG_SUCCESS);

				/*
				 * ›Ì Õ«· ﬂ«‰  ﬁÌ„… success = 1 Ì⁄‰Ì –·ﬂ «‰Â  „ «· ”ÃÌ· »‰Ã«Õ
				 * √ﬂ » «·√„— «·–Ì  —Ìœ  ‰›Ì–Â ”Ê«¡ ‰ﬁ· «·„” Œœ„ «·Ï √ﬂ Ì›Ì Ì
				 * √Œ— √Ê «Ì ‘Ì  —ÌœÂ
				 * 
				 * ******************************** √„« ›Ì Õ«·… ﬁÌ„… success = 0
				 * Ì⁄‰Ì «‰Â  ÊÃœ „‘ﬂ·… ”Ê«¡ „‰ «·„” Œœ„ √Ê „‰ «·”Ì—›— «·„” ÷Ì›
				 * ÕÌ‰Â« √ﬂ » «·√„— «·–Ì  —ÌœÂ
				 */
				if (success == 1) {
					Log.d("User Created!", json.toString());
					finish();
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Register Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(String file_url) {
			// ‰ﬁÊ„ »≈Œ›«¡ «·œ«Ì·ÊÃ »⁄œ «·«‰ Â«¡
			pDialog.dismiss();
			
			// ‰ﬁÊ„ »⁄—÷ «·—”«·… «·‰« Ã… „‰ œ«·… doInBackground()
			if (file_url != null) {
				Toast.makeText(RegisterActivity.this, file_url,
						Toast.LENGTH_LONG).show();
			}

		}

	}

}
